package com.example.pam_projek;

public class Kontak {

    public String nama;
    public int avatarId;

    public Kontak(String nama, int avatarId) {
        this.nama = nama;
        this.avatarId = avatarId;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
