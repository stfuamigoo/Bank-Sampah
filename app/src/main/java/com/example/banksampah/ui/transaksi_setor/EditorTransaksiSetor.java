package com.example.banksampah.ui.transaksi_setor;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.banksampah.R;
import com.example.banksampah.ui.data_user.ApiClient;
import com.example.banksampah.ui.data_user.ApiInterface;
import com.example.banksampah.ui.data_user.User;
import com.example.banksampah.ui.transaksi_tarik.TambahTransaksiTarik;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.layout.simple_spinner_item;

public class EditorTransaksiSetor extends AppCompatActivity {

    EditText et_tanggalsetor, et_id_user, et_id_sampah, et_nama, et_jenissampah, et_harga, et_satuan, et_jumlah, et_total, et_keterangan, et_saldo;
    Button mEdit, mDelete, hitung;
    Context context;
    ApiInterfaceSetor apiInterface;
    ApiInterface apiInterfaceUser;
    RequestQueue requestQueue;
    Calendar myCalendar = Calendar.getInstance();

    private String tanggalsetor, id_user, id_sampah, nama, jenissampah, harga, satuan, jumlah, total, keterangan, saldo, getBerat, getTanggalsetor, getHarga;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_transaksi_setor);

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
        mEdit = findViewById(R.id.ets_btnubah);
        mDelete = findViewById(R.id.ets_btnhapus);
        hitung = findViewById(R.id.ets_btnhitungtotal);
        requestQueue = Volley.newRequestQueue(this);

        et_tanggalsetor.setFocusableInTouchMode(false);
        et_tanggalsetor.setFocusable(false);
        et_tanggalsetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditorTransaksiSetor.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        context = this;
        apiInterface = ApiClientSetor.getApiClient().create(ApiInterfaceSetor.class);
        apiInterfaceUser = ApiClient.getApiClient().create(ApiInterface.class);

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggalsetor = et_tanggalsetor.getText().toString().trim();
                String jumlah = et_jumlah.getText().toString().trim();
                String keterangan = et_keterangan.getText().toString().trim();

                if (tanggalsetor.isEmpty()){
                    et_tanggalsetor.setError("Tanggal Setor Tidak Boleh Kosong");
                } else if (jumlah.isEmpty()){
                    et_jumlah.setError("Tanggal Setor Tidak Boleh Kosong");
                } else if (keterangan.isEmpty()){
                    et_keterangan.setError("Tanggal Setor Tidak Boleh Kosong");
                } else if (jumlah.equals("0")){
                    et_jumlah.setError("Tanggal Setor Tidak Boleh Kosong");
                } else {
                    editData("update", id);
                }
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorTransaksiSetor.this);
                dialog.setMessage("Apakah Anda Yakin Ingin Menghapus Transaksi Setor ini ?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id);
                        startActivity(new Intent(EditorTransaksiSetor.this, TransaksiSetorMain.class));
                        finish();
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

        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBerat = et_jumlah.getText().toString();
                getHarga = et_harga.getText().toString();

                if (getBerat.isEmpty()){
                    et_jumlah.setError("Jumlah Tidak Boleh Kosong");
                } else {
                    int sethasil = Integer.parseInt(getBerat)*Integer.parseInt(getHarga);
                    et_total.setText(String.valueOf(sethasil));
                    Log.e("hasil", String.valueOf(sethasil));
                }
            }
        });


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
        editMode();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setBirth();
        }

    };

    private void setBirth() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_tanggalsetor.setText(sdf.format(myCalendar.getTime()));
    }

    private void deleteData(String key, int id) {

        class deleteData extends AsyncTask<Void,Void,String> {

            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(
                        EditorTransaksiSetor.this,"Menghapus Data",
                        "Tunggu Sebentar",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(EditorTransaksiSetor.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(ApiClientSetor.URL_DELETE_TRANSAKSI_SETOR, String.valueOf(EditorTransaksiSetor.this.id));
            }
        }

        new deleteData().execute();

        String id_user = et_id_user.getText().toString().trim();
        String saldo_user = et_saldo.getText().toString().trim();
        final int saldo = Integer.parseInt(saldo_user);

        Call<User> userCall = apiInterfaceUser.updateSaldo(key, id_user, saldo);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("update saldo", String.valueOf(saldo));

                Log.i(EditorTransaksiSetor.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorTransaksiSetor.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    private void editData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String tanggalsetor = et_tanggalsetor.getText().toString().trim();
        String id_user = et_id_user.getText().toString().trim();
        String nama = et_nama.getText().toString().trim();
        String saldo_user = et_saldo.getText().toString().trim();
        String jenissampah = et_jenissampah.getText().toString().trim();
        String satuan = et_satuan.getText().toString().trim();
        String harga = et_harga.getText().toString().trim();
        String jumlah = et_jumlah.getText().toString().trim();
        String total = et_total.getText().toString().trim();
        String keterangan = et_keterangan.getText().toString().trim();

        int saldoawal = Integer.parseInt(saldo_user);
        int saldoakhir = Integer.parseInt(total);
        final int saldo = saldoawal + saldoakhir;

        if (saldo >= 0){
            Call<TransaksiSetor> call = apiInterface.updateSetor(key, id, tanggalsetor, nama, saldo_user, jenissampah, satuan, harga, jumlah, total, keterangan);
            call.enqueue(new Callback<TransaksiSetor>() {
                @Override
                public void onResponse(Call<TransaksiSetor> call, Response<TransaksiSetor> response) {

                    progressDialog.dismiss();

                    Log.i(EditorTransaksiSetor.class.getSimpleName(), response.toString());

                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    if (value.equals("1")){
                        Toast.makeText(EditorTransaksiSetor.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(EditorTransaksiSetor.this, message, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<TransaksiSetor> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(EditorTransaksiSetor.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            Call<User> userCall = apiInterfaceUser.updateSaldo(key, id_user, saldo);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    progressDialog.dismiss();
                    Log.e("update saldo", String.valueOf(saldo));

                    Log.i(EditorTransaksiSetor.class.getSimpleName(), response.toString());

                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    if (value.equals("1")){
                        Toast.makeText(EditorTransaksiSetor.this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        } else {
            progressDialog.dismiss();
            AlertDialog.Builder dialog = new AlertDialog.Builder(EditorTransaksiSetor.this);
            dialog.setMessage("Data Gagal Terupdate");
            dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }

    }

    private void setDataFromIntentExtra() {

        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit Transaksi " + nama.toString());

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
    }

    private void editMode(){

        et_tanggalsetor.setFocusableInTouchMode(true);
        et_id_user.setFocusableInTouchMode(true);
        et_nama.setFocusableInTouchMode(true);
        et_saldo.setFocusableInTouchMode(true);
        et_id_sampah.setFocusableInTouchMode(true);
        et_jenissampah.setFocusableInTouchMode(true);
        et_satuan.setFocusableInTouchMode(true);
        et_harga.setFocusableInTouchMode(true);
        et_jumlah.setFocusableInTouchMode(true);
        et_total.setFocusableInTouchMode(true);
        et_keterangan.setFocusableInTouchMode(true);
    }
}