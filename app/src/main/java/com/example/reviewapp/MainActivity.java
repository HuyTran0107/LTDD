package com.example.reviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reviewapp.adapter.SanPhamAdapter;
import com.example.reviewapp.data.repository.MyDatabase;
import com.example.reviewapp.dialog.DetailDialogFragment;
import com.example.reviewapp.model.SanPham;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DetailDialogFragment.DetailSanPhamListener {

    ListView listView;
    SanPhamAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_item);

        List<SanPham> listSp = MyDatabase.getAll(this);
        if(listSp.size() == 0){
            boolean check = initData();
            if(check){
                listSp.clear();
                listSp.addAll(MyDatabase.getAll(this));
            }
        }
        adapter = new SanPhamAdapter(listSp);
        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                DetailDialogFragment dialogFragment = DetailDialogFragment.newInstance(adapter.getItem(position), position, MainActivity.this);
                dialogFragment.show(getSupportFragmentManager(), "chi tiet san pham");
                return true;
            }
        });
    }

    boolean initData(){
        final long[] check = {0};
        List<SanPham> listSp = Arrays.asList(
                new SanPham("SP-112", "Vertu Constellation", "10000"),
                new SanPham("SP-123", "IPhone 5S", "10000"),
                new SanPham("SP-134", "Nokia Lumia 925", "10000"),
                new SanPham("SP-145", "SamSung Galaxy S4", "10000"),
                new SanPham("SP-156", "HTC Desire 600", "10000"),
                new SanPham("SP-167", "HKPhone Revo LEAD", "10000")
        );
        listSp.forEach(sp -> {
            if(!MyDatabase.insert(this, sp).getId().equals("-1")){
               check[0] = check[0] + 1;
            }
        });
        Log.d("INIT DATA", "initData: " + (check[0] > 0 ? "success" : "error"));
        return check[0] > 0;
    }

    @Override
    public void onDeleteCompleted(int position) {
        Toast.makeText(this, "delete complete", Toast.LENGTH_SHORT).show();
        adapter.deleteItem(position);
    }
}