package com.hoangsv.model;

public class NhanVien {
    private int manv;
    private String tennv;
    private String sdt;
    private String gioitinh;
    private String diachi;
    private String email;
    private int luong;
    private int mapb;
    private String ngaysinh;
    private byte[] anh;

    public NhanVien() {
    }

    public NhanVien(int manv, String tennv, String sdt, String gioitinh, String diachi, String email, int luong, int mapb, String ngaysinh, byte[] anh) {
        this.manv = manv;
        this.tennv = tennv;
        this.sdt = sdt;
        this.gioitinh = gioitinh;
        this.diachi = diachi;
        this.email = email;
        this.luong = luong;
        this.mapb = mapb;
        this.ngaysinh = ngaysinh;
        this.anh=anh;
    }

    public int getManv() {
        return manv;
    }

    public void setManv(int manv) {
        this.manv = manv;
    }

    public String getTennv() {
        return tennv;
    }

    public void setTennv(String tennv) {
        this.tennv = tennv;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }

    public int getMapb() {
        return mapb;
    }

    public void setMapb(int mapb) {
        this.mapb = mapb;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }
}
