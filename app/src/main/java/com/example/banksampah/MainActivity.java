package com.example.banksampah;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banksampah.ui.data_user.DataUserMain;
import com.example.banksampah.ui.sampah.MainSampah;
import com.example.banksampah.ui.transaksi_setor.TransaksiSetorMain;
import com.example.banksampah.ui.transaksi_tarik.TransaksiTarik;
import com.example.banksampah.ui.transaksi_tarik.TransaksiTarikMain;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String getId;
    SessionManager manager;
    TextView txt_role, txt_name;
    Button btn_sampah, btn_user, btn_transaksisetor, btn_transaksitarik, btn_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new SessionManager(this);
        manager.checkloggin();

        btn_sampah = findViewById(R.id.btnsampah);
        btn_user = findViewById(R.id.btnuser);
        btn_transaksisetor = findViewById(R.id.btntransaksisetor);
        btn_transaksitarik = findViewById(R.id.btntransaksitarik);
        btn_profile = findViewById(R.id.btnprofile);

        HashMap<String, String> user = manager.getUserDetail();
        getId = user.get(manager.ID);

        btn_sampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
            }
        });

        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DataUserMain.class);
                startActivity(intent);
            }
        });

        btn_transaksisetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransaksiSetorMain.class);
                startActivity(intent);
            }
        });

        btn_transaksitarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransaksiTarikMain.class);
                startActivity(intent);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
    }

    public void onBackPressed() {
        AlertDialog.Builder tombolout = new AlertDialog.Builder(MainActivity.this);
        tombolout.setMessage("Apakan anda yakin ingin keluar dari aplikasi ini? ");
        tombolout.setTitle("Keluar Aplikasi");
        tombolout.setIcon(R.mipmap.appicon);
        tombolout.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                MainActivity.this.finish();
            }
        });
        tombolout.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        tombolout.setNeutralButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        tombolout.show();
    }
}
