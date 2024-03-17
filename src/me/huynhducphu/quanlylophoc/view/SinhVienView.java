package me.huynhducphu.quanlylophoc.view;

import me.huynhducphu.quanlylophoc.controller.LopHocViewController;
import me.huynhducphu.quanlylophoc.controller.SinhVienViewController;
import me.huynhducphu.quanlylophoc.controller.tablemodel.LopHocTableModel;
import me.huynhducphu.quanlylophoc.controller.tablemodel.SinhVienTableModel;
import me.huynhducphu.quanlylophoc.model.LopHoc;
import me.huynhducphu.quanlylophoc.model.SinhVien;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinhVienView  extends JFrame implements ActionListener {
    private JTextField maSoSinhVienTxtField;
    private JTextField hoTenTxtField;
    private JTextField emailTxtField;
    private JTextField diaChiTxtField;
    private JButton huyBtn;
    private JButton luuBtn;
    private JButton suaBtn;
    private JButton xoaBtn;
    private SinhVienViewController sinhVienViewController;
    private LopHoc lopHoc;
    private SinhVienTableModel sinhVienTableModel;
    private JTable sinhVienTable;

    public SinhVienView(String title, LopHoc lopHoc) throws HeadlessException {
        super(title);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.lopHoc = lopHoc;
        add(setupNorthPanel(), BorderLayout.NORTH);
        add(setupTablePanel(), BorderLayout.CENTER);

        sinhVienViewController = new SinhVienViewController(this);

        setVisible(true);
    }
    private JPanel setupTablePanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());

        sinhVienTableModel = new SinhVienTableModel(lopHoc);
        sinhVienTable = new JTable(sinhVienTableModel);
        sinhVienTable.getTableHeader().setReorderingAllowed(false);
        sinhVienTable.getTableHeader().setResizingAllowed(false);
        sinhVienTable.getSelectionModel().addListSelectionListener(x -> {
            if (!x.getValueIsAdjusting()) {
                sinhVienViewController.showTxtField(sinhVienTable.getSelectedRow());
            }
        });
        sinhVienTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        centerPanel.add(new JScrollPane(sinhVienTable));

        return centerPanel;
    }
    private JPanel setupNorthPanel() {
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("THÔNG TIN SINH VIÊN");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        title.setForeground(Color.BLUE);

        northPanel.add(title, BorderLayout.NORTH);
        northPanel.add(setupFormPanel(), BorderLayout.CENTER);
        northPanel.add(setupBtnPanel(), BorderLayout.SOUTH);

        return northPanel;
    }
    private JPanel setupFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addItem(0, 0, 0, 0, gbc, formPanel, new JLabel("Mã số sinh viên"));
        addItem(0, 1, 0, 0, gbc, formPanel, new JLabel("Họ tên"));
        addItem(0, 2, 0, 0, gbc, formPanel, new JLabel("Email"));
        addItem(0, 3, 0, 0, gbc, formPanel, new JLabel("Địa chỉ"));

        addItem(1, 0, 1, 0, gbc, formPanel, maSoSinhVienTxtField = new JTextField());
        addItem(1, 1, 1, 0, gbc, formPanel, hoTenTxtField = new JTextField());
        addItem(1, 2, 1, 0, gbc, formPanel, emailTxtField = new JTextField());
        addItem(1, 3, 1, 0, gbc, formPanel, diaChiTxtField = new JTextField());

        return formPanel;
    }

    private void addItem(int x, int y, int weightx, int weighty, GridBagConstraints gbc, JPanel panel, JComponent component) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(component, gbc);
    }

    private JPanel setupBtnPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(huyBtn = new JButton("Hủy"));
        btnPanel.add(luuBtn = new JButton("Lưu"));
        btnPanel.add(suaBtn = new JButton("Sửa"));
        btnPanel.add(xoaBtn = new JButton("Xóa"));
        btnPanel.add(Box.createHorizontalGlue());

        huyBtn.addActionListener(this);
        luuBtn.addActionListener(this);
        suaBtn.addActionListener(this);
        xoaBtn.addActionListener(this);

        return btnPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        try {
            if (source == luuBtn) sinhVienViewController.handleThemBtn();
            else if (source == huyBtn) sinhVienViewController.handleHuyBtn();
            else if (source == suaBtn) sinhVienViewController.handleSuaBtn(sinhVienTable.getSelectedRow());
            else if (source == xoaBtn) sinhVienViewController.handleXoaBtn(sinhVienTable.getSelectedRow());
        } catch (Exception exception) {
            JOptionPane.showConfirmDialog(null, exception.getMessage());
            exception.printStackTrace();
        }
    }

    public JTextField getMaSoSinhVienTxtField() {
        return maSoSinhVienTxtField;
    }

    public JTextField getHoTenTxtField() {
        return hoTenTxtField;
    }

    public JTextField getEmailTxtField() {
        return emailTxtField;
    }

    public JTextField getDiaChiTxtField() {
        return diaChiTxtField;
    }

    public SinhVienTableModel getSinhVienTableModel() {
        return sinhVienTableModel;
    }

    public LopHoc getLopHoc() {
        return lopHoc;
    }
}
