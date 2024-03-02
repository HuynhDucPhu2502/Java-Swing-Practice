package me.huynhducphu.quanlynhanvien.view;

import me.huynhducphu.quanlynhanvien.controller.FileController;
import me.huynhducphu.quanlynhanvien.controller.NhanVienTableModel;
import me.huynhducphu.quanlynhanvien.controller.NhanVienTableRowFilter;
import me.huynhducphu.quanlynhanvien.model.NhanVien;
import me.huynhducphu.quanlynhanvien.model.NhanVienCollection;
import me.huynhducphu.quanlynhanvien.model.Phai;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;

public class Menu extends JFrame implements ActionListener {
    private final String filePath = "data/ThongTinNhanVien.db";
    private final NhanVienTableModel nhanVienTableModel;
    private TableRowSorter<NhanVienTableModel> nhanVienTableRowSorter;
    private JTable nhanVienTable;
    private JTextField idTxtField;
    private JTextField surnameTxtField;
    private JTextField nameTxtField;
    private JTextField ageTxtField;
    private JTextField salaryTxtField;
    private JTextField searchTxtField;
    private JButton searchBtn;
    private JButton insertBtn;
    private JButton resetBtn;
    private JButton deleteBtn;
    private JButton saveBtn;
    private JCheckBox phaiCBox;
    public Menu() throws HeadlessException {
        this("Example Menu Title");
    }

    public Menu(String title) throws HeadlessException {
        super(title);
        nhanVienTableModel = new NhanVienTableModel();
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(setupNorthPanel(), BorderLayout.NORTH);
        add(setupSouthPanel(), BorderLayout.SOUTH);
        add(setupCenterPanel(), BorderLayout.CENTER);

        setupFile();

        setVisible(true);

    }
    private void setupFile() {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                System.out.println("Tệp dữ liệu chưa được tạo. Bắt đầu quá trình tạo.");
                file.getParentFile().mkdirs();
                file.createNewFile();

            } else readData();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void readData() {
        try {
            NhanVienCollection newCollection = (NhanVienCollection)FileController.readFromFile(filePath);
            nhanVienTableModel.setNhanVienCollection(newCollection);
        } catch (EOFException eof) {
            System.out.println("Tệp dữ liệu rỗng. Không thông tin nào được đưa vào.");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private JPanel setupCenterPanel() {
        JPanel centerPanel = new JPanel();
        nhanVienTable = new JTable(nhanVienTableModel);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JComboBox<Phai> PhaiCBoxCellEditor = new JComboBox<>(Phai.values());
        nhanVienTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(PhaiCBoxCellEditor));
        nhanVienTable.getTableHeader().setResizingAllowed(false);
        nhanVienTable.getTableHeader().setReorderingAllowed(false);

        nhanVienTableRowSorter = new TableRowSorter<>(nhanVienTableModel);
        nhanVienTable.setRowSorter(nhanVienTableRowSorter);


        centerPanel.add(new JScrollPane(nhanVienTable));
        return centerPanel;
    }

    private JPanel setupNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("THÔNG TIN NHÂN VIÊN");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(setFormPanel(), BorderLayout.CENTER);

        return northPanel;

    }

    private JPanel setFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 10, 5);

        setItem(0, 0, 0, 0, gbc, new JLabel("Mã nhân viên"), formPanel);
        setItem(1, 0, 1, 0, gbc, idTxtField = new JTextField(), formPanel);
        setItem(0, 1, 0, 0, gbc, new JLabel("Họ"), formPanel);

        JPanel subPanel1 = new JPanel();
        subPanel1.setLayout(new BoxLayout(subPanel1, BoxLayout.X_AXIS));
        subPanel1.add(surnameTxtField = new JTextField());
        subPanel1.add(new JLabel("Tên nhân viên:"));
        subPanel1.add(nameTxtField = new JTextField());

        setItem(1, 1, 0, 0, gbc, subPanel1, formPanel);
        setItem(0, 2, 0, 0, gbc, new JLabel("Tuổi"), formPanel);

        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.X_AXIS));
        subPanel2.add(ageTxtField = new JTextField());
        subPanel2.add(new JLabel("Phái:"));
        subPanel2.add(Box.createHorizontalStrut(20));
        subPanel2.add(phaiCBox = new JCheckBox("Nữ"));
        setItem(1, 2, 0, 0, gbc, subPanel2, formPanel);

        setItem(0, 3, 0, 0, gbc, new JLabel("Tiền lương"), formPanel);
        setItem(1, 3, 0, 0, gbc, salaryTxtField = new JTextField(), formPanel);


        return formPanel;
    }

    private JSplitPane setupSouthPanel() {
        JSplitPane southPanel = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                setupSearchPanel(),
                setupBtnPanel()
        );
        southPanel.setResizeWeight(0.5);
        southPanel.setPreferredSize(new Dimension(0, 50));
        return southPanel;
    }

    private JPanel setupBtnPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(insertBtn = new JButton("Thêm"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(resetBtn = new JButton("Xóa trắng"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(deleteBtn = new JButton("Xóa"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(saveBtn = new JButton("Lưu"));
        btnPanel.add(Box.createHorizontalGlue());

        insertBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        saveBtn.addActionListener(this);

        return btnPanel;
    }

    private JPanel setupSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.add(Box.createHorizontalGlue());
        searchPanel.add(new JLabel("Nhập mã số cần tìm"));
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchTxtField = new JTextField(10));
        searchTxtField.setMaximumSize(searchTxtField.getPreferredSize());
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchBtn = new JButton("Tìm kiếm"));
        searchPanel.add(Box.createHorizontalGlue());

        searchBtn.addActionListener(this);

        return searchPanel;
    }

    private void setItem(int x, int y, int weightx, int weighty, GridBagConstraints gbc, Component component, JPanel
            panel) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(component, gbc);
    }

    private void resetInput() {
        idTxtField.setText(null);
        surnameTxtField.setText(null);
        nameTxtField.setText(null);
        ageTxtField.setText(null);
        salaryTxtField.setText(null);
        searchTxtField.setText(null);
        phaiCBox.setSelected(false);
    }

    private void checkInput() {
        String regex = "\\D+";
        if (idTxtField.getText().trim().isEmpty() || surnameTxtField.getText().trim().isEmpty() || nameTxtField.getText().trim().isEmpty()
            || ageTxtField.getText().trim().isEmpty() || salaryTxtField.getText().trim().isEmpty())
            throw new IllegalArgumentException("Mẫu không được bỏ trống");
        if (idTxtField.getText().trim().matches(regex) || ageTxtField.getText().trim().matches(regex)
            || salaryTxtField.getText().trim().matches(regex))
            throw new IllegalArgumentException("Sai kiểu dữ liêu");
    }

    private void handleInsertBtn() {
        checkInput();
        Phai phai = phaiCBox.isSelected() ? Phai.NU : Phai.NAM;
        NhanVien nhanVien = new NhanVien(
                Integer.parseInt(idTxtField.getText().trim()),
                surnameTxtField.getText().trim(),
                nameTxtField.getText().trim(),
                phai,
                Integer.parseInt(ageTxtField.getText().trim()),
                Double.parseDouble(salaryTxtField.getText().trim())
        );
        nhanVienTableModel.themNhanVien(nhanVien);
    }

    private void handleSearchBtn() {
        if (searchTxtField.getText().trim().isEmpty()) nhanVienTableRowSorter.setRowFilter(null);
        else nhanVienTableRowSorter.setRowFilter(new NhanVienTableRowFilter(Integer.parseInt(searchTxtField.getText().trim())));
    }

    private void handleSaveBtn() {
        try {
            NhanVienCollection nhanVienCollection = nhanVienTableModel.getNhanVienCollection();
            FileController.writeToFile(nhanVienCollection, filePath);
            JOptionPane.showMessageDialog(this, "Dữ liệu đã được lưu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            throw new IllegalArgumentException("Lưu thất bại!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        try {
            if (resetBtn.equals(src)) resetInput();
            else if (insertBtn.equals(src)) handleInsertBtn();
            else if (deleteBtn.equals(src)) nhanVienTableModel.xoaNhanVien(nhanVienTable.getSelectedRow());
            else if (searchBtn.equals(src)) handleSearchBtn();
            else if (saveBtn.equals(src)) handleSaveBtn();
        } catch (Exception exception) {
            JOptionPane.showOptionDialog(this, exception.getMessage(),
                    "Hệ thống", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, null, null);
        }
    }



    public static void main(String[] args) {
        new Menu();
    }
}
