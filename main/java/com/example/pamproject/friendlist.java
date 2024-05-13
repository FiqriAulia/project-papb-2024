package com.example.pamproject;

public class friendlist {

    private String fotoid;
    private String nama, deskripsi;


    public friendlist(String nama, String deskripsi, String fotoid  ) {
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.fotoid = fotoid;

    }


    public String getFoto() {
        return fotoid;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }


}



