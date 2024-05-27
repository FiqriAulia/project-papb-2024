package com.example.pamproject;

import android.graphics.Bitmap;

public class friendlist {
    private String nama;
    private String deskripsi;
    private String fotoid;
    private Bitmap fotoBitmap; // Tambahkan atribut untuk menyimpan gambar dalam format Bitmap

    public friendlist() {
        // Default constructor required for calls to DataSnapshot.getValue(FriendList.class)
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFotoid() {
        return fotoid;
    }

    public void setFoto(String foto) {
        this.fotoid = foto;
    }

    public Bitmap getFotoBitmap() {
        return fotoBitmap;
    }

    public void setFotoBitmap(Bitmap fotoBitmap) {
        this.fotoBitmap = fotoBitmap;
    }
}
