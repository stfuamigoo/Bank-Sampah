package com.example.banksampah.ui.histori_tarik;

import java.io.Serializable;

public class DataAdapter implements Serializable {
    int id ;
    String tanggal_tarik;
    String nik_user;
    int saldo;
    int jumlah_tarik;
    String keterangan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal_tarik() {
        return tanggal_tarik;
    }

    public void setTanggal_tarik(String tanggal_tarik) {
        this.tanggal_tarik = tanggal_tarik;
    }

    public String getNik_user() {
        return nik_user;
    }

    public void setNik_user(String nik_user) {
        this.nik_user = nik_user;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getJumlah_tarik() {
        return jumlah_tarik;
    }

    public void setJumlah_tarik(int jumlah_tarik) {
        this.jumlah_tarik = jumlah_tarik;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
