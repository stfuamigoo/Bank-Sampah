package com.example.banksampah.ui.transaksi_tarik;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.banksampah.R;
import com.example.banksampah.ui.data_user.DataUserMain;
import com.example.banksampah.ui.data_user.EditorUser;
import com.example.banksampah.ui.data_user.User;
import com.example.banksampah.ui.sampah.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class EditorTransaksiTarik extends AppCompatActivity {
    EditText et_jumlah_tarik, et_keterangan, et_tanggal_tarik, et_saldo, et_id_user, et_nama_user;
    Button button_ubah, button_hapus;
    private int id;
    String tanggal_tarik, id_user, jumlah_tarik, keterangan, saldo_user, nama_user;
    com.example.banksampah.ui.transaksi_tarik.ApiInterface apiInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_transaksi_tarik);
        et_tanggal_tarik = findViewById(R.id.ett_tanggal_tarik);
        et_nama_user = findViewById(R.id.ett_nama_user);
        et_id_user = findViewById(R.id.ett_id_user);
        et_jumlah_tarik = findViewById(R.id.ett_jumlah_tarik);
        et_saldo = findViewById(R.id.ett_saldo);
        et_keterangan = findViewById(R.id.ett_keterangan);
        button_ubah = findViewById(R.id.ett_btnubah);
        button_hapus = findViewById(R.id.ett_btnhapus);

        button_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData("update", id);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_tanggal_tarik, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        button_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorTransaksiTarik.this);
                dialog.setMessage("Delete " +tanggal_tarik+ "?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id);
                        startActivity(new Intent(EditorTransaksiTarik.this, TransaksiTarikMain.class));
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
        tanggal_tarik = intent.getStringExtra("tanggal_tarik");
        id_user = intent.getStringExtra("id_user");
        nama_user = intent.getStringExtra("nama_user");
        saldo_user = intent.getStringExtra("saldo_user");
        jumlah_tarik = intent.getStringExtra("jumlah_tarik");
        keterangan = intent.getStringExtra("keterangan");

        setDataFromIntentExtra();
        editMode();
    }

    private void deleteData(String delete, int id) {
        class deleteData extends AsyncTask<Void,Void,String> {

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(
                        EditorTransaksiTarik.this,"Menghapus Data",
                        "Tunggu Sebentar",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(EditorTransaksiTarik.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(ApiClient.URL_DELETE_TRANSAKSI_TARIK, String.valueOf(EditorTransaksiTarik.this.id));
            }
        }
        new deleteData().execute();
    }

    private void editData(final String key, final int id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String tanggal_tarik = et_tanggal_tarik.getText().toString().trim();
        String nama_user = et_nama_user.getText().toString().trim();
        String saldo_user = et_saldo.getText().toString().trim();
        String keterangan = et_keterangan.getText().toString().trim();
        String jumlah_tarik = et_jumlah_tarik.getText().toString().trim();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<TransaksiTarik> call = apiInterface.updateTransaksiTarik(key, id, tanggal_tarik, nama_user, saldo_user, jumlah_tarik,keterangan);

        call.enqueue(new Callback<TransaksiTarik>() {
            @Override
            public void onResponse(Call<TransaksiTarik> call, Response<TransaksiTarik> response) {

                progressDialog.dismiss();

                Log.i(EditorUser.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorTransaksiTarik.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditorTransaksiTarik.this, TransaksiTarikMain.class));
                } else {
                    Toast.makeText(EditorTransaksiTarik.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<TransaksiTarik> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorTransaksiTarik.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + tanggal_tarik.toString());

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
    }

    private void editMode(){
        et_tanggal_tarik.setFocusableInTouchMode(true);
        et_id_user.setFocusableInTouchMode(true);
        et_nama_user.setFocusableInTouchMode(true);
        et_saldo.setFocusableInTouchMode(true);
        et_jumlah_tarik.setFocusableInTouchMode(true);
        et_keterangan.setFocusableInTouchMode(true);
    }
}
