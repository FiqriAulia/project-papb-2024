package otr.mobile.activitypam;

public class input {
    int id;
    String nama;
    String status;
    int Image;

    public input(int id, String nama, String status, int Image) {
        this.nama = nama;
        this.status = status;
        this.Image = Image;
        this.id=id;
    }

    public input(int id, String nama, String status) {
        this.nama = nama;
        this.status = status;
        this.id=id;
    }


    public int getImage() {
        return Image;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getStatus() {
        return status;
    }
}