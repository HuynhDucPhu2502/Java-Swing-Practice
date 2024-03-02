package me.huynhducphu.quanlynhanvien.model;

import java.io.Serializable;
import java.util.ArrayList;

public class NhanVienCollection implements Serializable {
    private ArrayList<NhanVien> nhanVienList;

    public NhanVienCollection() {
        this.nhanVienList = new ArrayList<>(10);
    }

    public ArrayList<NhanVien> getNhanVienList() {
        return nhanVienList;
    }

    public void setNhanVienList(ArrayList<NhanVien> nhanVienList) {
        this.nhanVienList = nhanVienList;
    }

    public void addNhanVien(NhanVien nhanVien) {
        if (nhanVienList.contains(nhanVien))
            throw new IllegalArgumentException("Nhân viên đã tồn tại");
        nhanVienList.add(nhanVien);
    }

    public void xoaNhanVien(int selectedRow) {
        nhanVienList.remove(selectedRow);
    }
}
