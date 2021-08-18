package com.example.banksampah.ui.transaksi_setor;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

public class TambahTransaksiSetor extends AppCompatActivity {

    ArrayList<String> iduserList = new ArrayList<>();
    ArrayList<String> spinnernamaList = new ArrayList<>();
    ArrayList<String> saldoList = new ArrayList<>();
    ArrayList<String> idsampahList = new ArrayList<>();
    ArrayList<String> spinnerjenissampahList = new ArrayList<>();
    ArrayList<String> hargaList = new ArrayList<>();
    ArrayList<String> satuanList = new ArrayList<>();
    ArrayAdapter<String> spinnernamaAdapter;
    ArrayAdapter<String> spinnerjenissampahAdapter;

    Spinner spinnernama, spinnerjenissampah;
    EditText et_tanggalsetor, et_id_user, et_id_sampah, et_harga, et_berat, et_total, et_keterangan, et_saldo, et_satuan;
    Button btn_tambah, btn_hitungtotal;
    Context context;
    ApiInterfaceSetor apiInterface;
    ApiInterface apiInterfaceUser;
    RequestQueue requestQueue;
    Calendar myCalendar = Calendar.getInstance();
    String getBerat, getHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_transaksi_setor);

        et_tanggalsetor = findViewById(R.id.ets_tanggal_setor);
        et_id_user = findViewById(R.id.ets_id_user);
        spinnernama = findViewById(R.id.ets_spinnernama);
        et_saldo = findViewById(R.id.ets_saldo);
        et_id_sampah = findViewById(R.id.ets_id_sampah);
        spinnerjenissampah = findViewById(R.id.ets_spinnersampah);
        et_harga = findViewById(R.id.ets_hargasampah);
        et_berat = findViewById(R.id.ets_berat);
        et_total = findViewById(R.id.ets_total);
        et_keterangan = findViewById(R.id.ets_keterangan);
        et_satuan = findViewById(R.id.ets_satuan);
        btn_tambah = findViewById(R.id.ets_btntambah);
        btn_hitungtotal = findViewById(R.id.ets_btnhitungtotal);
        requestQueue = Volley.newRequestQueue(this);

        et_total.setText("0");

        et_harga.setClickable(false);
        et_harga.setFocusable(false);
        et_harga.setFocusableInTouchMode(false);
        et_satuan.setClickable(false);
        et_satuan.setFocusable(false);
        et_satuan.setFocusableInTouchMode(false);
        et_saldo.setClickable(false);
        et_saldo.setFocusable(false);
        et_saldo.setFocusableInTouchMode(false);

        btn_hitungtotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBerat = et_berat.getText().toString();
                getHarga = et_harga.getText().toString();

                if (getBerat.isEmpty()){
                    et_berat.setError("Jumlah Tidak Boleh Kosong");
                } else {
                    int sethasil = Integer.parseInt(getBerat)*Integer.parseInt(getHarga);
                    et_total.setText(String.valueOf(sethasil));
                    Log.e("hasil", String.valueOf(sethasil));
                }
            }
        });

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
        apiInterfaceUser = ApiClient.getApiClient().create(ApiInterface.class);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal_setor = et_tanggalsetor.getText().toString();
                String keterangan = et_keterangan.getText().toString();
                if (tanggal_setor.isEmpty()){
                    et_tanggalsetor.setError("Tanggal Setor Tidak Boleh Kosong");
                } else if (keterangan.isEmpty()){
                    et_keterangan.setError("Keterangan Tidak Boleh Kosong");
                } else {
                    saveData("insert");
                }
            }
        });

        populate_spinner_nama();
        populate_spinner_jenissampah();

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

        String tanggalsetor = et_tanggalsetor.getText().toString().trim();
        String id_user = et_id_user.getText().toString().trim();
        String nama = spinnernama.getSelectedItem().toString().trim();
        String saldo_user = et_saldo.getText().toString().trim();
        String id_sampah = et_id_sampah.getText().toString().trim();
        String jenissampah = spinnerjenissampah.getSelectedItem().toString().trim();
        String satuan = et_satuan.getText().toString().trim();
        String harga = et_harga.getText().toString().trim();
        String jumlah = et_berat.getText().toString().trim();
        String total = et_total.getText().toString().trim();
        String keterangan = et_keterangan.getText().toString().trim();

        int saldoawal = Integer.parseInt(saldo_user);
        int saldoakhir = Integer.parseInt(total);
        int totall = saldoawal + saldoakhir;
        apiInterface = ApiClientSetor.getApiClient().create(ApiInterfaceSetor.class);

        Call<TransaksiSetor> call = apiInterface.insertTransaksiSetor(key, tanggalsetor, id_user, nama, saldo_user, id_sampah, jenissampah, satuan, harga, jumlah, total, keterangan);
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

        Call<User> userCall = apiInterfaceUser.updateSaldo(key, id_user, totall);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                progressDialog.dismiss();

                Log.i(TambahTransaksiTarik.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(TambahTransaksiSetor.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populate_spinner_nama() {
        String url = "http://192.168.1.4/Bank-Sampah/backend/setor/spinner_getnamauser.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("user");
                    for (int i = 0 ; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        String iduser = object.optString("id");
                        String nama = object.optString("nama");
                        String saldo = object.optString("saldo");
                        spinnernamaList.add(nama);
                        saldoList.add(saldo);
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
        spinnernama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedIduser = iduserList.get(position).toString();
                String selectedSaldo = saldoList.get(position).toString();
                et_id_user.setText(selectedIduser);
                et_saldo.setText(selectedSaldo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void populate_spinner_jenissampah() {
        String url = "http://192.168.1.4/Bank-Sampah/backend/setor/spinner_getsampah.php";
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
                        String satuan = object.optString("satuan");
                        spinnerjenissampahList.add(jenissampah);
                        satuanList.add(satuan);
                        hargaList.add(harga);
                        idsampahList.add(idsampah);
                        spinnerjenissampahAdapter = new ArrayAdapter<>(TambahTransaksiSetor.this, simple_spinner_item, spinnerjenissampahList);
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
        spinnerjenissampah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedIdsampah = idsampahList.get(position).toString();
                String selectedharga = hargaList.get(position).toString();
                String selectedsatuan = satuanList.get(position).toString();
                et_id_sampah.setText(selectedIdsampah);
                et_harga.setText(selectedharga);
                et_satuan.setText(selectedsatuan);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}