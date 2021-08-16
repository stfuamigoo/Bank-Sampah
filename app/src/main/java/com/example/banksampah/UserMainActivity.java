package com.example.banksampah;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banksampah.ui.histori_setor.HistoriSetor;
import com.example.banksampah.ui.histori_tarik.HistoriTarikMain;
import com.example.banksampah.ui.sampah.user.UserSampahMain;

public class UserMainActivity extends AppCompatActivity {
    SessionManager manager;
    Button btn_sampah, btn_historisetor, btn_historitarik, btn_profile;
    String getId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        btn_sampah = findViewById(R.id.btnsampahuser);
        btn_historisetor = findViewById(R.id.btnhistorisetoruser);
        btn_historitarik = findViewById(R.id.btnhistoritarikuser);
        btn_profile = findViewById(R.id.btnprofileuser);

        btn_sampah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, UserSampahMain.class);
                startActivity(intent);
            }
        });

        btn_historisetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, HistoriSetor.class);
                startActivity(intent);
            }
        });

        btn_historitarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, HistoriTarikMain.class);
                startActivity(intent);
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, Profile.class);
                startActivity(intent);
            }
        });
    }

    public void onBackPressed() {
        AlertDialog.Builder tombolout = new AlertDialog.Builder(UserMainActivity.this);
        tombolout.setMessage("Apakan anda yakin ingin keluar dari aplikasi ini? ");
        tombolout.setTitle("Keluar Aplikasi");
        tombolout.setIcon(R.mipmap.appicon);
        tombolout.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                UserMainActivity.this.finish();
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
