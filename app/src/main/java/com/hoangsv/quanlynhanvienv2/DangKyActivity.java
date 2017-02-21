package com.hoangsv.quanlynhanvienv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DangKyActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText txtTenTaiKhoanDK, txtMatKhauDK, txtNhapLaiMatKhauDK;
    Button btnDangKy, btnThoatDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        addControls();
        addEvents();
    }

    private void addEvents() {
        finish();
    }

    private void addControls() {
        txtTenTaiKhoanDK= (EditText) findViewById(R.id.txtTenTaiKhoanDK);
        txtMatKhauDK= (EditText) findViewById(R.id.txtMatKhauDK);
        txtNhapLaiMatKhauDK= (EditText) findViewById(R.id.txtNhapLaiMatKhauDK);
        btnDangKy= (Button) findViewById(R.id.btnDangKy);
        btnThoatDK= (Button) findViewById(R.id.btnThoatDK);

        sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);

        String taikhoan, matkhau;
        taikhoan=sharedPreferences.getString("taikhoan","");
        matkhau=sharedPreferences.getString("matkhau","");
        if (taikhoan.trim().length()<=0 || matkhau.trim().length()<=0)
        {
            Toast.makeText(getApplicationContext(),"Bạn không thể đăng ký vì đã có tài khoản",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
