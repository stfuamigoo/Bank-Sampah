package com.example.banksampah.ui.transaksi_tarik;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.banksampah.R;
import com.example.banksampah.ui.data_user.User;

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

public class TambahTransaksiTarik extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> spinnernamaList = new ArrayList<>();
    ArrayList<String> saldoList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    ArrayAdapter<String> spinnernamaAdapter;

    Spinner spinnernama;
    EditText et_keterangan, et_jumlah_tarik, et_tanggal_tarik, et_saldo, et_id_user;
    Button btn_tambah;
    Context context;
    ApiInterfaceTarik apiInterfaceTarik;
    com.example.banksampah.ui.data_user.ApiInterface apiInterfaceUser;
    DatePickerDialog picker;
    Calendar calendar = Calendar.getInstance();
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
        apiInterfaceTarik = ApiClientTarik.getApiClient().create(ApiInterfaceTarik.class);
        apiInterfaceUser = com.example.banksampah.ui.data_user.ApiClient.getApiClient().create(com.example.banksampah.ui.data_user.ApiInterface.class);

        et_tanggal_tarik.setFocusableInTouchMode(false);
        et_tanggal_tarik.setFocusable(false);
        et_tanggal_tarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TambahTransaksiTarik.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal_tarik = et_tanggal_tarik.getText().toString().trim();
                String jumlah_tarik = et_jumlah_tarik.getText().toString().trim();
                String keterangan = et_keterangan.getText().toString().trim();

                if (tanggal_tarik.isEmpty()) {
                    et_tanggal_tarik.setError("Tanggal Tidak Boleh Kosong");
                } else if (jumlah_tarik.isEmpty()) {
                    et_jumlah_tarik.setError("Jumlah Tidak Boleh Kosong");
                } else if (keterangan.isEmpty()) {
                    et_keterangan.setError("Keterangan Boleh Kosong");
                } else {
                    saveData("insert");
                }
            }
        });

        populate_spinner();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setBirth();
        }

    };

    private void setBirth() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_tanggal_tarik.setText(sdf.format(calendar.getTime()));
    }

    private void saveData(final String key) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        final String tanggal_tarik = et_tanggal_tarik.getText().toString().trim();
        final String id_user = et_id_user.getText().toString().trim();
        final String nama_user = spinnernama.getSelectedItem().toString().trim();
        final String jumlah_tarik = et_jumlah_tarik.getText().toString().trim();
        final String saldo_user = et_saldo.getText().toString().trim();
        final String keterangan = et_keterangan.getText().toString().trim();

        int uangawal = Integer.parseInt(saldo_user);
        int uangakhir = Integer.parseInt(jumlah_tarik);
        final int saldo = (uangawal - uangakhir);

        if (saldo >= 0){
            Log.e("hasil ", String.valueOf(saldo));
            Call<TransaksiTarik> call = apiInterfaceTarik.insertTransaksiTarik(key, tanggal_tarik, id_user, nama_user, saldo_user, jumlah_tarik, keterangan);
            call.enqueue(new Callback<TransaksiTarik>() {
                @Override
                public void onResponse(Call<TransaksiTarik> call, Response<TransaksiTarik> response) {
                    progressDialog.dismiss();
                    Log.e("data binding ", tanggal_tarik+","+id_user+","+nama_user+","+saldo_user+","+jumlah_tarik+","+keterangan);
                    Log.i(TambahTransaksiTarik.class.getSimpleName(), response.toString());

                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    if (value.equals("1")){
                        Toast.makeText(TambahTransaksiTarik.this, message, Toast.LENGTH_SHORT).show();
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

            Call<User> userCall = apiInterfaceUser.updateSaldo(key, id_user, saldo);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    progressDialog.dismiss();
                    Log.e("update saldo", String.valueOf(saldo));

                    Log.i(TambahTransaksiTarik.class.getSimpleName(), response.toString());

                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    if (value.equals("1")){
                        Toast.makeText(TambahTransaksiTarik.this, message, Toast.LENGTH_SHORT).show();
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
        } else {
            progressDialog.dismiss();
            AlertDialog.Builder dialog = new AlertDialog.Builder(TambahTransaksiTarik.this);
            dialog.setMessage("Jumlah Tarik Terlalu Besar");
            dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.show();
        }
    }

    private void populate_spinner() {
        String url = "http://192.168.1.4/Bank-Sampah/backend/tarik/spinner_getnamauser.php";
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