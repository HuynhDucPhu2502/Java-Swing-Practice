package me.huynhducphu.quanlylophoc.controller.tablemodel;

import me.huynhducphu.quanlylophoc.model.LopHoc;
import me.huynhducphu.quanlylophoc.model.SinhVien;

import javax.swing.table.AbstractTableModel;

public class SinhVienTableModel extends AbstractTableModel {
    private final String column[] = {"Mã số sinh viên", "Họ và tên", "Email liên hệ", "Địa chỉ"};
    private LopHoc lopHoc;

    public SinhVienTableModel(LopHoc lopHoc) {
        this.lopHoc = lopHoc;
    }

    @Override
    public String getColumnName(int column) {
        return this.column[column];
    }

    @Override
    public int getRowCount() {
        return lopHoc.getDsSinhVien().size();
    }

    @Override
    public int getColumnCount() {
        return column.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SinhVien sinhVien = lopHoc.getDsSinhVien().get(rowIndex);
        switch (columnIndex) {
            case 0: return sinhVien.getMaSoSinhVien();
            case 1: return sinhVien.getHoVaTen();
            case 2: return sinhVien.getEmailLienHe();
            case 3: return sinhVien.getDiaChi();
            default: return null;
        }
    }

    public void themSinhVien(SinhVien sinhVien) {
        if (sinhVien == null) throw new IllegalArgumentException("Lớp học không hợp lệ");
        if (lopHoc.themSinhVien(sinhVien)) {
            int lastIndex = lopHoc.getDsSinhVien().size() - 1;
            fireTableRowsInserted(lastIndex, lastIndex);
        }
    }

    public void suaSinhVien(SinhVien newSinhVien, int selectedIndex) {
        if (selectedIndex == -1)
            throw new IllegalArgumentException("Không tìm thấy dòng chọn trong bảng");
        if (newSinhVien == null)
            throw new IllegalArgumentException("Sinh viên không hợp lệ");
        lopHoc.suaSinhVien(newSinhVien);
        fireTableRowsDeleted(selectedIndex, selectedIndex);
    }

    public void xoaSinhVien(int selectedIndex) {
        if (selectedIndex == -1)
            throw new IllegalArgumentException("Không tìm thấy dòng chọn trong bảng");
        lopHoc.xoaSinhVien(lopHoc.getDsSinhVien().get(selectedIndex));
        fireTableRowsDeleted(selectedIndex, selectedIndex);
    }
}
