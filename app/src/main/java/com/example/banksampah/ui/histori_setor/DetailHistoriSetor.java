package com.example.banksampah.ui.histori_setor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banksampah.R;

public class DetailHistoriSetor extends AppCompatActivity {
    EditText et_tanggalsetor, et_id_user, et_id_sampah, et_nama, et_jenissampah, et_harga, et_satuan, et_jumlah, et_total, et_keterangan, et_saldo;
    private String tanggalsetor, id_user, id_sampah, nama, jenissampah, harga, satuan, jumlah, total, keterangan, saldo;
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_setor_detail);

        et_tanggalsetor = findViewById(R.id.ets_tanggal_setor);
        et_id_user = findViewById(R.id.ets_id_user);
        et_nama = findViewById(R.id.ets_spinnernama);
        et_saldo = findViewById(R.id.ets_saldo);
        et_id_sampah = findViewById(R.id.ets_id_sampah);
        et_jenissampah = findViewById(R.id.ets_spinnersampah);
        et_satuan = findViewById(R.id.ets_satuan);
        et_harga = findViewById(R.id.ets_hargasampah);
        et_jumlah = findViewById(R.id.ets_berat);
        et_total = findViewById(R.id.ets_total);
        et_keterangan = findViewById(R.id.ets_keterangan);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        tanggalsetor = intent.getStringExtra("tanggalsetor");
        id_user = intent.getStringExtra("id_user");
        id_sampah = intent.getStringExtra("id_sampah");
        saldo = intent.getStringExtra("saldo_user");
        nama = intent.getStringExtra("nama");
        jenissampah = intent.getStringExtra("jenissampah");
        satuan = intent.getStringExtra("satuan");
        harga = intent.getStringExtra("harga");
        jumlah = intent.getStringExtra("jumlah");
        total = intent.getStringExtra("total");
        keterangan = intent.getStringExtra("keterangan");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {

        if (id != 0) {
            readMode();
            getSupportActionBar().setTitle("Detail Transaksi " + tanggalsetor.toString());

            et_tanggalsetor.setText(tanggalsetor);
            et_id_user.setText(id_user);
            et_nama.setText(nama);
            et_saldo.setText(saldo);
            et_id_sampah.setText(id_sampah);
            et_jenissampah.setText(jenissampah);
            et_satuan.setText(satuan);
            et_harga.setText(harga);
            et_jumlah.setText(jumlah);
            et_total.setText(total);
            et_keterangan.setText(keterangan);

        } else {
            getSupportActionBar().setTitle("Add Transaksi Setor");
        }
    }

    void readMode(){

        et_tanggalsetor.setFocusableInTouchMode(false);
        et_id_user.setFocusableInTouchMode(false);
        et_nama.setFocusableInTouchMode(false);
        et_saldo.setFocusableInTouchMode(false);
        et_id_sampah.setFocusableInTouchMode(false);
        et_jenissampah.setFocusableInTouchMode(false);
        et_satuan.setFocusableInTouchMode(false);
        et_harga.setFocusableInTouchMode(false);
        et_jumlah.setFocusableInTouchMode(false);
        et_total.setFocusableInTouchMode(false);
        et_keterangan.setFocusableInTouchMode(false);

        et_tanggalsetor.setFocusable(false);
        et_id_user.setFocusable(false);
        et_nama.setFocusable(false);
        et_saldo.setFocusable(false);
        et_id_sampah.setFocusable(false);
        et_jenissampah.setFocusable(false);
        et_satuan.setFocusable(false);
        et_harga.setFocusable(false);
        et_jumlah.setFocusable(false);
        et_total.setFocusable(false);
        et_keterangan.setFocusable(false);

        et_tanggalsetor.setClickable(false);
        et_id_user.setClickable(false);
        et_nama.setClickable(false);
        et_saldo.setClickable(false);
        et_id_sampah.setClickable(false);
        et_jenissampah.setClickable(false);
        et_satuan.setClickable(false);
        et_harga.setClickable(false);
        et_jumlah.setClickable(false);
        et_total.setClickable(false);
        et_keterangan.setClickable(false);
    }
}
