package me.huynhducphu.quanlynhanvien.model;

public enum Phai {
    NAM("Nam"), NU("Nữ");
    private final String displayname;

    Phai(String displayname) {
        this.displayname = displayname;
    }

    @Override
    public String toString() {
        return displayname;
    }
}
