package me.huynhducphu.quanlynhanvien.model;

import java.io.Serializable;
import java.util.Objects;

public class NhanVien implements Serializable {
    private int id;
    private String surname;
    private String name;
    private int age;
    private Phai phai;
    private double salary;

    public NhanVien() {
        this(0, "", "", Phai.NAM, 0, 0);
    }

    public NhanVien(int id, String surname, String name, Phai phai, int age, double salary) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.phai = phai;
        this.age = age;
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhanVien nhanVien = (NhanVien) o;
        return Objects.equals(id, nhanVien.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "NhanVien{" +
                "id='" + id + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phai=" + phai +
                ", salary=" + salary +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Phai getPhai() {
        return phai;
    }

    public void setPhai(Phai phai) {
        this.phai = phai;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
