    package me.huynhducphu.quanlynhanvien.controller;

    import me.huynhducphu.quanlynhanvien.model.NhanVien;
    import me.huynhducphu.quanlynhanvien.model.NhanVienCollection;
    import me.huynhducphu.quanlynhanvien.model.Phai;

    import javax.swing.table.AbstractTableModel;
    import java.text.DecimalFormat;

    public class NhanVienTableModel  extends AbstractTableModel {
        private NhanVienCollection nhanVienCollection;
        private final String[] column = {"Mã NV", "Họ", "Tên", "Phái", "Tuổi", "Tiền lương"};

        public NhanVienTableModel() {
            this.nhanVienCollection = new NhanVienCollection();
        }

        @Override
        public int getRowCount() {
            return nhanVienCollection.getNhanVienList().size();
        }

        @Override
        public int getColumnCount() {
            return column.length;
        }

        @Override
        public String getColumnName(int columnIndex) {
            return column[columnIndex];
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex != 0;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            NhanVien nhanVien = nhanVienCollection.getNhanVienList().get(rowIndex);
            switch (columnIndex) {
                case 0: return nhanVien.getId();
                case 1: return nhanVien.getSurname();
                case 2: return nhanVien.getName();
                case 3: return nhanVien.getPhai();
                case 4: return nhanVien.getAge();
                case 5: {
                    DecimalFormat currencryFormat = new DecimalFormat("#,### $");
                    return currencryFormat.format(nhanVien.getSalary());
                }
                default: return  null;
            }
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            NhanVien nhanVien = nhanVienCollection.getNhanVienList().get(rowIndex);
            switch (columnIndex) {
                case 1: nhanVien.setSurname((String)aValue); break;
                case 2: nhanVien.setName((String)aValue); break;
                case 3: nhanVien.setPhai((Phai) aValue); break;
                case 4: nhanVien.setAge((int)aValue); break;
                case 6: nhanVien.setSalary((double)aValue); break;
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        public void themNhanVien(NhanVien nhanVien) {
            nhanVienCollection.addNhanVien(nhanVien);
            int lastIndex = nhanVienCollection.getNhanVienList().size() - 1;
            fireTableRowsInserted(lastIndex, lastIndex);
        }

        public void xoaNhanVien(int selectedRow) {
            if (selectedRow == -1)
                throw new IllegalArgumentException("Hàng xoá nằm ngoài bảng");
            nhanVienCollection.xoaNhanVien(selectedRow);
            fireTableRowsDeleted(selectedRow, selectedRow);
        }

        public NhanVien getNhanVienByRow(int rowIndex) {
            return nhanVienCollection.getNhanVienList().get(rowIndex);
        }

        public NhanVienCollection getNhanVienCollection() {
            return nhanVienCollection;
        }
        public void setNhanVienCollection(NhanVienCollection newCollection) {
            this.nhanVienCollection = newCollection;
            fireTableDataChanged();
        }
    }
