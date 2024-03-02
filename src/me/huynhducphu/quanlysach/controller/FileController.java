package me.huynhducphu.quanlysach.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileController {
    public static void writeToFile(Object books, String filePath) throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
        out.writeObject(books);
        out.close();
    }
    public static Object readFromFile(String filePath) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
        Object data = in.readObject();
        in.close();
        return data;
    }
}
