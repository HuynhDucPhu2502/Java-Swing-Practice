package me.huynhducphu.quanlynhanvien.controller;

import javax.swing.*;

public class NhanVienTableRowFilter extends RowFilter<NhanVienTableModel, Integer> {
    private final int id;

    public NhanVienTableRowFilter(int id) {
        this.id = id;
    }

    @Override
    public boolean include(Entry<? extends NhanVienTableModel, ? extends Integer> entry) {
        NhanVienTableModel nhanVienTableModel = entry.getModel();
        return nhanVienTableModel.getNhanVienByRow(entry.getIdentifier()).getId() == id;
    }
}
