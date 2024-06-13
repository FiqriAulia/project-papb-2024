package com.example.pamproject;

public class friendlist {

    private int foto;
    private String nama,deskripsi;


    public friendlist(int foto,String nama, String deskripsi){
        this.nama=nama;
        this.deskripsi=deskripsi;
        this.foto = foto;

    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getFoto() {
        return foto;
    }
    public String getNama(){return nama;}
    public String getDeskripsi(){return deskripsi;}






}
