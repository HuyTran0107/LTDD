package com.example.reviewapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.reviewapp.R;
import com.example.reviewapp.data.repository.MyDatabase;
import com.example.reviewapp.model.SanPham;

public class DetailDialogFragment extends DialogFragment {

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    private SanPham sanPham;

    public void setPosition(int position) {
        this.position = position;
    }

    private int position;
    boolean stateDelete = false;

    public void setListener(DetailSanPhamListener listener) {
        this.listener = listener;
    }

    DetailSanPhamListener listener;

    TextView id, name, price;
    Button delete, back;

    View view;

    public static DetailDialogFragment newInstance(SanPham sanPham, int position, DetailSanPhamListener listener){
        DetailDialogFragment dialog = new DetailDialogFragment();
        dialog.setSanPham(sanPham);
        dialog.setPosition(position);
        dialog.setListener(listener);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.dialog_detail_san_pham, null);

        id = (TextView) view.findViewById(R.id.edt_id);
        name = (TextView) view.findViewById(R.id.edt_name);
        price = (TextView) view.findViewById(R.id.edt_price);

        id.setText(String.valueOf(sanPham.getId()));
        name.setText(String.valueOf(sanPham.getName()));
        price.setText(String.valueOf(sanPham.getPrice()));

        delete = (Button) view.findViewById(R.id.delete_item);
        back = (Button) view.findViewById(R.id.back);

        //event back
        back.setOnClickListener(v -> {
            dismiss();
        });


        //event delete
        delete.setOnClickListener(v -> {
            boolean check = MyDatabase.delete(getContext(), sanPham);
            if(check){
                stateDelete = true;
                dismiss();
            };
        });

        //event update đề không có nút update

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(stateDelete){
            listener.onDeleteCompleted(position);
            stateDelete = false;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        return builder.create();
    }

    public interface DetailSanPhamListener{
        void onDeleteCompleted(int position);
    }
}
