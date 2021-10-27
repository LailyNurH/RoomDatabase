package com.example.databasempii.Data.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mahasiswa {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    @ColumnInfo(name = "nama")
    private String nama = "";

    @ColumnInfo(name = "nim")
    private String nim = "";

    @ColumnInfo(name = "kejuruan")
    String kejuruan;

    @ColumnInfo(name = "alamat")
    private String alamat = "";

    @ColumnInfo(name = "gambar")
    private String gambar = "";

    public Mahasiswa() {
    }

    public Mahasiswa(int id, String nama, String nim, String kejuruan, String alamat, String gambar) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.kejuruan = kejuruan;
        this.alamat = alamat;
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() { return nama; }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() { return nim; }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getKejuruan() {
        return kejuruan;
    }
    public void setKejuruan(String kejuruan) {
        this.kejuruan = kejuruan;
    }

    public String getAlamat() { return alamat; }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

}