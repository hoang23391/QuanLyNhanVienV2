package com.hoangsv.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hoangsv.model.NhanVien;
import com.hoangsv.quanlynhanvienv2.R;

import java.util.List;

public class NhanVienAdapter extends ArrayAdapter<NhanVien> {
    Activity context;
    int resource;
    List<NhanVien> objects;
    public NhanVienAdapter(Activity context, int resource, List<NhanVien> objects) {
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
        TextView txtTenNhanVien= (TextView) row.findViewById(R.id.txtTenNhanVien);
        TextView txtGioiTinh= (TextView) row.findViewById(R.id.txtGioiTinh);
        TextView txtSoDienThoai= (TextView) row.findViewById(R.id.txtSoDienThoai);

        NhanVien nhanVien=this.objects.get(position);
        txtTenNhanVien.setText(nhanVien.getTennv());
        txtGioiTinh.setText(nhanVien.getGioitinh());
        txtSoDienThoai.setText(nhanVien.getSdt());

        return row;
    }
}
