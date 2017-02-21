package com.hoangsv.quanlynhanvienv2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hoangsv.adapter.NhanVienAdapter;
import com.hoangsv.model.NhanVien;

import java.util.ArrayList;

public class NhanVienActivity extends AppCompatActivity {
    final String DATABASE_NAME="NhanVienDB.sqlite";
    SQLiteDatabase database;

    ListView lvNhanVien;
    ArrayList<NhanVien> dsNhanVien;
    NhanVienAdapter adapterNhanVien;

    LinearLayout layoutNhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        addControls();
        addEvents();
    }

    private void addEvents() {
        xuLyDocToanBoCSDL();

        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(NhanVienActivity.this,ChiTietNhanVienActivity.class);
                intent.putExtra("MaNV",dsNhanVien.get(position).getManv());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        xuLyDocToanBoCSDL();
    }

    private void xuLyDocToanBoCSDL() {
        database = Database.initDatabase(this,DATABASE_NAME);
        dsNhanVien.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien",null);
        while (cursor.moveToNext())
        {
            int manv = cursor.getInt(0);
            String tennv=cursor.getString(1);
            String sdt=cursor.getString(2);
            String gioitinh=cursor.getString(3);
            String diachi=cursor.getString(4);
            String email=cursor.getString(5);
            int luong = cursor.getInt(6);
            int mapb = cursor.getInt(7);
            String ngaysinh=cursor.getString(8);

            NhanVien nhanVien=new NhanVien();
            nhanVien.setManv(manv);
            nhanVien.setTennv(tennv);
            nhanVien.setSdt(sdt);
            nhanVien.setGioitinh(gioitinh);
            nhanVien.setDiachi(diachi);
            nhanVien.setEmail(email);
            nhanVien.setLuong(luong);
            nhanVien.setMapb(mapb);
            nhanVien.setNgaysinh(ngaysinh);
            dsNhanVien.add(nhanVien);
        }
        cursor.close();
        adapterNhanVien.notifyDataSetChanged();
    }

    private void addControls() {
        layoutNhanVien= (LinearLayout) findViewById(R.id.layout_nhan_vien);
        lvNhanVien= (ListView) findViewById(R.id.lvNhanVien);
        dsNhanVien=new ArrayList<>();
        adapterNhanVien=new NhanVienAdapter(NhanVienActivity.this,R.layout.nhanvien_item,dsNhanVien);
        lvNhanVien.setAdapter(adapterNhanVien);
        registerForContextMenu(layoutNhanVien);
        registerForContextMenu(lvNhanVien);
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
        if(v.getId()==R.id.layout_nhan_vien){
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
            Intent intent=new Intent(NhanVienActivity.this,ThemNhanVienActivity.class);
            startActivity(intent);
        }
        else if (id==R.id.mnuSua)
        {
            AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final int pos= info.position;
            Intent intent=new Intent(NhanVienActivity.this, SuaNhanVienActivity.class);
            intent.putExtra("MaNV", dsNhanVien.get(pos).getManv());
            startActivity(intent);
        }
        else if (id==R.id.mnuXoa)
        {
            AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            final int pos= info.position;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(android.R.drawable.ic_delete);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa nhân viên: "+'"'+dsNhanVien.get(pos).getTennv()+'"'+" ?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Xóa CSDL
                    SQLiteDatabase database = Database.initDatabase(NhanVienActivity.this,DATABASE_NAME);
                    database.delete("NhanVien", "MaNV=?", new String[]{dsNhanVien.get(pos).getManv()+""});
                    //Load lại CSDL
                    Toast.makeText(NhanVienActivity.this,"Xóa thành công nhân viên: "+dsNhanVien.get(pos).getTennv(),Toast.LENGTH_LONG).show();
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
}
