package com.example.banksampah.ui.transaksi_setor;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.banksampah.R;

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

public class TambahTransaksiSetor extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    ArrayList<String> spinnernamaList = new ArrayList<>();
    ArrayList<String> spinnerjenissampahList = new ArrayList<>();
    ArrayList<String> hargaList = new ArrayList<>();
    ArrayList<String> jenissampahList = new ArrayList<>();
    ArrayList<String> iduserList = new ArrayList<>();
    ArrayList<String> idsampahList = new ArrayList<>();
    ArrayAdapter<String> spinnernamaAdapter;
    ArrayAdapter<String> spinnerjenissampahAdapter;

    Spinner spinnernama, spinnerjenissampah;
    EditText et_tanggalsetor, et_id_user, et_id_sampah, et_nama, et_jenissampah, et_harga, et_berat, et_total, et_keterangan;
    Button btn_tambah,hitungtotal;
    Context context;
    ApiInterfaceSetor apiInterface;
    DatePickerDialog picker;
    RequestQueue requestQueue;
    Calendar myCalendar = Calendar.getInstance();

    private String tanggalsetor, id_user, id_sampah, nama, jenissampah, harga,berat, total, keterangan;
    Integer hitung=0;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi_setor);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        et_tanggalsetor = findViewById(R.id.et_tanggalsetor);
        et_id_user = findViewById(R.id.et_id_user);
        et_id_sampah = findViewById(R.id.et_id_sampah);
        et_harga = findViewById(R.id.et_harga);
        et_berat = findViewById(R.id.et_berat);
        et_total = findViewById(R.id.et_total);
        et_keterangan = findViewById(R.id.et_keterangan);
        spinnernama = findViewById(R.id.et_spinnernama);
        spinnerjenissampah = findViewById(R.id.et_spinnerjenissampah);
        btn_tambah = findViewById(R.id.et_btn_tambah_setor);
        hitungtotal = findViewById(R.id.hitungtotal);
        requestQueue = Volley.newRequestQueue(this);

//        et_saldo.setFocusable(false);
//        et_saldo.setFocusableInTouchMode(false);
//        et_saldo.setClickable(false);

        et_tanggalsetor.setFocusableInTouchMode(false);
        et_tanggalsetor.setFocusable(false);
        et_tanggalsetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TambahTransaksiSetor.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        context = this;
        apiInterface = ApiClientSetor.getApiClient().create(ApiInterfaceSetor.class);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData("insert");
            }
        });
        hitungtotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitung=R.id.et_berat*R.id.et_harga;
            }
        });

        populate_spinner_nama();
        populate_spinner_jenissampah();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        tanggalsetor = intent.getStringExtra("tanggalsetor");
        id_user = intent.getStringExtra("id_user");
        id_sampah = intent.getStringExtra("id_sampah");
        nama = intent.getStringExtra("nama");
        jenissampah = intent.getStringExtra("jenissampah");
        harga = (intent.getStringExtra("harga"));
        berat = (intent.getStringExtra("berat"));
        total = intent.getStringExtra("total");
        keterangan = intent.getStringExtra("keterangan");

        setDataFromIntentExtra();
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

    private void saveData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        readMode();

        String tanggalsetor = et_tanggalsetor.getText().toString().trim();
        String id_user = et_id_user.getText().toString().trim();
        String id_sampah = et_id_sampah.getText().toString().trim();
        String nama = spinnernama.getSelectedItem().toString().trim();
        String jenissampah = spinnerjenissampah.getSelectedItem().toString().trim();
        String harga = et_harga.getText().toString().trim();
        String berat = et_berat.getText().toString().trim();
        String total = et_total.getText().toString().trim();
        String keterangan = et_keterangan.getText().toString().trim();

        apiInterface = ApiClientSetor.getApiClient().create(ApiInterfaceSetor.class);

        Call<TransaksiSetor> call = apiInterface.insertTransaksiSetor(key, tanggalsetor, id_user, id_sampah, nama, jenissampah, harga, berat, total, keterangan);
        call.enqueue(new Callback<TransaksiSetor>() {
            @Override
            public void onResponse(Call<TransaksiSetor> call, Response<TransaksiSetor> response) {
                progressDialog.dismiss();

                Log.i(TambahTransaksiSetor.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiSetor> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataFromIntentExtra() {

        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + nama.toString());

            et_tanggalsetor.setText(tanggalsetor);
            et_id_user.setText(id_user);
            et_id_sampah.setText(id_sampah);
            et_nama.setText(nama);
            et_jenissampah.setText(jenissampah);
            et_harga.setText(harga);
            et_berat.setText(berat);
            et_total.setText(total);
            et_keterangan.setText(keterangan);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.bg);
            requestOptions.error(R.drawable.bg);

        } else {
            getSupportActionBar().setTitle("Add a Setor");
        }
    }

    private void populate_spinner_nama() {
        String url = "https://4e02fb7b82af.ngrok.io/Bank-Sampah/backend/setor/spinner_getnamauser.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("user");
                    for (int i = 0 ; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        String iduser = object.optString("id");
                        String nama = object.optString("nama");
//                        String saldo = object.optString("saldo");
                        spinnernamaList.add(nama);
//                        saldoList.add(saldo);
                        iduserList.add(iduser);
                        spinnernamaAdapter = new ArrayAdapter<>(TambahTransaksiSetor.this, simple_spinner_item, spinnernamaList);
                        spinnernamaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnernama.setAdapter(spinnernamaAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        spinnernama.setOnItemSelectedListener(this);
    }

    private void populate_spinner_jenissampah() {
        String url = "https://4e02fb7b82af.ngrok.io/Bank-Sampah/backend/setor/spinner_getsampah.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("sampah");
                    for (int i = 0 ; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        String idsampah = object.optString("id");
                        String jenissampah = object.optString("jenissampah");
                        String harga = object.optString("harga");
//                        String saldo = object.optString("saldo");
                        spinnerjenissampahList.add(jenissampah);
                        hargaList.add(harga);
//                        saldoList.add(saldo);
                        idsampahList.add(idsampah);
                        spinnerjenissampahAdapter = new ArrayAdapter<>(TambahTransaksiSetor.this, simple_spinner_item, spinnernamaList);
                        spinnerjenissampahAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerjenissampah.setAdapter(spinnerjenissampahAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        spinnerjenissampah.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String selectedSaldo = saldoList.get(position).toString();
        String selectedIduser = iduserList.get(position).toString();
        String selectedIdsampah = idsampahList.get(position).toString();
//        String selectedjenissampah = jenissampahList.get(position).toString();
        String selectedharga = hargaList.get(position).toString();
//        et_saldo.setText(selectedSaldo);
        et_id_user.setText(selectedIduser);
        et_id_sampah.setText(selectedIdsampah);
//        et_jenissampah.setText(selectedjenissampah);
        et_harga.setText(selectedharga);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void readMode(){

        et_tanggalsetor.setFocusableInTouchMode(false);
        et_id_user.setFocusableInTouchMode(false);
        et_id_sampah.setFocusableInTouchMode(false);
        et_nama.setFocusableInTouchMode(false);
        et_jenissampah.setFocusableInTouchMode(false);
        et_harga.setFocusableInTouchMode(false);
        et_berat.setFocusableInTouchMode(false);
        et_total.setFocusableInTouchMode(false);
        et_keterangan.setFocusableInTouchMode(false);

        et_tanggalsetor.setFocusable(false);
        et_id_user.setFocusable(false);
        et_id_sampah.setFocusable(false);
        et_nama.setFocusable(false);
        et_jenissampah.setFocusable(false);
        et_harga.setFocusable(false);
        et_berat.setFocusable(false);
        et_total.setFocusable(false);
        et_keterangan.setFocusable(false);

    }
}