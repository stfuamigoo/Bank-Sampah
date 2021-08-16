package com.example.banksampah.ui.sampah.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.banksampah.R;

public class DetailSampah extends AppCompatActivity {
    private EditText mJenissampah, mSatuan, mHarga, mKeterangan;
    private ImageView mPicture;

    private String jenissampah, satuan, harga, picture, keterangan;
    private int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sampah_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mJenissampah = findViewById(R.id.jenissampah);
        mSatuan = findViewById(R.id.satuan);
        mHarga = findViewById(R.id.harga);
        mKeterangan = findViewById(R.id.keterangan);
        mPicture = findViewById(R.id.picture);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        jenissampah = intent.getStringExtra("jenissampah");
        satuan = intent.getStringExtra("satuan");
        harga = intent.getStringExtra("harga");
        keterangan = intent.getStringExtra("keterangan");
        picture = intent.getStringExtra("picture");

        setDataFromIntentExtra();
    }

    private void setDataFromIntentExtra() {

        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + jenissampah.toString());

            mJenissampah.setText(jenissampah);
            mSatuan.setText(satuan);
            mHarga.setText(harga);
            mKeterangan.setText(keterangan);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.bg);
            requestOptions.error(R.drawable.bg);

            Glide.with(DetailSampah.this)
                    .load(picture)
                    .apply(requestOptions)
                    .into(mPicture);

        } else {
            getSupportActionBar().setTitle("Add a Sampah");
        }
    }

    void readMode(){

        mJenissampah.setFocusableInTouchMode(false);
        mSatuan.setFocusableInTouchMode(false);
        mHarga.setFocusableInTouchMode(false);
        mKeterangan.setFocusableInTouchMode(false);
        mJenissampah.setFocusable(false);
        mSatuan.setFocusable(false);
        mHarga.setFocusable(false);
        mKeterangan.setFocusable(false);
    }
}
