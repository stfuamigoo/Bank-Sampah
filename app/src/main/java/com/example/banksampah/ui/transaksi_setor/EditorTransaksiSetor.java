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
    Button mEdit, mDelete;
    Context context;
    ApiInterfaceSetor apiInterface;
    DatePickerDialog picker;
    RequestQueue requestQueue;
    Calendar myCalendar = Calendar.getInstance();

    private String tanggalsetor, id_user, id_sampah, nama, jenissampah, harga, berat, total, keterangan;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_transaksi_setor);

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
        mEdit = findViewById(R.id.edit);
        mDelete = findViewById(R.id.delete);
        requestQueue = Volley.newRequestQueue(this);

//        et_saldo.setFocusable(false);
//        et_saldo.setFocusableInTouchMode(false);
//        et_saldo.setClickable(false);

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

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData("update", id);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(spinnernama, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorTransaksiSetor.this);
                dialog.setMessage("Delete setor?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id);
                        startActivity(new Intent(EditorTransaksiSetor.this, TransaksiSetorMain.class));
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
        tanggalsetor = intent.getStringExtra("tanggalsetor");
        id_user = intent.getStringExtra("id_user");
        id_sampah = intent.getStringExtra("id_sampah");
        nama = intent.getStringExtra("nama");
        jenissampah = intent.getStringExtra("jenissampah");
        harga = intent.getStringExtra("harga");
        berat = intent.getStringExtra("berat");
        total = intent.getStringExtra("total");
        keterangan = intent.getStringExtra("keterangan");

        setDataFromIntentExtra();
        editMode();

//        populate_spinner_nama();
//        populate_spinner_jenissampah();
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

    private void deleteData(String delete, int id) {

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

    }

    private void editData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String tanggalsetor = et_tanggalsetor.getText().toString().trim();
//        String id_user = et_id_user.getText().toString().trim();
//        String id_sampah = et_id_sampah.getText().toString().trim();
        String nama = spinnernama.getSelectedItem().toString().trim();
        String jenissampah = spinnerjenissampah.getSelectedItem().toString().trim();
        String harga = et_harga.getText().toString().trim();
        String berat = et_berat.getText().toString().trim();
        String total = et_total.getText().toString().trim();
        String keterangan = et_keterangan.getText().toString().trim();

        apiInterface = ApiClientSetor.getApiClient().create(ApiInterfaceSetor.class);

        Call<TransaksiSetor> call = apiInterface.updateSetor(key, id,tanggalsetor, nama, jenissampah, harga, berat, total, keterangan);

        call.enqueue(new Callback<TransaksiSetor>() {
            @Override
            public void onResponse(Call<TransaksiSetor> call, Response<TransaksiSetor> response) {

                progressDialog.dismiss();

                Log.i(EditorTransaksiSetor.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorTransaksiSetor.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditorTransaksiSetor.this,EditorTransaksiSetor.class));
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

    private void editMode(){

        et_tanggalsetor.setFocusableInTouchMode(true);
        et_id_user.setFocusableInTouchMode(true);
        et_id_sampah.setFocusableInTouchMode(true);
        et_nama.setFocusableInTouchMode(true);
        et_jenissampah.setFocusableInTouchMode(true);
        et_harga.setFocusableInTouchMode(true);
        et_berat.setFocusableInTouchMode(true);
        et_total.setFocusableInTouchMode(true);
        et_keterangan.setFocusableInTouchMode(true);
    }
}