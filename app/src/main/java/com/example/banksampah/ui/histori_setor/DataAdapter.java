package com.example.banksampah.ui.histori_setor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataAdapter {
     int id;
     String tanggal_setor;
     int id_user;
     int saldo_user;
     int id_sampah;
     int berat;
     int total;
     String keterangan;

     public DataAdapter(){

     }

     public DataAdapter(int id, String tanggal_setor, int id_user, int saldo_user, int id_sampah, int berat, int total, String keterangan) {
        this.id = id;
        this.tanggal_setor = tanggal_setor;
        this.id_user = id_user;
        this.saldo_user = saldo_user;
        this.id_sampah = id_sampah;
        this.berat = berat;
        this.total = total;
        this.keterangan = keterangan;
     }

     public int getId() {
        return id;
    }

     public void setId(int id) {
        this.id = id;
    }

     public String getTanggal_setor() {
        return tanggal_setor;
    }

     public void setTanggal_setor(String tanggal_setor) {
        this.tanggal_setor = tanggal_setor;
    }

     public int getId_user() {
        return id_user;
    }

     public void setId_user(int id_user) {
        this.id_user = id_user;
    }

     public int getSaldo_user() {
        return saldo_user;
    }

     public void setSaldo_user(int saldo_user) {
        this.saldo_user = saldo_user;
    }

     public int getId_sampah() {
        return id_sampah;
    }

     public void setId_sampah(int id_sampah) {
        this.id_sampah = id_sampah;
    }

     public int getBerat() {
        return berat;
    }

     public void setBerat(int berat) {
        this.berat = berat;
    }

     public int getTotal() {
        return total;
    }

     public void setTotal(int total) {
        this.total = total;
    }

     public String getKeterangan() {
        return keterangan;
    }

     public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
