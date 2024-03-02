package me.huynhducphu.quanlysach.controller;

import me.huynhducphu.quanlysach.model.Book;
import me.huynhducphu.quanlysach.model.BookCollection;

import javax.swing.table.AbstractTableModel;

public class BookTableModel extends AbstractTableModel {
    private final String[] column = {"MaSach", "TuaSach", "TacGia", "NamXuatBan", "NhaXuatBan", "SoTrang", "DonGia", "ISBN"};
    private BookCollection bookCollection;

    public BookTableModel() {
        bookCollection = new BookCollection();
    }

    @Override
    public int getRowCount() {
        return bookCollection.getBooks().size();
    }

    @Override
    public int getColumnCount() {
        return column.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.column[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Book book = bookCollection.getBooks().get(rowIndex);
        switch(columnIndex) {
            case 0: return book.getId();
            case 1: return book.getName();
            case 2: return book.getAuthor();
            case 3: return book.getYearPublished();
            case 4: return book.getPublisher();
            case 5: return book.getPagesNumber();
            case 6: return book.getPrice();
            case 7: return book.getIsbn();
            default: return null;
        }
    }

    public void insertBook(Book book) {
        bookCollection.insertBook(book);
        int lastIndex = bookCollection.getBooks().size() - 1;
        fireTableRowsInserted(lastIndex, lastIndex);
    }

    public void deleteBook(int selectedRow) {
        bookCollection.deleteBook(selectedRow);
        fireTableRowsDeleted(selectedRow, selectedRow);
    }

    public Book getBookByRow(int row) {
        return bookCollection.getBooks().get(row);
    }

    public BookCollection getBookCollection() {
        return bookCollection;
    }

    public void setBookCollection(BookCollection bookCollection) {
        this.bookCollection = bookCollection;
    }

    public void updateBook(Book newBook, int selectedRow) {
        bookCollection.updateBook(newBook, selectedRow);
        fireTableRowsUpdated(selectedRow, selectedRow);
    }

}
