package com.hoangsv.quanlynhanvienv2;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ThayDoiMatKhauActivity extends AppCompatActivity {
    ToggleButton btnOnOff;
    EditText txtTenDangNhapTD, txtMatKhauTD, txtTenDangNhapMoiTD, txtMatKhauMoiTD, txtNhapLaiMatKhauMoiTD;
    Button btnSuaTD, btnHuyTD;
    TableRow tar1, tar2, tar3, tar4, tar5, tar6, tar7;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_mat_khau);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    tar2.setVisibility(View.VISIBLE);
                    tar3.setVisibility(View.VISIBLE);
                    tar4.setVisibility(View.VISIBLE);
                    tar5.setVisibility(View.VISIBLE);
                    tar6.setVisibility(View.VISIBLE);
                    tar7.setVisibility(View.VISIBLE);
                }
                else
                {
                    tar2.setVisibility(View.INVISIBLE);
                    tar3.setVisibility(View.INVISIBLE);
                    tar4.setVisibility(View.INVISIBLE);
                    tar5.setVisibility(View.INVISIBLE);
                    tar6.setVisibility(View.INVISIBLE);
                    tar7.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnHuyTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSuaTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThayDoiMatKhau();
            }
        });

    }

    private void xuLyThayDoiMatKhau() {
        String taikhoan, matkhau, tentaikhoannhap, matkhaunhap;
        taikhoan=sharedPreferences.getString("TaiKhoan","");
        matkhau=sharedPreferences.getString("MatKhau","");
        tentaikhoannhap=txtTenDangNhapTD.getText().toString();
        matkhaunhap=txtMatKhauTD.getText().toString();

        if (tentaikhoannhap.equals(taikhoan)&&matkhaunhap.equals(matkhau))
        {
            String tentaikhoanmoi, matkhaumoi, matkhaumoi2;
            tentaikhoanmoi=txtTenDangNhapMoiTD.getText().toString();
            matkhaumoi=txtMatKhauMoiTD.getText().toString();
            matkhaumoi2=txtNhapLaiMatKhauMoiTD.getText().toString();
            if (tentaikhoanmoi.equals(""))
            {
                Toast.makeText(ThayDoiMatKhauActivity.this,"Bạn chưa nhập tên tài khoản mới",Toast.LENGTH_LONG).show();
            }
            else
            {
                if (matkhaumoi.equals(matkhaumoi2))
                {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("TaiKhoan",tentaikhoanmoi);
                    editor.putString("MatKhau",matkhaumoi);
                    editor.commit();
                    Toast.makeText(ThayDoiMatKhauActivity.this,"Thay đổi mật khẩu thành công",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(ThayDoiMatKhauActivity.this,"Mật khẩu mới không giống nhau",Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            Toast.makeText(ThayDoiMatKhauActivity.this,"Bạn nhập sai tài khoản hoặc mật khẩu",Toast.LENGTH_LONG).show();
        }
    }

    private void addControls() {
        sharedPreferences=getSharedPreferences("config", 0);

        btnOnOff= (ToggleButton) findViewById(R.id.btnonoff);
        btnSuaTD= (Button) findViewById(R.id.btnSuaTD);
        btnHuyTD= (Button) findViewById(R.id.btnHuyTD);
        txtTenDangNhapTD= (EditText) findViewById(R.id.txtTenDangNhapTD);
        txtMatKhauTD= (EditText) findViewById(R.id.txtMatKhauTD);
        txtTenDangNhapMoiTD= (EditText) findViewById(R.id.txtTenDangNhapMoiTD);
        txtMatKhauMoiTD= (EditText) findViewById(R.id.txtMatKhauMoiTD);
        txtNhapLaiMatKhauMoiTD= (EditText) findViewById(R.id.txtNhapLaiMatKhauMoiTD);
        tar1= (TableRow) findViewById(R.id.tar1);
        tar2= (TableRow) findViewById(R.id.tar2);
        tar3= (TableRow) findViewById(R.id.tar3);
        tar4= (TableRow) findViewById(R.id.tar4);
        tar5= (TableRow) findViewById(R.id.tar5);
        tar6= (TableRow) findViewById(R.id.tar6);
        tar7= (TableRow) findViewById(R.id.tar7);

        tar2.setVisibility(View.INVISIBLE);
        tar3.setVisibility(View.INVISIBLE);
        tar4.setVisibility(View.INVISIBLE);
        tar5.setVisibility(View.INVISIBLE);
        tar6.setVisibility(View.INVISIBLE);
        tar7.setVisibility(View.INVISIBLE);

    }
}
