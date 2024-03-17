package me.huynhducphu.quanlylophoc.model;

import java.util.Objects;

public class SinhVien {
    private String maSoSinhVien;
    private String hoVaTen;
    private String emailLienHe;
    private String diaChi;
    private String maLop;

    public SinhVien(String maSoSinhVien, String hoVaTen, String emailLienHe, String diaChi, String maLop) {
        this.maSoSinhVien = maSoSinhVien;
        this.hoVaTen = hoVaTen;
        this.emailLienHe = emailLienHe;
        this.diaChi = diaChi;
        this.maLop = maLop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SinhVien sinhVien = (SinhVien) o;
        return Objects.equals(maSoSinhVien, sinhVien.maSoSinhVien);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSoSinhVien);
    }

    @Override
    public String toString() {
        return "SinhVien{" +
                "maSoSinhVien='" + maSoSinhVien + '\'' +
                ", hoVaTen='" + hoVaTen + '\'' +
                ", emailLienHe='" + emailLienHe + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", maLop='" + maLop + '\'' +
                '}';
    }

    public String getMaSoSinhVien() {
        return maSoSinhVien;
    }

    public void setMaSoSinhVien(String maSoSinhVien) {
        this.maSoSinhVien = maSoSinhVien;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getEmailLienHe() {
        return emailLienHe;
    }

    public void setEmailLienHe(String emailLienHe) {
        this.emailLienHe = emailLienHe;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }
}
