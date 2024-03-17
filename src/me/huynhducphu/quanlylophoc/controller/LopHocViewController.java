package me.huynhducphu.quanlylophoc.controller;

import me.huynhducphu.quanlylophoc.model.LopHoc;
import me.huynhducphu.quanlylophoc.view.LopHocView;
import me.huynhducphu.quanlylophoc.view.SinhVienView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LopHocViewController {
    private LopHocView lopHocView;

    public LopHocViewController(LopHocView lopHocView) {
        this.lopHocView = lopHocView;
    }

    public void handleHuyBtn() {
        lopHocView.getMaLopTxtField().setText(null);
        lopHocView.getTenLopTxtField().setText(null);
        lopHocView.getGiaoVienChuNhiemTxtField().setText(null);
    }

    public void handleThemBtn() {
        String maLop = lopHocView.getMaLopTxtField().getText().trim();
        String tenLop = lopHocView.getTenLopTxtField().getText().trim();
        String giaoVienChuNhiem = lopHocView.getGiaoVienChuNhiemTxtField().getText().trim();

        LopHoc lopHoc = new LopHoc(maLop, tenLop, giaoVienChuNhiem);
        lopHocView.getLopHocTableModel().themLopHoc(lopHoc);
        try (
                Connection connection = DBHelper.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO LopHoc(maSoLop, tenLop, giaoVienChuNhiem) VALUES (?, ?, ?)"
                )
        )
        {
            preparedStatement.setString(1, maLop);
            preparedStatement.setString(2, tenLop);
            preparedStatement.setString(3, giaoVienChuNhiem);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new  IllegalArgumentException(e.getMessage());
        }
    }

    public void handleSuaBtn(int selectedIndex) {
        String maLop = lopHocView.getMaLopTxtField().getText().trim();
        String tenLop = lopHocView.getTenLopTxtField().getText().trim();
        String giaoVienChuNhiem = lopHocView.getGiaoVienChuNhiemTxtField().getText().trim();

        LopHoc lopHoc = new LopHoc(maLop, tenLop, giaoVienChuNhiem);
        lopHocView.getLopHocTableModel().suaLopHoc(lopHoc, selectedIndex);
        try (
                Connection connection = DBHelper.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE LopHoc SET tenLop = ?, giaoVienChuNhiem = ? WHERE maSoLop = ?"
                )
        )
        {
            preparedStatement.setString(1, tenLop);
            preparedStatement.setString(2, giaoVienChuNhiem);
            preparedStatement.setString(3, maLop);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new  IllegalArgumentException(e.getMessage());
        }
    }

    public void showTxtField(int selectedIndex) {
        if (selectedIndex >= 0) {
            LopHoc lopHoc = lopHocView
                    .getLopHocTableModel()
                    .getLopHocCollection()
                    .getDsLopHoc()
                    .get(selectedIndex);
            lopHocView.getMaLopTxtField().setText(lopHoc.getMaSoLop());
            lopHocView.getTenLopTxtField().setText(lopHoc.getTenLop());
            lopHocView.getGiaoVienChuNhiemTxtField().setText(lopHoc.getGiaoVienChuNhiem());
            lopHocView.getMaLopTxtField().setEditable(false);
        } else  {
            lopHocView.getMaLopTxtField().setEditable(true);
            lopHocView.getMaLopTxtField().setText(null);
            lopHocView.getTenLopTxtField().setText(null);
            lopHocView.getGiaoVienChuNhiemTxtField().setText(null);
        }
    }

    public void handleXemBtn(int selectedIndex) {
        if (selectedIndex != -1) {
            new SinhVienView("Thông tin sinh viên",
                    lopHocView.getLopHocTableModel()
                            .getLopHocCollection()
                            .getDsLopHoc()
                            .get(selectedIndex)
            );
        } else throw new IllegalArgumentException("Không tìm thấy lớp học cần xem");
    }

    public void handleXoaBtn(int selectedIndex) {
        if (selectedIndex == -1)
            throw new IllegalArgumentException("Không tìm thấy dòng chọn trong bảng");
        String maLop = lopHocView.getLopHocTableModel().getLopHocCollection().getDsLopHoc().get(selectedIndex).getMaSoLop();
        lopHocView.getLopHocTableModel().xoaLopHoc(selectedIndex);
        try (
                Connection connection = DBHelper.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM LopHoc WHERE maSoLop = ?")
                )
        {
                preparedStatement.setString(1, maLop);
                preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new  IllegalArgumentException(e.getMessage());
        }
    }


}
