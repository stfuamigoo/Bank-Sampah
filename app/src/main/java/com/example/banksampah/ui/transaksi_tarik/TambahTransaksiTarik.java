package com.example.banksampah.ui.transaksi_tarik;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.example.banksampah.ui.data_user.User;
import com.example.banksampah.ui.sampah.Sampah;
import com.example.banksampah.ui.sampah.TambahSampah;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.R.layout.simple_spinner_item;

public class TambahTransaksiTarik extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> spinnernamaList = new ArrayList<>();
    ArrayList<String> saldoList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    ArrayAdapter<String> spinnernamaAdapter;

    Spinner spinnernama;
    EditText et_keterangan, et_jumlah_tarik, et_tanggal_tarik, et_saldo, et_id_user;
    Button btn_tambah;
    Context context;
    ApiInterface apiInterface;
    DatePickerDialog picker;
    Calendar calendar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi_tarik);
        et_jumlah_tarik = findViewById(R.id.ett_jumlah_tarik);
        et_keterangan = findViewById(R.id.ett_keterangan);
        et_tanggal_tarik = findViewById(R.id.ett_tanggal_tarik);
        btn_tambah = findViewById(R.id.ett_btntambah);
        spinnernama = findViewById(R.id.ett_spinnernama);
        et_saldo = findViewById(R.id.ett_saldo);
        et_id_user = findViewById(R.id.ett_id_user);
        requestQueue = Volley.newRequestQueue(this);

        et_saldo.setFocusable(false);
        et_saldo.setFocusableInTouchMode(false);
        et_saldo.setClickable(false);

        context = this;
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        et_tanggal_tarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(TambahTransaksiTarik.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_tanggal_tarik.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData("insert");
            }
        });

        populate_spinner();
    }

    private void saveData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String tanggal_tarik = et_tanggal_tarik.getText().toString().trim();
        String id_user = et_id_user.getText().toString().trim();
        String nama_user = spinnernama.getSelectedItem().toString().trim();
        String jumlah_tarik = et_jumlah_tarik.getText().toString().trim();
        String saldo_user = et_saldo.getText().toString().trim();
        String keterangan = et_keterangan.getText().toString().trim();

        Call<TransaksiTarik> call = apiInterface.insertTransaksiTarik(key, tanggal_tarik, id_user, nama_user, saldo_user, jumlah_tarik, keterangan);
        call.enqueue(new Callback<TransaksiTarik>() {
            @Override
            public void onResponse(Call<TransaksiTarik> call, Response<TransaksiTarik> response) {
                progressDialog.dismiss();

                Log.i(TambahTransaksiTarik.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransaksiTarik> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populate_spinner() {
        String url = "http://192.168.1.6/Bank-Sampah/backend/tarik/spinner_getnamauser.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("user");
                    for (int i = 0 ; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        String id = object.optString("id");
                        String nama = object.optString("nama");
                        String saldo = object.optString("saldo");
                        spinnernamaList.add(nama);
                        saldoList.add(saldo);
                        idList.add(id);
                        spinnernamaAdapter = new ArrayAdapter<>(TambahTransaksiTarik.this, simple_spinner_item, spinnernamaList);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedSaldo = saldoList.get(position).toString();
            String selectedId = idList.get(position).toString();
            et_saldo.setText(selectedSaldo);
            et_id_user.setText(selectedId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}