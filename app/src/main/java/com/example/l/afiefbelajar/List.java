package com.example.l.afiefbelajar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    private ListView list;
    private java.util.List<String> listitem;
    private Button lihatJumlahData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        list  = (ListView)findViewById(R.id.list);
        lihatJumlahData = (Button)findViewById(R.id.lihatjumlahdata);
        final java.util.List<DataSelected> data = new ArrayList<>();

        listitem = new ArrayList<>();
        data.add(new DataSelected("abc",false));
        data.add(new DataSelected("adi",false));
        data.add(new DataSelected("sarah",false));
        data.add(new DataSelected("doni",false));
        data.add(new DataSelected("vivi",false));
        data.add(new DataSelected("anita",false));
        data.add(new DataSelected("yohani",false));
        data.add(new DataSelected("anni",false));
        data.add(new DataSelected("purwanti",false));
        final ActionBar actionBar = getSupportActionBar();
        final Adapter adapter = new Adapter(this,data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#92d1e8")));
                String itemselected = ((TextView)view.findViewById(R.id.list1)).getText().toString();
                if(data.get(i).isselected() == false){
                    data.get(i).setIsselected(true);
                    listitem.add(itemselected);
                    adapter.notifyDataSetChanged();
                }else{
                    listitem.remove(itemselected);
                    data.get(i).setIsselected(false);
                    adapter.notifyDataSetChanged();
                }
                if(listitem.size() == 0){
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                }
            }
        });
        lihatJumlahData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(List.this, String.valueOf(listitem.size()), Toast.LENGTH_SHORT).show();
                for (int i =0;i<listitem.size();i++){
                    Toast.makeText(List.this, listitem.get(i), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
