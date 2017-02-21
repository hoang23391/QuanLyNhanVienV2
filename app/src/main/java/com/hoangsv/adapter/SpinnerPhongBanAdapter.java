package com.hoangsv.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hoangsv.model.PhongBan;
import com.hoangsv.quanlynhanvienv2.R;

import java.util.List;

public class SpinnerPhongBanAdapter extends ArrayAdapter<PhongBan> {
    Activity context;
    int resource;
    List<PhongBan> objects;
    public SpinnerPhongBanAdapter(Activity context, int resource, List<PhongBan> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(R.layout.spinner_item,parent,false);
        TextView txtSpPhongBan= (TextView) row.findViewById(R.id.txtSpPhongBan);

        PhongBan phongBan=objects.get(position);
        txtSpPhongBan.setText(phongBan.getTenphongban());
        return row;
    }
}
