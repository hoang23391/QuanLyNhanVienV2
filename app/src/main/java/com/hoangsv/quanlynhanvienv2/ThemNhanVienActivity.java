package com.hoangsv.quanlynhanvienv2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.hoangsv.adapter.SpinnerPhongBanAdapter;
import com.hoangsv.model.PhongBan;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ThemNhanVienActivity extends AppCompatActivity {
    final String DATABASE_NAME="NhanVienDB.sqlite";
    SQLiteDatabase database;

    EditText txtTenNhanVienThemNV, txtNgaySinhThemNV, txtSoDienThoaiThemNV, txtEmailThemNV, txtDiaChiThemNV, txtLuongThemNV;
    RadioGroup radGioiTinh;
    RadioButton radNam, radNu;
    Button btnThemNhanVien, btnHuyThemNhanVien,btnChupHinhThemNV, btnChonHinhThemNV;
    ImageView imgAnhNhanVienThemNV;
    Spinner spPhongBan;
    ArrayList<PhongBan>dsPhongBan;
    SpinnerPhongBanAdapter adapterSpinnerPhongBan;

    final int REQUEST_TAKE_PHOTO =123;
    final int REQUEST_CHOOSE_PHOTO =321;

    int vitri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhan_vien);
        addControls();
        addEvents();
    }

    private void addEvents() {
        xuLyLoadCSDLChoSpinner();
        btnThemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThemNhanVien();
            }
        });
        btnHuyThemNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spPhongBan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vitri=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnChonHinhThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonHinh();
            }
        });
        btnChupHinhThemNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChupHinh();
            }
        });
    }

    private void xuLyChupHinh() {
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_TAKE_PHOTO);
    }

    private void xuLyChonHinh() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_CHOOSE_PHOTO)
            {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    imgAnhNhanVienThemNV.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == REQUEST_TAKE_PHOTO)
            {
                Bitmap bitmap= (Bitmap) data.getExtras().get("data");
                imgAnhNhanVienThemNV.setImageBitmap(bitmap);
            }
        }
    }

    private void xuLyThemNhanVien() {
        String tennv=txtTenNhanVienThemNV.getText().toString();
        String ngaysinh=txtNgaySinhThemNV.getText().toString();
        String sdt=txtSoDienThoaiThemNV.getText().toString();

        int rdID=radGioiTinh.getCheckedRadioButtonId();
        RadioButton rdChecked= (RadioButton) findViewById(rdID);
        String gioitinh=rdChecked.getText().toString();

        String email=txtEmailThemNV.getText().toString();
        String diachi=txtDiaChiThemNV.getText().toString();
        int luong=Integer.parseInt(txtLuongThemNV.getText().toString());
        byte[] anh=getByteArrayFromImageView(imgAnhNhanVienThemNV);

        //int selectedItemPosition=spPhongBan.getSelectedItemPosition();
        //int mapb=dsPhongBan.get(selectedItemPosition).getMaphongban();
        //String tenpb1=dsPhongBan.get(selectedItemPosition).getTenphongban();
        int mapb=dsPhongBan.get(vitri).getMaphongban();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TenNV", tennv);
            contentValues.put("SDT", sdt);
            contentValues.put("GioiTinh", gioitinh);
            contentValues.put("DiaChi", diachi);
            contentValues.put("Email", email);
            contentValues.put("Luong", luong);
            contentValues.put("MaPB", mapb);
            contentValues.put("NgaySinh", ngaysinh);
            contentValues.put("Anh",anh);

            database = Database.initDatabase(this, DATABASE_NAME);
            database.insert("NhanVien", null, contentValues);
            Toast.makeText(ThemNhanVienActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();

            finish();
        }
        catch (Exception ex)
        {
            Log.e("LOI",ex.toString());
        }

    }

    private byte[] getByteArrayFromImageView(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    private void xuLyLoadCSDLChoSpinner() {
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM PhongBan ",null);
        while (cursor.moveToNext())
        {
            int mapb=cursor.getInt(0);
            String tenpb=cursor.getString(1);

            PhongBan phongBan=new PhongBan();
            phongBan.setMaphongban(mapb);
            phongBan.setTenphongban(tenpb);
            dsPhongBan.add(phongBan);

        }
        cursor.close();
        adapterSpinnerPhongBan.notifyDataSetChanged();
    }

    private void addControls() {
        imgAnhNhanVienThemNV= (ImageView) findViewById(R.id.imgAnhNhanVienThemNV);
        txtTenNhanVienThemNV= (EditText) findViewById(R.id.txtTenNhanVienThemNV);
        txtNgaySinhThemNV= (EditText) findViewById(R.id.txtNgaySinhThemNV);
        txtSoDienThoaiThemNV= (EditText) findViewById(R.id.txtSoDienThoaiThemNV);
        txtEmailThemNV= (EditText) findViewById(R.id.txtEmailThemNV);
        txtDiaChiThemNV= (EditText) findViewById(R.id.txtDiaChiThemNV);
        txtLuongThemNV= (EditText) findViewById(R.id.txtLuongThemNV);
        radGioiTinh= (RadioGroup) findViewById(R.id.radGioiTinh);
        radNam= (RadioButton) findViewById(R.id.radNam);
        radNu= (RadioButton) findViewById(R.id.radNu);
        btnThemNhanVien= (Button) findViewById(R.id.btnThemNhanVien);
        btnHuyThemNhanVien= (Button) findViewById(R.id.btnHuyThemNhanVien);
        btnChonHinhThemNV= (Button) findViewById(R.id.btnChonHinhThemNV);
        btnChupHinhThemNV= (Button) findViewById(R.id.btnChupHinhThemNV);


        spPhongBan= (Spinner) findViewById(R.id.spPhongBan);
        dsPhongBan=new ArrayList<>();
        adapterSpinnerPhongBan=new SpinnerPhongBanAdapter(ThemNhanVienActivity.this,R.layout.spinner_item,dsPhongBan);
        adapterSpinnerPhongBan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhongBan.setAdapter(adapterSpinnerPhongBan);
    }
}
