package com.example.banksampah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    Button btn_profile, btn_contact, btn_about, btn_logout;
    SessionManager manager;
    String getId, sRole;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profile);

        btn_profile = findViewById(R.id.btn_profile);
        btn_contact = findViewById(R.id.btn_contact);
        btn_about = findViewById(R.id.btn_about);
        btn_logout = findViewById(R.id.btn_logout);

        manager = new SessionManager(this);
        manager.checkloggin();
        HashMap<String, String> user = manager.getUserDetail();
        getId = user.get(manager.ID);
        sRole = user.get(manager.ROLE);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, UserProfile.class);
                startActivity(intent);
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, About.class);
                startActivity(intent);
            }
        });

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Contact.class);
                startActivity(intent);
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.logout();
                Toast.makeText(getApplicationContext(),
                        "Logout Berhasil", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (sRole.equals("Admin")) {
            startActivity(new Intent(Profile.this, MainActivity.class));
        } else {
            startActivity(new Intent(Profile.this, UserMainActivity.class));
        }
        finish();
    }
}
