package com.example.banksampah.ui.sampah;

import com.google.gson.annotations.SerializedName;

public class Sampah {

    @SerializedName("id")
    private int id;
    @SerializedName("jenissampah")
    private String jenissampah;
    @SerializedName("satuan")
    private String satuan;
    @SerializedName("harga")
    private String harga;
    @SerializedName("keterangan")
    private String keterangan;
//    @SerializedName("birth")
//    private String birth;
    @SerializedName("picture")
    private String picture;
//    @SerializedName("love")
//    private Boolean love;
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

    public String getJenissampah() {
        return jenissampah;
    }

    public void setJenissampah(String jenissampah) {
        this.jenissampah = jenissampah;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

//    public String getBirth() {
//        return birth;
//    }
//
//    public void setBirth(String birth) {
//        this.birth = birth;
//    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

//    public Boolean getLove() {
//        return love;
//    }
//
//    public void setLove(Boolean love) {
//        this.love = love;
//    }

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
