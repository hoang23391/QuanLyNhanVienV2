package com.hoangsv.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hoangsv.model.PhongBan;
import com.hoangsv.quanlynhanvienv2.R;

import java.util.List;

public class PhongBanAdapter extends ArrayAdapter<PhongBan> {
    Activity context;
    int resource;
    List<PhongBan> objects;

    public PhongBanAdapter(Activity context, int resource, List<PhongBan> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(this.resource,null);
        TextView txtMaPhongBanItem= (TextView) row.findViewById(R.id.txtMaPhongBanItem);
        TextView txtTenPhongBanItem= (TextView) row.findViewById(R.id.txtTenPhongBanItem);
        TextView txtSoNhanVienItem= (TextView) row.findViewById(R.id.txtSoNhanVienItem);

        PhongBan phongBan=this.objects.get(position);
        txtMaPhongBanItem.setText(phongBan.getMaphongban()+"");
        txtTenPhongBanItem.setText(phongBan.getTenphongban());
        txtSoNhanVienItem.setText(phongBan.getSonhanvien()+"");
        return row;
    }

}
