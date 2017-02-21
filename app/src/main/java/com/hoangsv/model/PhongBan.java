package com.hoangsv.model;

public class PhongBan {
    private int maphongban;
    private String tenphongban;
    private int sonhanvien;

    public PhongBan() {
    }

    public PhongBan(int maphongban, String tenphongban, int sonhanvien) {
        this.maphongban = maphongban;
        this.tenphongban = tenphongban;
        this.sonhanvien = sonhanvien;
    }

    public int getMaphongban() {
        return maphongban;
    }

    public void setMaphongban(int maphongban) {
        this.maphongban = maphongban;
    }

    public String getTenphongban() {
        return tenphongban;
    }

    public void setTenphongban(String tenphongban) {
        this.tenphongban = tenphongban;
    }

    public int getSonhanvien() {
        return sonhanvien;
    }

    public void setSonhanvien(int sonhanvien) {
        this.sonhanvien = sonhanvien;
    }
}
