package com.example.banksampah.ui.transaksi_tarik;

import com.google.gson.annotations.SerializedName;

public class TransaksiTarik {
    @SerializedName("id")
    private int id;
    @SerializedName("tanggal_tarik")
    private String tanggal_tarik;
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("nama_user")
    private String nama_user;
    @SerializedName("saldo_user")
    private String saldo_user;
    @SerializedName("jumlah_tarik")
    private String jumlah_tarik;
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

    public String getTanggal_tarik() {
        return tanggal_tarik;
    }

    public void setTanggal_tarik(String tanggal_tarik) {
        this.tanggal_tarik = tanggal_tarik;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
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

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getSaldo_user() {
        return saldo_user;
    }

    public void setSaldo_user(String saldo_user) {
        this.saldo_user = saldo_user;
    }

    public String getJumlah_tarik() {
        return jumlah_tarik;
    }

    public void setJumlah_tarik(String jumlah_tarik) {
        this.jumlah_tarik = jumlah_tarik;
    }
}
