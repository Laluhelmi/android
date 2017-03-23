package com.example.l.afiefbelajar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.*;
import java.util.List;

/**
 * Created by L on 1/8/17.
 */

public class Adapter extends BaseAdapter {
    LayoutInflater inflater;
    List<DataSelected> strings;
    public Adapter(Context context, java.util.List<DataSelected> data){
        inflater = LayoutInflater.from(context);
        this.strings = data;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            DataSelected selected = (DataSelected) getItem(i);
            view = inflater.inflate(R.layout.customlistview,viewGroup,false);
            RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.idlistback);

            TextView textView = (TextView)view.findViewById(R.id.list1);
            if(selected.isselected() == false){
                layout.setBackgroundColor(Color.parseColor("#e8f774"));
            }else{
                layout.setBackgroundColor(Color.parseColor("#92d1e8"));
            }
            textView.setText(selected.getData());
            return view;
    }

    @Override
    public Object getItem(int i) {
        return strings.get(i);
    }
}
