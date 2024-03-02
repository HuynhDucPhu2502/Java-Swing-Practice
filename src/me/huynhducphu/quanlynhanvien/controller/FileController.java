package me.huynhducphu.quanlynhanvien.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileController {
    public static void writeToFile(Object nhanVienList, String filePath) throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
        out.writeObject(nhanVienList);
        out.close();
    }

    public static Object readFromFile(String filePath) throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
        Object data = in.readObject();
        in.close();
        return data;

    }


}
