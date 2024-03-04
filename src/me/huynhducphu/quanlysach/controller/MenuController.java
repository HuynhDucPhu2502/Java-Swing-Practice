package me.huynhducphu.quanlysach.controller;

import me.huynhducphu.quanlysach.model.Book;
import me.huynhducphu.quanlysach.view.Menu;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class MenuController {
    private final Menu menu;

    public MenuController(Menu menu) {
        this.menu = menu;
    }

    public void handleResetBtn() {
        menu.getIdTxtField().setText(null);
        menu.getNameTxtField().setText(null);
        menu.getAuthorTxtField().setText(null);
        menu.getYearPublishedTxtField().setText(null);
        menu.getPublisherTxtField().setText(null);
        menu.getPagesNumberTxtField().setText(null);
        menu.getPriceTxtField().setText(null);
        menu.getIsbnTxtField().setText(null);
        menu.getIdTxtField().setEditable(true);
    }

    public void updateTextFields(int selectedRow) {
        Book book = menu.getBookTableModel().getBookByRow(selectedRow);
        menu.getIdTxtField().setText(book.getId());
        menu.getNameTxtField().setText(book.getName());
        menu.getAuthorTxtField().setText(book.getAuthor());
        menu.getYearPublishedTxtField().setText(String.valueOf(book.getYearPublished()));
        menu.getPublisherTxtField().setText(book.getPublisher());
        menu.getPagesNumberTxtField().setText(String.valueOf(book.getPagesNumber()));
        menu.getPriceTxtField().setText(String.valueOf(book.getPrice()));
        menu.getIsbnTxtField().setText(book.getIsbn());
        menu.getIdTxtField().setEditable(false);
        menu.getIdTxtField().setFocusable(false);
    }

    private void handleTxtField() {
        if (!menu.getIdTxtField().getText().trim().matches("[A-Z]\\d{3}"))
            throw new IllegalArgumentException("Mã sách không hợp lệ");

        if (!menu.getNameTxtField().getText().trim().matches("[A-Za-z0-9\\-() ]+"))
            throw new IllegalArgumentException("Tựa sách không hợp lệ");

        if (!menu.getAuthorTxtField().getText().trim().matches("[A-Za-z‘' ]+"))
            throw new IllegalArgumentException("Tác giả không hợp lệ");

        if (!menu.getYearPublishedTxtField().getText().trim().matches("\\d+"))
            throw new IllegalArgumentException("Năm phát hành không hợp lệ");
        if (Integer.parseInt(menu.getYearPublishedTxtField().getText().trim()) < 1900
                || Integer.parseInt(menu.getYearPublishedTxtField().getText().trim()) > LocalDate.now().getYear())
            throw new IllegalArgumentException("Năm phát hành phải từ 1900 đến " + LocalDate.now().getYear());


        if (!menu.getPagesNumberTxtField().getText().trim().isEmpty()
                && !menu.getPagesNumberTxtField().getText().trim().matches("\\d+")
                && Integer.parseInt(menu.getPagesNumberTxtField().getText().trim()) < 0)
            throw new IllegalArgumentException("Số trang không hợp lệ");

        if (!menu.getPriceTxtField().getText().trim().isEmpty()
                && !menu.getPriceTxtField().getText().trim().matches("\\d+")
                && Double.parseDouble(menu.getPriceTxtField().getText().trim()) < 0)
            throw new IllegalArgumentException("Giá phải là một con số");

        if (!menu.getIsbnTxtField().getText().matches("\\d+-\\d+-\\d+-\\d+(-\\d+)?"))
            throw new IllegalArgumentException("mã ISBN không hợp lệ");
    }

    public void handleInsertBtn() {
        handleTxtField();

        int pagesNumber = menu.getPagesNumberTxtField().getText().trim().isEmpty() ? 0 : Integer.parseInt(menu.getPagesNumberTxtField().getText().trim());
        double price = menu.getPriceTxtField().getText().trim().isEmpty() ? 0 : Double.parseDouble(menu.getPriceTxtField().getText().trim());
        String author = menu.getAuthorTxtField().getText().trim().isEmpty() ? "UNKNOW" : menu.getAuthorTxtField().getText().trim();
        Book book = new Book(
                menu.getIdTxtField().getText().trim(),
                menu.getNameTxtField().getText().trim(),
                author,
                Integer.parseInt(menu.getYearPublishedTxtField().getText().trim()),
                menu.getPublisherTxtField().getText().trim(),
                pagesNumber,
                price,
                menu.getIsbnTxtField().getText().trim()
        );
        menu.getBookTableModel().insertBook(book);
        menu.getIdCBox().addItem(book.getId());
    }

    public void handleDeleteBtn(int selectedRow) {
        if (selectedRow == -1)
            throw new IllegalArgumentException("Hàng xóa không hợp lệ");
        int result = JOptionPane.showOptionDialog(
                null,
                "Bạn chắc chắn muốn xóa?",
                "Hệ thống",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null, null, null
        );

        if (result == JOptionPane.YES_OPTION) {
            menu.getBookTableModel().deleteBook(selectedRow);
            menu.getIdCBox().removeItemAt(selectedRow);
        }
    }

    public void handleUpdateBtn(int selectedRow) {
        if (selectedRow == -1)
            throw new IllegalArgumentException("Hàng cập nhât không hợp lệ");
        handleTxtField();
        int pagesNumber = menu.getPagesNumberTxtField().getText().trim().isEmpty() ? 0 : Integer.parseInt(menu.getPagesNumberTxtField().getText().trim());
        double price = menu.getPriceTxtField().getText().trim().isEmpty() ? 0 : Double.parseDouble(menu.getPriceTxtField().getText().trim());
        String author = menu.getAuthorTxtField().getText().trim().isEmpty() ? "UNKNOW" : menu.getAuthorTxtField().getText().trim();
        Book newBook = new Book(
                menu.getIdTxtField().getText().trim(),
                menu.getNameTxtField().getText().trim(),
                author,
                Integer.parseInt(menu.getYearPublishedTxtField().getText().trim()),
                menu.getPublisherTxtField().getText().trim(),
                pagesNumber,
                price,
                menu.getIsbnTxtField().getText().trim()
        );
        menu.getBookTableModel().updateBook(newBook, selectedRow);
    }

    public void handleSearchCBox() {
        String id = (String)menu.getIdCBox().getSelectedItem();
        ArrayList<Book> books = menu.getBookTableModel().getBookCollection().getBooks();
        IntStream.range(0, books.size()).forEach(
                x ->  {
                    if (books.get(x).getId().equalsIgnoreCase(id)) {
                        menu.getBookTable().setRowSelectionInterval(x, x);
                        menu.getBookTable().scrollRectToVisible(menu.getBookTable().getCellRect(menu.getBookTable().getSelectedRow(), 0, true));
                        updateTextFields(x);
                    }
                }
        );
    }

    public void handleSaveBtn() {
        try {
            FileController.writeToFile(menu.getBookTableModel().getBookCollection(), menu.getFilePath());
            JOptionPane.showMessageDialog(null, "Dữ liệu đã được lưu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Lưu thất bại!");
        }
    }
}
