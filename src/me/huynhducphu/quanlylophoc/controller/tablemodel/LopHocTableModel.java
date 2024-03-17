package me.huynhducphu.quanlylophoc.controller.tablemodel;

import me.huynhducphu.quanlylophoc.model.LopHoc;
import me.huynhducphu.quanlylophoc.model.LopHocCollection;

import javax.swing.table.AbstractTableModel;

public class LopHocTableModel extends AbstractTableModel {
    private final String column[] = {"Mã số lớp", "Tên lớp", "Giáo viên chủ nhiệm"};
    private final LopHocCollection lopHocCollection;

    public LopHocTableModel() {
        lopHocCollection = new LopHocCollection();
    }

    @Override
    public String getColumnName(int column) {
        return this.column[column];
    }

    @Override
    public int getRowCount() {
        return lopHocCollection.getDsLopHoc().size();
    }

    @Override
    public int getColumnCount() {
        return column.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LopHoc lopHoc = lopHocCollection.getDsLopHoc().get(rowIndex);
        switch (columnIndex) {
            case 0: return lopHoc.getMaSoLop();
            case 1: return lopHoc.getTenLop();
            case 2: return lopHoc.getGiaoVienChuNhiem();
            default: return null;
        }
    }

    public LopHocCollection getLopHocCollection() {
        return lopHocCollection;
    }

    public void themLopHoc(LopHoc lopHoc) {
        if (lopHoc == null) throw new IllegalArgumentException("Lớp học không hợp lệ");
        if (lopHocCollection.themLopHoc(lopHoc)) {
            int lastIndex = lopHocCollection.getDsLopHoc().size() - 1;
            fireTableRowsInserted(lastIndex, lastIndex);
        }
    }

    public void suaLopHoc(LopHoc newLopHoc, int selectedIndex) {
        if (newLopHoc == null)
            throw new IllegalArgumentException("Lớp học không hợp lệ");
        if (selectedIndex == -1)
            throw new IllegalArgumentException("Không tìm thấy dòng chọn trong bảng");
        lopHocCollection.suaLopHoc(newLopHoc);
        fireTableRowsUpdated(selectedIndex, selectedIndex);
    }

    public void xoaLopHoc(int selectedIndex) {
        lopHocCollection.xoaLopHoc(lopHocCollection.getDsLopHoc().get(selectedIndex));
        fireTableRowsDeleted(selectedIndex, selectedIndex);
    }
}
