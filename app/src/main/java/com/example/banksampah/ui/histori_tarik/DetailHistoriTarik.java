package com.example.banksampah.ui.histori_tarik;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banksampah.R;

public class DetailHistoriTarik extends AppCompatActivity {
    EditText et_jumlah_tarik, et_keterangan, et_tanggal_tarik, et_saldo, et_id_user, et_nama_user;
    private int id;
    String tanggal_tarik, id_user, jumlah_tarik, keterangan, saldo_user, nama_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histori_tarik_detail);

        et_tanggal_tarik = findViewById(R.id.ett_tanggal_tarik);
        et_nama_user = findViewById(R.id.ett_nama_user);
        et_id_user = findViewById(R.id.ett_id_user);
        et_jumlah_tarik = findViewById(R.id.ett_jumlah_tarik);
        et_saldo = findViewById(R.id.ett_saldo);
        et_keterangan = findViewById(R.id.ett_keterangan);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        tanggal_tarik = intent.getStringExtra("tanggal_tarik");
        id_user = intent.getStringExtra("id_user");
        nama_user = intent.getStringExtra("nama_user");
        saldo_user = intent.getStringExtra("saldo_user");
        jumlah_tarik = intent.getStringExtra("jumlah_tarik");
        keterangan = intent.getStringExtra("keterangan");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Transaksi " + tanggal_tarik.toString());

            et_tanggal_tarik.setText(tanggal_tarik);
            et_id_user.setText(id_user);
            et_nama_user.setText(nama_user);
            et_saldo.setText(saldo_user);
            et_jumlah_tarik.setText(jumlah_tarik);
            et_keterangan.setText(keterangan);
        } else {
            getSupportActionBar().setTitle("Add Transaksi Tarik");
        }
    }

    void readMode(){
        et_tanggal_tarik.setFocusableInTouchMode(false);
        et_id_user.setFocusableInTouchMode(false);
        et_nama_user.setFocusableInTouchMode(false);
        et_saldo.setFocusableInTouchMode(false);
        et_jumlah_tarik.setFocusableInTouchMode(false);
        et_keterangan.setFocusableInTouchMode(false);

        et_tanggal_tarik.setFocusable(false);
        et_id_user.setFocusable(false);
        et_nama_user.setFocusable(false);
        et_saldo.setFocusable(false);
        et_jumlah_tarik.setFocusable(false);
        et_keterangan.setFocusable(false);

        et_tanggal_tarik.setClickable(false);
        et_id_user.setClickable(false);
        et_nama_user.setClickable(false);
        et_saldo.setClickable(false);
        et_jumlah_tarik.setClickable(false);
        et_keterangan.setClickable(false);
    }
}
