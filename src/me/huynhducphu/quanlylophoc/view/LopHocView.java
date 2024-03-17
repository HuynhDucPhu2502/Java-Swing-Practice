package me.huynhducphu.quanlylophoc.view;

import me.huynhducphu.quanlylophoc.controller.tablemodel.LopHocTableModel;
import me.huynhducphu.quanlylophoc.controller.LopHocViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LopHocView extends JFrame implements ActionListener {
    private JTextField maLopTxtField;
    private JTextField tenLopTxtField;
    private JTextField giaoVienChuNhiemTxtField;
    private JButton huyBtn;
    private JButton luuBtn;
    private JButton suaBtn;
    private JButton xoaBtn;
    private JButton xemBtn;
    private LopHocTableModel lopHocTableModel;
    private JTable lopHocTable;
    private final LopHocViewController lopHocViewController;
    public LopHocView() throws HeadlessException {
        this("Thông tin lớp học");
    }

    public LopHocView(String title) throws HeadlessException {
        super(title);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(setupNorthPanel(), BorderLayout.NORTH);
        add(setupTablePanel(), BorderLayout.CENTER);
        add(setUpSouthPanel(), BorderLayout.SOUTH);

        lopHocViewController = new LopHocViewController(this);


        setVisible(true);
    }

    private JPanel setupNorthPanel() {
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("THÔNG TIN LỚP HỌC");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

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

        addItem(0, 0, 0, 0, gbc, formPanel, new JLabel("Mã lớp"));
        addItem(0, 1, 0, 0, gbc, formPanel, new JLabel("Tên lớp"));
        addItem(0, 2, 0, 0, gbc, formPanel, new JLabel("Giáo viên chủ nhiệm"));

        addItem(1, 0, 1, 0, gbc, formPanel, maLopTxtField = new JTextField());
        addItem(1, 1, 1, 0, gbc, formPanel, tenLopTxtField = new JTextField());
        addItem(1, 2, 1, 0, gbc, formPanel, giaoVienChuNhiemTxtField = new JTextField());

        return formPanel;
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

    private JPanel setupTablePanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());

        lopHocTableModel = new LopHocTableModel();
        lopHocTable = new JTable(lopHocTableModel);
        lopHocTable.getTableHeader().setReorderingAllowed(false);
        lopHocTable.getTableHeader().setResizingAllowed(false);
        lopHocTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                lopHocViewController.showTxtField(lopHocTable.getSelectedRow());
            }
        });
        lopHocTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);



        centerPanel.add(new JScrollPane(lopHocTable));

        return centerPanel;
    }

    private JPanel setUpSouthPanel() {
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        southPanel.add(Box.createHorizontalGlue());
        southPanel.add(xemBtn = new JButton("Xem danh sách sinh viên của lớp hiện tại"));
        xemBtn.setForeground(Color.RED);
        xemBtn.addActionListener(this);

        return southPanel;
    }

    private void addItem(int x, int y, int weightx, int weighty, GridBagConstraints gbc, JPanel panel, JComponent component) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(component, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        try {
            if (source == huyBtn) lopHocViewController.handleHuyBtn();
            else if (source == luuBtn) lopHocViewController.handleThemBtn();
            else if (source == suaBtn) lopHocViewController.handleSuaBtn(lopHocTable.getSelectedRow());
            else if (source == xemBtn) lopHocViewController.handleXemBtn(lopHocTable.getSelectedRow());
            else if (source == xoaBtn) lopHocViewController.handleXoaBtn(lopHocTable.getSelectedRow());
        } catch (Exception exception) {
            JOptionPane.showConfirmDialog(null, exception.getMessage());
        }
    }

    public JTextField getMaLopTxtField() {
        return maLopTxtField;
    }


    public JTextField getTenLopTxtField() {
        return tenLopTxtField;
    }


    public JTextField getGiaoVienChuNhiemTxtField() {
        return giaoVienChuNhiemTxtField;
    }


    public LopHocTableModel getLopHocTableModel() {
        return lopHocTableModel;
    }

}
