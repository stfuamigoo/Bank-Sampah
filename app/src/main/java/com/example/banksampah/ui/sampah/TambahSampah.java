package com.example.banksampah.ui.sampah;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.banksampah.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahSampah extends AppCompatActivity {

    private EditText mJenissampah, mSatuan, mHarga, mKeterangan;
    private ImageView mPicture;
    private FloatingActionButton mFabChoosePic;
    private Button mSave;


    private String jenissampah, satuan, harga, picture, keterangan;
    private int id;


    private Bitmap bitmap;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_sampah);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mJenissampah = findViewById(R.id.jenissampah);
        mSatuan = findViewById(R.id.satuan);
        mHarga = findViewById(R.id.harga);
        mKeterangan = findViewById(R.id.keterangan);
        mPicture = findViewById(R.id.picture);
        mFabChoosePic = findViewById(R.id.fabChoosePic);
        mSave = findViewById(R.id.save);

        mFabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData("insert");
            }
        });


        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        jenissampah = intent.getStringExtra("jenissampah");
        satuan = intent.getStringExtra("satuan");
        harga = intent.getStringExtra("harga");
        keterangan = intent.getStringExtra("keterangan");
        picture = intent.getStringExtra("picture");

        setDataFromIntentExtra();
    }

    private void saveData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        readMode();

        String jenissampah = mJenissampah.getText().toString().trim();
        String satuan = mSatuan.getText().toString().trim();
        String harga = mHarga.getText().toString().trim();
        String keterangan = mKeterangan.getText().toString().trim();
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Sampah> call = apiInterface.insertSampah(key, jenissampah, satuan, harga, keterangan, picture);

        call.enqueue(new Callback<Sampah>() {
            @Override
            public void onResponse(Call<Sampah> call, Response<Sampah> response) {

                progressDialog.dismiss();

                Log.i(TambahSampah.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(TambahSampah.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Sampah> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TambahSampah.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
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

            Glide.with(TambahSampah.this)
                    .load(picture)
                    .apply(requestOptions)
                    .into(mPicture);

        } else {
            getSupportActionBar().setTitle("Add a Sampah");
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                mPicture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

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


        mFabChoosePic.setVisibility(View.INVISIBLE);

    }

    private void editMode(){

        mJenissampah.setFocusableInTouchMode(true);
        mSatuan.setFocusableInTouchMode(true);
        mHarga.setFocusableInTouchMode(true);
        mKeterangan.setFocusableInTouchMode(true);


        mFabChoosePic.setVisibility(View.VISIBLE);
    }
}