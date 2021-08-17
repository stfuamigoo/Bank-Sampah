package com.example.banksampah.ui.transaksi_setor;

import com.google.gson.annotations.SerializedName;

public class TransaksiSetor {
    @SerializedName("id")
    private int id;
    @SerializedName("tanggalsetor")
    private String tanggalsetor;
    @SerializedName("id_user")
    private String id_user;
    @SerializedName("id_sampah")
    private String id_sampah;
    @SerializedName("nama")
    private String nama;
    @SerializedName("jenissampah")
    private String jenissampah;
    @SerializedName("harga")
    private String harga;
    @SerializedName("berat")
    private String berat;
    @SerializedName("total")
    private String total;
    @SerializedName("keterangan")
    private String keterangan;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggalsetor() {
        return tanggalsetor;
    }

    public void setTanggalsetor(String tanggalsetor) {
        this.tanggalsetor = tanggalsetor;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_sampah() {
        return id_sampah;
    }

    public void setId_sampah(String id_sampah) {
        this.id_sampah = id_sampah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenissampah() {
        return jenissampah;
    }

    public void setJenissampah(String jenissampah) {
        this.jenissampah = jenissampah;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }



    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
