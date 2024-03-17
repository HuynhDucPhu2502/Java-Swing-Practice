package me.huynhducphu.quanlylophoc.controller;

import me.huynhducphu.quanlylophoc.model.SinhVien;
import me.huynhducphu.quanlylophoc.view.SinhVienView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SinhVienViewController {
    private SinhVienView sinhVienView;


    public SinhVienViewController(SinhVienView sinhVienView) {
        this.sinhVienView = sinhVienView;
    }

    public void handleHuyBtn() {
        sinhVienView.getMaSoSinhVienTxtField().setText(null);
        sinhVienView.getDiaChiTxtField().setText(null);
        sinhVienView.getEmailTxtField().setText(null);
        sinhVienView.getHoTenTxtField().setText(null);
    }

    public void showTxtField(int selectedIndex) {
        if (selectedIndex != -1) {
            SinhVien sinhVien = sinhVienView.getLopHoc().getDsSinhVien().get(selectedIndex);
            sinhVienView.getMaSoSinhVienTxtField().setText(sinhVien.getMaSoSinhVien());
            sinhVienView.getDiaChiTxtField().setText(sinhVien.getDiaChi());
            sinhVienView.getEmailTxtField().setText(sinhVien.getEmailLienHe());
            sinhVienView.getHoTenTxtField().setText(sinhVien.getHoVaTen());
            sinhVienView.getMaSoSinhVienTxtField().setEditable(false);
        } else {
            sinhVienView.getMaSoSinhVienTxtField().setEditable(true);
            handleHuyBtn();
        }
    }

    public void handleThemBtn() {
        String maSoSinhVien = sinhVienView.getMaSoSinhVienTxtField().getText().trim();
        String hoVaTen = sinhVienView.getHoTenTxtField().getText().trim();
        String emailLienHe = sinhVienView.getEmailTxtField().getText().trim();
        String diaChi = sinhVienView.getDiaChiTxtField().getText().trim();

        SinhVien sinhVien = new SinhVien(maSoSinhVien, hoVaTen, emailLienHe, diaChi, sinhVienView.getLopHoc().getMaSoLop());
        sinhVienView.getSinhVienTableModel().themSinhVien(sinhVien);
        try (
                Connection connection = DBHelper.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO SinhVien(maSoSinhVien, hoVaTen, emailLienHe, diaChi, maLop) VALUES (?, ?, ?, ? , ?)"
                )
        )
        {
            preparedStatement.setString(1, maSoSinhVien);
            preparedStatement.setString(2, hoVaTen);
            preparedStatement.setString(3, emailLienHe);
            preparedStatement.setString(4, diaChi);
            preparedStatement.setString(5, sinhVienView.getLopHoc().getMaSoLop());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new  IllegalArgumentException(e.getMessage());
        }
    }

    public void handleSuaBtn(int selectedIndex) {
        String maSoSinhVien = sinhVienView.getMaSoSinhVienTxtField().getText().trim();
        String hoVaTen = sinhVienView.getHoTenTxtField().getText().trim();
        String emailLienHe = sinhVienView.getEmailTxtField().getText().trim();
        String diaChi = sinhVienView.getDiaChiTxtField().getText().trim();

        SinhVien sinhVien = new SinhVien(maSoSinhVien, hoVaTen, emailLienHe, diaChi, sinhVienView.getLopHoc().getMaSoLop());
        sinhVienView.getSinhVienTableModel().suaSinhVien(sinhVien, selectedIndex);
        try (
                Connection connection = DBHelper.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE SinhVien SET hoVaTen = ?, emailLienHe = ?, diaChi = ? WHERE maSoSinhVien = ?"
                )
        )
        {
            preparedStatement.setString(1, hoVaTen);
            preparedStatement.setString(2, emailLienHe);
            preparedStatement.setString(3, diaChi);
            preparedStatement.setString(4, maSoSinhVien);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new  IllegalArgumentException(e.getMessage());
        }
    }

    public void handleXoaBtn(int selectedIndex) {
        if (selectedIndex == -1)
            throw new IllegalArgumentException("Không tìm thấy dòng chọn trong bảng");
        String maSoSinhVien = sinhVienView.getLopHoc().getDsSinhVien().get(selectedIndex).getMaSoSinhVien();
        sinhVienView.getSinhVienTableModel().xoaSinhVien(selectedIndex);
        try (
                Connection connection = DBHelper.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM SinhVien WHERE maSoSinhVien = ?")
        )
        {
            preparedStatement.setString(1, maSoSinhVien);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new  IllegalArgumentException(e.getMessage());
        }
    }
}
