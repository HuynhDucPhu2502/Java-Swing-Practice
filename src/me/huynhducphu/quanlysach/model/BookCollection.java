package me.huynhducphu.quanlysach.model;

import java.io.Serializable;
import java.util.ArrayList;

public class BookCollection implements Serializable {
    private ArrayList<Book> books;

    public BookCollection() {
        this.books = new ArrayList<>(10);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public void insertBook(Book book) {
        if (books.contains(book))
            throw new IllegalArgumentException("Sách đã tồn tại");
        books.add(book);
    }

    public void deleteBook(int selectedRow) {
        books.remove(selectedRow);
    }

    public void updateBook(Book newBook, int selectedRow) {
        Book book = books.get(selectedRow);

        book.setName(newBook.getName());
        book.setAuthor(newBook.getAuthor());
        book.setYearPublished(newBook.getYearPublished());
        book.setPublisher(newBook.getPublisher());
        book.setPagesNumber(newBook.getPagesNumber());
        book.setPrice(newBook.getPrice());
        book.setIsbn(newBook.getIsbn());
    }
}
