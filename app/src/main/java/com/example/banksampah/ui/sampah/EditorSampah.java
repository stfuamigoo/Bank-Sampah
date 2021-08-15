package com.example.banksampah.ui.sampah;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class EditorSampah extends AppCompatActivity {

    private EditText mJenissampah, mSatuan, mHarga, mKeterangan;
    private ImageView mPicture;
    private Button mEdit, mDelete;
    private FloatingActionButton mFabChoosePic;

    private String jenissampah, satuan, harga, picture, keterangan;
    private int id;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_sampah);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mJenissampah = findViewById(R.id.jenissampah);
        mSatuan = findViewById(R.id.satuan);
        mHarga = findViewById(R.id.harga);
        mKeterangan = findViewById(R.id.keterangan);
        mPicture = findViewById(R.id.picture);
        mEdit = findViewById(R.id.edit);
        mDelete = findViewById(R.id.delete);
        mFabChoosePic = findViewById(R.id.fabChoosePic);


        mFabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData("update", id);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mJenissampah, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorSampah.this);
                dialog.setMessage("Delete sampah?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id, picture);
                        startActivity(new Intent(EditorSampah.this,MainSampah.class));
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

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
        editMode();
    }

    private void deleteData(String delete, int id, String picture) {

        class deleteData extends AsyncTask<Void,Void,String> {

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(
                        EditorSampah.this,"Menghapus Data",
                        "Tunggu Sebentar",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(EditorSampah.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(ApiClient.URL_DELETE_SAMPAH, String.valueOf(EditorSampah.this.id));
            }
        }

        new deleteData().execute();

    }

    private void editData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
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

        Call<Sampah> call = apiInterface.updateSampah(key, id,jenissampah, satuan, harga, keterangan, picture);

        call.enqueue(new Callback<Sampah>() {
            @Override
            public void onResponse(Call<Sampah> call, Response<Sampah> response) {

                progressDialog.dismiss();

                Log.i(EditorSampah.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorSampah.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditorSampah.this,EditorSampah.class));
                } else {
                    Toast.makeText(EditorSampah.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Sampah> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorSampah.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmDelete() {
        new androidx.appcompat.app.AlertDialog.Builder(EditorSampah.this)
                .setMessage("Hapus data sampah ini ?")
                .setTitle("KONFIRMASI")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteSampah("delete", id, picture);
                        startActivity(new Intent(EditorSampah.this,MainSampah.class));
                    }
                })
                .setNegativeButton("Tidak",null)
                .show();
    }

    private void deleteSampah(String delete, int id, String picture) {
        class DeleteSampah extends AsyncTask<Void,Void,String> {

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(
                        EditorSampah.this,"Menghapus Data",
                        "Tunggu Sebentar",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(EditorSampah.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(ApiClient.URL_DELETE_SAMPAH, String.valueOf(EditorSampah.this.id));
            }
        }

        new DeleteSampah().execute();
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

            Glide.with(EditorSampah.this)
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



    }

    private void editMode(){

        mJenissampah.setFocusableInTouchMode(true);
        mSatuan.setFocusableInTouchMode(true);
        mHarga.setFocusableInTouchMode(true);
        mKeterangan.setFocusableInTouchMode(true);


    }
}