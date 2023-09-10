package com.example.reviewapp.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.reviewapp.R;
import com.example.reviewapp.dialog.DetailDialogFragment;
import com.example.reviewapp.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamAdapter extends BaseAdapter {

    final List<SanPham> listSp;

    public SanPhamAdapter(List<SanPham> listSp) {
        this.listSp = listSp;
    }

    @Override
    public int getCount() {
        return listSp.size();
    }

    @Override
    public SanPham getItem(int position) {
        return listSp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View viewSanPham;// layout item san pham

        //Phan nay khong lam cung duoc, su dung view luon khoi tao instance
        if(view == null){
            viewSanPham = View.inflate(viewGroup.getContext(), R.layout.item_list_san_pham, null);
        }else viewSanPham = view;


        //Binding du lieu
        SanPham sanPham = listSp.get(position);
        ((TextView) viewSanPham.findViewById(R.id.item_name)).setText(String.valueOf(sanPham.getName()));

        return viewSanPham;
    }

    public void deleteItem(int position){
        listSp.remove(position);
        notifyDataSetChanged();
    }
}
