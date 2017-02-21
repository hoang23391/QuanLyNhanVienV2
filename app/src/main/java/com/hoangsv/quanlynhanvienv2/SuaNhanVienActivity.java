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

public class SuaNhanVienActivity extends AppCompatActivity {
    final String DATABASE_NAME="NhanVienDB.sqlite";
    SQLiteDatabase database;

    EditText txtTenNhanVienSuaNV, txtNgaySinhSuaNV, txtSoDienThoaiSuaNV, txtEmailSuaNV, txtDiaChiSuaNV, txtLuongSuaNV;
    RadioGroup radGioiTinhSuaNV;
    RadioButton radNamSuaNV, radNuSuaNV;
    Button btnSuaNhanVien, btnHuySuaNhanVien, btnChupHinhSuaNV, btnChonHinhSuaNV;
    ImageView imgAnhNhanVienChiTietSuaNV;
    Spinner spPhongBanSuaNV;
    ArrayList<PhongBan>dsPhongBan;
    SpinnerPhongBanAdapter adapterSpinnerPhongBan;

    final int REQUEST_TAKE_PHOTO =123;
    final int REQUEST_CHOOSE_PHOTO =321;

    int vitri;
    int manv=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_nhan_vien);
        addControls();
        addEvents();
    }

    private void addEvents() {
        xuLyLoadCSDLChoSpinner();
        xuLyLayThongTin();
        btnSuaNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySuaThongTinNhanVien();
            }
        });
        btnHuySuaNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spPhongBanSuaNV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vitri=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnChonHinhSuaNV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonHinh();
            }
        });
        btnChupHinhSuaNV.setOnClickListener(new View.OnClickListener() {
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
                    imgAnhNhanVienChiTietSuaNV.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode == REQUEST_TAKE_PHOTO)
            {
                Bitmap bitmap= (Bitmap) data.getExtras().get("data");
                imgAnhNhanVienChiTietSuaNV.setImageBitmap(bitmap);
            }
        }
    }

    private void xuLySuaThongTinNhanVien() {
        String tennv=txtTenNhanVienSuaNV.getText().toString();
        String sdt=txtSoDienThoaiSuaNV.getText().toString();
        String ngaysinh=txtNgaySinhSuaNV.getText().toString();

        int rdID=radGioiTinhSuaNV.getCheckedRadioButtonId();
        RadioButton rdChecked= (RadioButton) findViewById(rdID);
        String gioitinh=rdChecked.getText().toString();

        String diachi=txtDiaChiSuaNV.getText().toString();
        String email=txtEmailSuaNV.getText().toString();
        int luong=Integer.parseInt(txtLuongSuaNV.getText().toString());
        byte[] anh=getByteArrayFromImageView(imgAnhNhanVienChiTietSuaNV);

        //int selectedItemPosition=spPhongBanSuaNV.getSelectedItemPosition();
        //int mapb=dsPhongBan.get(selectedItemPosition).getMaphongban();
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
            database.update("NhanVien", contentValues, "MaNV=?",new String[]{manv+""});
            Toast.makeText(SuaNhanVienActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();

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

        txtTenNhanVienSuaNV.setText(tennv);
        txtSoDienThoaiSuaNV.setText(sdt);

        if (gioitinh.equals(radNamSuaNV.getText().toString()))
        {
            radNamSuaNV.setChecked(true);
        }
        else  if (gioitinh.equals(radNuSuaNV.getText().toString()))
        {
            radNuSuaNV.setChecked(true);
        }
        else
        {

        }

        txtDiaChiSuaNV.setText(diachi);
        txtEmailSuaNV.setText(email);
        txtLuongSuaNV.setText(luong+"");
        txtNgaySinhSuaNV.setText(ngaysinh);
        Bitmap bitmap= BitmapFactory.decodeByteArray(anh,0,anh.length);
        imgAnhNhanVienChiTietSuaNV.setImageBitmap(bitmap);

        spPhongBanSuaNV.setSelection(0);
        //Chưa tìm ra cách giải quyết :(

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
        imgAnhNhanVienChiTietSuaNV= (ImageView) findViewById(R.id.imgAnhNhanVienChiTietSuaNV);
        txtTenNhanVienSuaNV= (EditText) findViewById(R.id.txtTenNhanVienSuaNV);
        txtNgaySinhSuaNV= (EditText) findViewById(R.id.txtNgaySinhSuaNV);
        txtSoDienThoaiSuaNV= (EditText) findViewById(R.id.txtSoDienThoaiSuaNV);
        txtEmailSuaNV= (EditText) findViewById(R.id.txtEmailSuaNV);
        txtDiaChiSuaNV= (EditText) findViewById(R.id.txtDiaChiSuaNV);
        txtLuongSuaNV= (EditText) findViewById(R.id.txtLuongSuaNV);
        radGioiTinhSuaNV= (RadioGroup) findViewById(R.id.radGioiTinhSuaNV);
        radNamSuaNV= (RadioButton) findViewById(R.id.radNamSuaNV);
        radNuSuaNV= (RadioButton) findViewById(R.id.radNuSuaNV);
        btnSuaNhanVien= (Button) findViewById(R.id.btnSuaNhanVien);
        btnHuySuaNhanVien= (Button) findViewById(R.id.btnHuySuaNhanVien);
        btnChonHinhSuaNV= (Button) findViewById(R.id.btnChonHinhSuaNV);
        btnChupHinhSuaNV= (Button) findViewById(R.id.btnChupHinhSuaNV);

        spPhongBanSuaNV= (Spinner) findViewById(R.id.spPhongBanSuaNV);
        dsPhongBan=new ArrayList<>();
        adapterSpinnerPhongBan=new SpinnerPhongBanAdapter(SuaNhanVienActivity.this,R.layout.spinner_item,dsPhongBan);
        adapterSpinnerPhongBan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhongBanSuaNV.setAdapter(adapterSpinnerPhongBan);
    }
}
