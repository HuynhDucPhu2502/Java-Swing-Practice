package me.huynhducphu.quanlylophoc.model;

import me.huynhducphu.quanlylophoc.controller.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class LopHoc {
    private String maSoLop;
    private String tenLop;
    private String giaoVienChuNhiem;
    private ArrayList<SinhVien> dsSinhVien;

    public LopHoc(String maSoLop, String tenLop, String giaoVienChuNhiem) {
        this.maSoLop = maSoLop;
        this.tenLop = tenLop;
        this.giaoVienChuNhiem = giaoVienChuNhiem;
        dsSinhVien = new ArrayList<>(10);
        layDuLieu();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LopHoc lopHoc = (LopHoc) o;
        return Objects.equals(maSoLop, lopHoc.maSoLop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSoLop);
    }

    @Override
    public String toString() {
        return "LopHoc{" +
                "maSoLop='" + maSoLop + '\'' +
                ", tenLop='" + tenLop + '\'' +
                ", giaoVienChuNhiem='" + giaoVienChuNhiem + '\'' +
                '}';
    }

    public String getMaSoLop() {
        return maSoLop;
    }

    public void setMaSoLop(String maSoLop) {
        this.maSoLop = maSoLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public String getGiaoVienChuNhiem() {
        return giaoVienChuNhiem;
    }

    public void setGiaoVienChuNhiem(String giaoVienChuNhiem) {
        this.giaoVienChuNhiem = giaoVienChuNhiem;
    }

    public ArrayList<SinhVien> getDsSinhVien() {
        return dsSinhVien;
    }

    public void setDsSinhVien(ArrayList<SinhVien> dsSinhVien) {
        this.dsSinhVien = dsSinhVien;
    }

    private void layDuLieu()  {
        try (
                Connection connection = DBHelper.getConnection("QuanLyLopHoc", "sa", "sapassword");
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM SinhVien WHERE maLop = ?");
        )
        {
            preparedStatement.setString(1, maSoLop);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                SinhVien sinhVien = new SinhVien(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
                dsSinhVien.add(sinhVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean themSinhVien(SinhVien sinhVien) {
        if (dsSinhVien.contains(sinhVien))
            throw new IllegalArgumentException("Mã số sinh viên này đã tồn tại");
        return dsSinhVien.add(sinhVien);
    }

    public void suaSinhVien(SinhVien newSinhVien) {
        SinhVien sinhVien = dsSinhVien.stream()
                .filter(x -> x.getMaSoSinhVien().equalsIgnoreCase(newSinhVien.getMaSoSinhVien()))
                .findFirst().orElse(null);
        if (sinhVien == null)
            throw new IllegalArgumentException("Không tìm thấy sinh viên");
        sinhVien.setDiaChi(newSinhVien.getDiaChi());
        sinhVien.setEmailLienHe(newSinhVien.getEmailLienHe());
        sinhVien.setHoVaTen(newSinhVien.getHoVaTen());
    }

    public void xoaSinhVien(SinhVien sinhVien) {
        dsSinhVien.remove(sinhVien);
    }
}
