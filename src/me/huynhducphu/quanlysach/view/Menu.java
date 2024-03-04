package me.huynhducphu.quanlysach.view;

import me.huynhducphu.quanlysach.controller.BookTableModel;
import me.huynhducphu.quanlysach.controller.FileController;
import me.huynhducphu.quanlysach.controller.MenuController;
import me.huynhducphu.quanlysach.model.BookCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;

public class Menu extends JFrame implements ActionListener {
    private final MenuController menuController;
    private final String filePath = "data/ThongTinSach.db";
    private BookTableModel bookTableModel;
    private JTable bookTable;
    private JTextField idTxtField;
    private JTextField nameTxtField;
    private JTextField authorTxtField;
    private JTextField yearPublishedTxtField;
    private JTextField publisherTxtField;
    private JTextField pagesNumberTxtField;
    private JTextField priceTxtField;
    private JTextField isbnTxtField;
    private JButton insertBtn;
    private JButton resetBtn;
    private JButton deleteBtn;
    private JButton updateBtn;
    private JButton saveBtn;
    private JComboBox<String> idCBox;
    public Menu() throws HeadlessException {
        this("Example Menu Title");
    }

    public Menu(String title) throws HeadlessException {
        super(title);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuController = new MenuController(this);
        add(setupNorthPanel(), BorderLayout.NORTH);
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
            } else readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readFile() {
        try {
            bookTableModel.setBookCollection((BookCollection)FileController.readFromFile(filePath));
            bookTableModel.getBookCollection().getBooks()
                    .forEach(x -> idCBox.addItem(x.getId()));
        } catch (EOFException eof) {
            System.out.println("Tệp dữ liệu rỗng. Không thông tin nào được đưa vào.");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private JPanel setupCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Danh sách các cuốn sách"));

        bookTableModel = new BookTableModel();
        bookTable = new JTable(bookTableModel);
        setupTableEvent();

        centerPanel.add(new JScrollPane(bookTable));
        return centerPanel;
    }

    private JPanel setupNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        northPanel.add(setupFormPanel(), BorderLayout.CENTER);
        northPanel.add(setupBtnPanel(), BorderLayout.SOUTH);

        return northPanel;
    }

    private JPanel setupFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Records Editor"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 5, 15);

        setItem(0, 0, 0, 0, new JLabel("Mã sách:"), formPanel, gbc);

        JPanel subPanel1 = new JPanel();
        subPanel1.setLayout(new BoxLayout(subPanel1, BoxLayout.X_AXIS));
        subPanel1.add(idTxtField = new JTextField(20));
        idTxtField.setMaximumSize(idTxtField.getPreferredSize());

        setItem(1, 0, 1, 0, subPanel1, formPanel, gbc);

        setItem(0, 1, 0, 0, new JLabel("Tựa sách:"), formPanel, gbc);
        setItem(1, 1, 1, 0, nameTxtField = new JTextField(10), formPanel, gbc);
        setItem(2, 1, 0, 0, new JLabel("Tác giả:"), formPanel, gbc);
        setItem(3, 1, 1, 0, authorTxtField = new JTextField(10), formPanel, gbc);

        setItem(0, 2, 0, 0, new JLabel("Năm xuất bản:"), formPanel, gbc);
        setItem(1, 2, 1, 0, yearPublishedTxtField = new JTextField(10), formPanel, gbc);
        setItem(2, 2, 0, 0, new JLabel("Nhà xuất bản:"), formPanel, gbc);
        setItem(3, 2, 1, 0, publisherTxtField = new JTextField(10), formPanel, gbc);

        setItem(0, 3, 0, 0, new JLabel("Số trang:"), formPanel, gbc);
        setItem(1, 3, 1, 0, pagesNumberTxtField = new JTextField(10), formPanel, gbc);
        setItem(2, 3, 0, 0, new JLabel("Đơn giá:"), formPanel, gbc);
        setItem(3, 3, 1, 0, priceTxtField = new JTextField(10), formPanel, gbc);

        JPanel subPanel2 = new JPanel();
        subPanel2.setLayout(new BoxLayout(subPanel2, BoxLayout.X_AXIS));
        subPanel2.add(new JLabel("International Standard Book Number:"));
        subPanel2.add(isbnTxtField = new JTextField(10));
        gbc.gridwidth = 2;
        setItem(0, 4, 0, 0, subPanel2, formPanel, gbc);

        return formPanel;
    }

    private JPanel setupBtnPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.X_AXIS));
        btnPanel.setPreferredSize(new Dimension(0, 50));

        btnPanel.add(Box.createHorizontalGlue());
        btnPanel.add(insertBtn = new JButton("Thêm"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(resetBtn = new JButton("Làm mới"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(deleteBtn = new JButton("Xóa"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(updateBtn = new JButton("Sửa"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(saveBtn = new JButton("Lưu"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(new JLabel("Tìm theo mã sách:"));
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(idCBox = new JComboBox<>());
        idCBox.setMaximumSize(new Dimension(Short.MAX_VALUE, idCBox.getPreferredSize().height));
        btnPanel.add(Box.createHorizontalGlue());

        insertBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        idCBox.addActionListener(this);



        return  btnPanel;
    }

    private void setupTableEvent() {
        bookTable.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow != -1) {

                    menuController.updateTextFields(selectedRow);
                    idCBox.setSelectedIndex(selectedRow);
                }
            }
        });
    }

    private void setItem(int x, int y, int weightx, int weighty, Component component, JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        panel.add(component, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        try {
            if (src.equals(resetBtn)) menuController.handleResetBtn();
            else if (src.equals(insertBtn)) menuController.handleInsertBtn();
            else if (src.equals(deleteBtn)) menuController.handleDeleteBtn(bookTable.getSelectedRow());
            else if (src.equals(updateBtn)) menuController.handleUpdateBtn(bookTable.getSelectedRow());
            else if (src.equals(idCBox)) menuController.handleSearchCBox();
            else if (src.equals(saveBtn)) menuController.handleSaveBtn();
        } catch (Exception exception) {
            JOptionPane.showOptionDialog(null, exception.getMessage(), "System",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
        }

    }

    public static void main(String[] args) {
        new Menu();
    }

    public BookTableModel getBookTableModel() {
        return bookTableModel;
    }

    public JTable getBookTable() {
        return bookTable;
    }

    public JTextField getIdTxtField() {
        return idTxtField;
    }

    public JTextField getNameTxtField() {
        return nameTxtField;
    }

    public JTextField getAuthorTxtField() {
        return authorTxtField;
    }

    public JTextField getYearPublishedTxtField() {
        return yearPublishedTxtField;
    }

    public JTextField getPublisherTxtField() {
        return publisherTxtField;
    }

    public JTextField getPagesNumberTxtField() {
        return pagesNumberTxtField;
    }

    public JTextField getPriceTxtField() {
        return priceTxtField;
    }

    public JTextField getIsbnTxtField() {
        return isbnTxtField;
    }


    public JComboBox<String> getIdCBox() {
        return idCBox;
    }


    public String getFilePath() {
        return filePath;
    }
}
