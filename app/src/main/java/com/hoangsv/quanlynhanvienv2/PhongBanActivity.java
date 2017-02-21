package com.hoangsv.quanlynhanvienv2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hoangsv.adapter.PhongBanAdapter;
import com.hoangsv.model.NhanVien;
import com.hoangsv.model.PhongBan;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class PhongBanActivity extends AppCompatActivity {
    final String DATABASE_NAME="NhanVienDB.sqlite";
    SQLiteDatabase database;

    ListView lvPhongBan;
    ArrayList<PhongBan> dsPhongBan;
    PhongBanAdapter adapterPhongBan;

    ArrayList<NhanVien> dsNhanVien;

    LinearLayout layoutPhongBan;
    Dialog dialogThemPhongBan,dialogSuaPhongBan, dialogLienHe;
    EditText txtTenPhongBan,txtMaPhongBanSua,txtTenPhongBanSua,txtTenPhongBanMoiSua;
    Button btnThemPhongBan,btnSuaPhongBan,btnHuyThem,btnHuySua;
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_ban);
        addControls();
        addEvents();
    }

    private void addEvents() {
        xuLyDocToanBoCSDL();
        btnThemPhongBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyThemPhongBan();
            }
        });
        btnHuyThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemPhongBan.dismiss();
            }
        });
        btnHuySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSuaPhongBan.dismiss();
            }
        });
        lvPhongBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(PhongBanActivity.this,NhanVienPhongBanActivity.class);
                intent.putExtra("maphongban",dsPhongBan.get(position).getMaphongban());
                startActivity(intent);
            }
        });
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.mnuNhanVien)
                {
                    Intent intent=new Intent(PhongBanActivity.this,NhanVienActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.mnuPhongBan)
                {

                }
                else if (id==R.id.mnuHeThong)
                {
                    Intent intent=new Intent(PhongBanActivity.this,ThayDoiMatKhauActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.mnuLienHe)
                {
                    dialogLienHe.show();
                }
                return true;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        xuLyDocToanBoCSDL();
    }

    private void xuLyThemPhongBan() {
        String tenphongban=txtTenPhongBan.getText().toString();
        if (tenphongban.equals(""))
        {
            Toast.makeText(PhongBanActivity.this,"Bạn chưa nhập tên phòng ban",Toast.LENGTH_LONG).show();
        }
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("TenPB", tenphongban);
            database = Database.initDatabase(this, DATABASE_NAME);
            database.insert("PhongBan", null, contentValues);
            dialogThemPhongBan.dismiss();
            Toast.makeText(PhongBanActivity.this,"Thêm thành công phòng ban: "+tenphongban,Toast.LENGTH_LONG).show();

            xuLyDocToanBoCSDL();
        }
    }

    private void xuLyDocToanBoCSDL() {
        database = Database.initDatabase(this,DATABASE_NAME);
        dsPhongBan.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM PhongBan",null);
        while (cursor.moveToNext())
        {
            int maphongban = cursor.getInt(0);
            String tenphongban=cursor.getString(1);

            Cursor cursor1=database.rawQuery("SELECT * FROM NhanVien WHERE MaPB=?",new String[]{maphongban + ""});
            while (cursor1.moveToNext())
            {
                int manv=cursor1.getInt(0);

                NhanVien nhanVien=new NhanVien();
                nhanVien.setManv(manv);
                dsNhanVien.add(nhanVien);
            }
            cursor1.close();

            int sonhanvien=dsNhanVien.size();

            PhongBan phongBan=new PhongBan();
            phongBan.setMaphongban(maphongban);
            phongBan.setTenphongban(tenphongban);
            phongBan.setSonhanvien(sonhanvien);
            dsPhongBan.add(phongBan);
            dsNhanVien.clear();

        }
        cursor.close();

        adapterPhongBan.notifyDataSetChanged();
    }

    private void addControls() {
        dsNhanVien=new ArrayList<>();

        layoutPhongBan= (LinearLayout) findViewById(R.id.layout_phong_ban);
        lvPhongBan= (ListView) findViewById(R.id.lvPhongBan);
        dsPhongBan=new ArrayList<>();
        adapterPhongBan=new PhongBanAdapter(PhongBanActivity.this,R.layout.phongban_item,dsPhongBan);
        lvPhongBan.setAdapter(adapterPhongBan);
        registerForContextMenu(layoutPhongBan);
        registerForContextMenu(lvPhongBan);

        dialogThemPhongBan = new Dialog(PhongBanActivity.this);
        dialogThemPhongBan.setContentView(R.layout.them_phongban);
        dialogThemPhongBan.setTitle(R.string.themphongban);
        dialogThemPhongBan.getWindow().setTitleColor(getResources().getColor(android.R.color.holo_blue_dark));
        dialogThemPhongBan.setCanceledOnTouchOutside(true);
        txtTenPhongBan= (EditText) dialogThemPhongBan.findViewById(R.id.txtTenPhongBan);
        btnThemPhongBan= (Button) dialogThemPhongBan.findViewById(R.id.btnThemPhongBan);
        btnHuyThem= (Button) dialogThemPhongBan.findViewById(R.id.btnHuyThem);

        dialogLienHe = new Dialog(PhongBanActivity.this);
        dialogLienHe.setContentView(R.layout.lienhe);
        dialogLienHe.setTitle(R.string.lienhe);
        dialogLienHe.getWindow().setTitleColor(getResources().getColor(android.R.color.holo_blue_dark));
        dialogLienHe.setCanceledOnTouchOutside(true);

        dialogSuaPhongBan=new Dialog(PhongBanActivity.this);
        dialogSuaPhongBan.setContentView(R.layout.sua_phongban);
        dialogSuaPhongBan.setTitle(R.string.suaphongban);
        dialogSuaPhongBan.getWindow().setTitleColor(getResources().getColor(android.R.color.holo_blue_dark));
        dialogSuaPhongBan.setCanceledOnTouchOutside(true);
        txtMaPhongBanSua= (EditText) dialogSuaPhongBan.findViewById(R.id.txtMaPhongBanSua);
        txtTenPhongBanSua= (EditText) dialogSuaPhongBan.findViewById(R.id.txtTenPhongBanSua);
        txtTenPhongBanMoiSua= (EditText) dialogSuaPhongBan.findViewById(R.id.txtTenPhongBanMoiSua);
        btnSuaPhongBan= (Button) dialogSuaPhongBan.findViewById(R.id.btnSuaPhongBan);
        btnHuySua= (Button) dialogSuaPhongBan.findViewById(R.id.btnHuySua);
        txtMaPhongBanSua.setEnabled(false);
        txtTenPhongBanSua.setEnabled(false);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        disableShiftMode(bottomNavigation);
    }

    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("NoSuchFieldException", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("IllegalAccessException", "Unable to change value of shift mode");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.contextmenu_chinhsua,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Chỉnh sửa");
        menu.setHeaderIcon(android.R.drawable.ic_menu_edit);
        //Sửa lỗi hiện 2 lần của cả listview lẫn layout
        //Bấm ra ngoài thì chỉ có 3 menu item trong khi ở trong là 6
        if(v.getId()==R.id.layout_phong_ban){
            int a,b,c;
            a=3;
            b=4;
            c=5;
            if (menu.size()<=3) {
                a = a - 3;
                b = b - 3;
                c = c - 3;
                menu.getItem(b).setVisible(false);
                menu.getItem(c).setVisible(false);
            }
            else
            {
                menu.getItem(a).setVisible(false);
                menu.getItem(b).setVisible(false);
                menu.getItem(c).setVisible(false);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id=item.getItemId();

        if (id==R.id.mnuThem)
        {
            dialogThemPhongBan.show();
            txtTenPhongBan.setText("");

        }
        else if (id==R.id.mnuSua)
        {
            AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final int pos= info.position;
            dialogSuaPhongBan.show();

            final int maphongbansua=dsPhongBan.get(pos).getMaphongban();
            final String tenphongbansua=dsPhongBan.get(pos).getTenphongban();
            txtMaPhongBanSua.setText(String.valueOf(maphongbansua));
            txtTenPhongBanSua.setText(tenphongbansua);
            txtTenPhongBanMoiSua.setText("");


            btnSuaPhongBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tenphongbanmoisua=txtTenPhongBanMoiSua.getText().toString();
                    if (tenphongbanmoisua.equals(""))
                    {
                        Toast.makeText(PhongBanActivity.this, "Bạn chưa nhập tên phòng ban mới", Toast.LENGTH_LONG).show();
                    }
                    else {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("MaPB", maphongbansua);
                        contentValues.put("TenPB", tenphongbanmoisua);
                        database = Database.initDatabase(PhongBanActivity.this, DATABASE_NAME);
                        database.update("PhongBan", contentValues, "MaPB=?", new String[]{dsPhongBan.get(pos).getMaphongban() + ""});
                        dialogSuaPhongBan.dismiss();
                        Toast.makeText(PhongBanActivity.this, "Thay đổi thông tin phòng ban thành công", Toast.LENGTH_LONG).show();
                        xuLyDocToanBoCSDL();
                    }
                }
            });
        }
        else if (id==R.id.mnuXoa)
        {
            AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final int pos= info.position;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_delete);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa phòng ban: "+'"'+dsPhongBan.get(pos).getTenphongban()+'"'+" ?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Xóa CSDL
                    SQLiteDatabase database = Database.initDatabase(PhongBanActivity.this,DATABASE_NAME);
                    database.delete("PhongBan", "MaPB=?", new String[]{dsPhongBan.get(pos).getMaphongban()+""});
                    //Load lại CSDL
                    Toast.makeText(PhongBanActivity.this,"Xóa thành công phòng ban: "+dsPhongBan.get(pos).getTenphongban(),Toast.LENGTH_LONG).show();
                    xuLyDocToanBoCSDL();
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }

        return super.onContextItemSelected(item);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.phongban_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.mnuNhanVien)
        {
            Intent intent=new Intent(PhongBanActivity.this,NhanVienActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.mnuPhongBan)
        {

        }
        else if (id==R.id.mnuHeThong)
        {
            Intent intent=new Intent(PhongBanActivity.this,ThayDoiMatKhauActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.mnuLienHe)
        {
            dialogLienHe.show();
        }
        return super.onOptionsItemSelected(item);
    }*/
}
