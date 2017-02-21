package com.hoangsv.quanlynhanvienv2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText txtTenTaiKhoanDK, txtMatKhauDK, txtNhapLaiMatKhauDK;
    EditText txtTenTaiKhoanDN, txtMatKhauDN;
    Button btnDangKy, btnThoatDK;
    Button btnDangNhap, btnThoatDN;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taikhoanconfig, matkhauconfig, taikhoandn, matkhaudn;
                taikhoanconfig=sharedPreferences.getString("TaiKhoan","");
                matkhauconfig=sharedPreferences.getString("MatKhau","");
                taikhoandn=txtTenTaiKhoanDN.getText().toString();
                matkhaudn=txtMatKhauDN.getText().toString();
                if (taikhoandn.equals(taikhoanconfig)&&matkhaudn.equals(matkhauconfig))
                {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,PhongBanActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnThoatDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matkhaudk, nhaplaimatkhaudk;
                matkhaudk=txtMatKhauDK.getText().toString();
                nhaplaimatkhaudk=txtNhapLaiMatKhauDK.getText().toString();
                if (matkhaudk.equals(nhaplaimatkhaudk))
                {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TaiKhoan",txtTenTaiKhoanDK.getText().toString());
                    editor.putString("MatKhau",txtMatKhauDK.getText().toString());
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"Đăng ký thành công",Toast.LENGTH_LONG).show();
                    txtTenTaiKhoanDN.setText(txtTenTaiKhoanDK.getText().toString());
                    txtMatKhauDN.setText(txtMatKhauDK.getText().toString());
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Mật khẩu nhập lại không khớp",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnThoatDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void addControls() {
        sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.activity_dang_ky);
        dialog.setTitle(R.string.dangky);
        dialog.getWindow().setTitleColor(getResources().getColor(android.R.color.holo_blue_dark));
        dialog.setCanceledOnTouchOutside(true);

        txtTenTaiKhoanDK= (EditText) dialog.findViewById(R.id.txtTenTaiKhoanDK);
        txtMatKhauDK= (EditText) dialog.findViewById(R.id.txtMatKhauDK);
        txtNhapLaiMatKhauDK= (EditText) dialog.findViewById(R.id.txtNhapLaiMatKhauDK);
        btnDangKy= (Button) dialog.findViewById(R.id.btnDangKy);
        btnThoatDK= (Button) dialog.findViewById(R.id.btnThoatDK);

        txtTenTaiKhoanDN= (EditText) findViewById(R.id.txtTenTaiKhoanDN);
        txtMatKhauDN= (EditText) findViewById(R.id.txtMatKhauDN);
        btnDangNhap= (Button) findViewById(R.id.btnDangNhap);
        btnThoatDN= (Button) findViewById(R.id.btnThoatDN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.mnuDangKy)
        {
            String taikhoan, matkhau;
            taikhoan=sharedPreferences.getString("TaiKhoan","");
            matkhau=sharedPreferences.getString("MatKhau","");
            if (taikhoan.trim().length()==0 || matkhau.trim().length()==0)
            {
                dialog.show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Bạn không thể đăng ký vì đã có tài khoản",Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
