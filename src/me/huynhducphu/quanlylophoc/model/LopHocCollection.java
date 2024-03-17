package me.huynhducphu.quanlylophoc.model;

import me.huynhducphu.quanlylophoc.controller.DBHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LopHocCollection {
    ArrayList<LopHoc> dsLopHoc;

    public LopHocCollection() {
        this.dsLopHoc = new ArrayList<>(10);
        layDuLieu();
    }
    public ArrayList<LopHoc> getDsLopHoc() {
        return dsLopHoc;
    }

    public void setDsLopHoc(ArrayList<LopHoc> dsLopHoc) {
        this.dsLopHoc = dsLopHoc;
    }
    public boolean themLopHoc(LopHoc lopHoc) {
        if (dsLopHoc.contains(lopHoc))
            throw new IllegalArgumentException("Mã lớp không được trùng");
        return dsLopHoc.add(lopHoc);
    }

    public void suaLopHoc(LopHoc newLopHoc) {
        LopHoc lopHoc = dsLopHoc.stream()
                .filter(x -> x.getMaSoLop().equalsIgnoreCase(newLopHoc.getMaSoLop()))
                .findFirst().orElse(null);
        if (lopHoc == null) throw new IllegalArgumentException("Không tìm thấy lớp học");

        lopHoc.setTenLop(newLopHoc.getTenLop());
        lopHoc.setGiaoVienChuNhiem(newLopHoc.getGiaoVienChuNhiem());
    }

    public void xoaLopHoc(LopHoc lopHoc) {
        dsLopHoc.remove(lopHoc);
    }

    private void layDuLieu()  {
        try (
                Connection connection = DBHelper.getConnection("QuanLyLopHoc", "sa", "sapassword");
                Statement statement = connection.createStatement();
                )
        {
            String sql = "select * from LopHoc";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                LopHoc lopHoc = new LopHoc(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3));
                dsLopHoc.add(lopHoc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
