package com.hoangsv.quanlynhanvienv2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ChiTietNhanVienActivity extends AppCompatActivity {
    final String DATABASE_NAME="NhanVienDB.sqlite";
    SQLiteDatabase database;

    ImageView imgAnhNhanVienChiTiet;
    TextView txtTenNhanVienChiTiet, txtGioiTinhChiTiet, txtSoDienThoaiChiTiet, txtNgaySinhChiTiet,
            txtDiaChiChiTiet, txtEmailChiTiet, txtPhongBanChiTiet, txtLuongChiTiet;
    Button btnThoatChiTiet;
    int manv=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_nhan_vien);
        addControls();
        addEvents();
    }

    private void addEvents() {
        xuLyLayThongTin();

        btnThoatChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void xuLyLayThongTin() {
        Intent intent=getIntent();
        manv=intent.getIntExtra("MaNV", -1);
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien WHERE MaNV=?",new String[]{manv + ""});
        cursor.moveToFirst();
        int manv = cursor.getInt(0);
        String tennv=cursor.getString(1);
        String sdt=cursor.getString(2);
        String gioitinh=cursor.getString(3);
        String diachi=cursor.getString(4);
        String email=cursor.getString(5);
        int luong = cursor.getInt(6);
        int mapb = cursor.getInt(7);
        String ngaysinh=cursor.getString(8);
        byte[] anh=cursor.getBlob(9);

        txtTenNhanVienChiTiet.setText(tennv);
        txtSoDienThoaiChiTiet.setText(sdt);
        txtGioiTinhChiTiet.setText(gioitinh);
        txtDiaChiChiTiet.setText(diachi);
        txtEmailChiTiet.setText(email);
        txtLuongChiTiet.setText(luong+"");
        txtNgaySinhChiTiet.setText(ngaysinh);
        Bitmap bitmap= BitmapFactory.decodeByteArray(anh,0,anh.length);
        imgAnhNhanVienChiTiet.setImageBitmap(bitmap);
        cursor.close();

        Cursor cursor1 = database.rawQuery("SELECT * FROM PhongBan WHERE MaPB=?",new String[]{mapb+""});
        cursor1.moveToFirst();
        String tenphongban=cursor1.getString(1);

        txtPhongBanChiTiet.setText(tenphongban);
        cursor1.close();


    }

    private void addControls() {
        imgAnhNhanVienChiTiet= (ImageView) findViewById(R.id.imgAnhNhanVienChiTiet);
        txtTenNhanVienChiTiet= (TextView) findViewById(R.id.txtTenNhanVienChiTiet);
        txtGioiTinhChiTiet= (TextView) findViewById(R.id.txtGioiTinhChiTiet);
        txtSoDienThoaiChiTiet= (TextView) findViewById(R.id.txtSoDienThoaiChiTiet);
        txtNgaySinhChiTiet= (TextView) findViewById(R.id.txtNgaySinhChiTiet);
        txtDiaChiChiTiet= (TextView) findViewById(R.id.txtDiaChiChiTiet);
        txtEmailChiTiet= (TextView) findViewById(R.id.txtEmailChiTiet);
        txtPhongBanChiTiet= (TextView) findViewById(R.id.txtPhongBanChiTiet);
        txtLuongChiTiet= (TextView) findViewById(R.id.txtLuongChiTiet);
        btnThoatChiTiet= (Button) findViewById(R.id.btnThoatChiTiet);
    }
}
