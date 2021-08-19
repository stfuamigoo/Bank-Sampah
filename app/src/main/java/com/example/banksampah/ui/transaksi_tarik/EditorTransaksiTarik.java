package com.example.banksampah.ui.transaksi_tarik;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banksampah.R;
import com.example.banksampah.ui.data_user.ApiClient;
import com.example.banksampah.ui.data_user.ApiInterface;
import com.example.banksampah.ui.data_user.User;
import com.example.banksampah.ui.sampah.RequestHandler;
import com.example.banksampah.ui.transaksi_setor.EditorTransaksiSetor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorTransaksiTarik extends AppCompatActivity {
    EditText et_jumlah_tarik, et_keterangan, et_tanggal_tarik, et_saldo, et_id_user, et_nama_user;
    Button button_ubah, button_hapus;
    private int id;
    String tanggal_tarik, id_user, jumlah_tarik, keterangan, saldo_user, nama_user;
    ApiInterfaceTarik apiInterfaceTarik;
    ApiInterface apiInterfaceUser;
    Calendar calendar = Calendar.getInstance();
    Context context;

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

        context = this;
        apiInterfaceUser = ApiClient.getApiClient().create(ApiInterface.class);
        apiInterfaceTarik = ApiClientTarik.getApiClient().create(ApiInterfaceTarik.class);

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

        et_tanggal_tarik.setFocusableInTouchMode(false);
        et_tanggal_tarik.setFocusable(false);
        et_tanggal_tarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditorTransaksiTarik.this, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
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

        Log.e("data binding ", id+","+tanggal_tarik+","+id_user+","+nama_user+","+saldo_user+","+jumlah_tarik+","+keterangan);

        setDataFromIntentExtra();
        editMode();
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

    private void deleteData(String key, int id) {
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
                return rh.sendGetRequestParam(ApiClientTarik.URL_DELETE_TRANSAKSI_TARIK, String.valueOf(EditorTransaksiTarik.this.id));
            }
        }

        new deleteData().execute();

        String id_user = et_id_user.getText().toString().trim();
        String saldo_user = et_saldo.getText().toString().trim();
        final int saldo = Integer.parseInt(saldo_user);
        Log.e("data binding ", id+","+tanggal_tarik+","+id_user+","+nama_user+","+saldo_user+","+jumlah_tarik+","+keterangan);
        Call<User> userCall = apiInterfaceUser.updateSaldo(key, id_user, saldo);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("update saldo", String.valueOf(saldo));

                Log.i(EditorTransaksiSetor.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorTransaksiTarik.this, message, Toast.LENGTH_SHORT).show();
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

    private void editData(final String key, final int id){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        final String tanggal_tarik = et_tanggal_tarik.getText().toString().trim();
        final String nama_user = et_nama_user.getText().toString().trim();
        final String saldo_user = et_saldo.getText().toString().trim();
        final String keterangan = et_keterangan.getText().toString().trim();
        final String jumlah_tarik = et_jumlah_tarik.getText().toString().trim();
        final String id_user = et_id_user.getText().toString().trim();

        int uangawal = Integer.parseInt(saldo_user);
        int uangakhir = Integer.parseInt(jumlah_tarik);
        final int saldo = (uangawal - uangakhir);

        if (saldo >= 0) {
            Call<TransaksiTarik> call = apiInterfaceTarik.updateTransaksiTarik(key, id, tanggal_tarik, nama_user, saldo_user, jumlah_tarik, keterangan);
            call.enqueue(new Callback<TransaksiTarik>() {
                @Override
                public void onResponse(Call<TransaksiTarik> call, Response<TransaksiTarik> response) {

                    progressDialog.dismiss();
                    Log.e("save saldo ", String.valueOf(saldo));

                    Log.i(EditorTransaksiTarik.class.getSimpleName(), response.toString());

                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    if (value.equals("1")){
                        Toast.makeText(EditorTransaksiTarik.this, message, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditorTransaksiTarik.this, TransaksiTarikMain.class));
                        finish();
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

            Call<User> userCall = apiInterfaceUser.updateSaldo(key, id_user, saldo);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    progressDialog.dismiss();
                    Log.e("update saldo", String.valueOf(saldo)+","+id_user);
                    Log.e("data binding ", id+","+tanggal_tarik+","+id_user+","+nama_user+","+saldo_user+","+jumlah_tarik+","+keterangan);
                    Log.i(EditorTransaksiSetor.class.getSimpleName(), response.toString());

                    String value = response.body().getValue();
                    String message = response.body().getMassage();

                    if (value.equals("1")){
                        Toast.makeText(EditorTransaksiTarik.this, message, Toast.LENGTH_SHORT).show();
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
            AlertDialog.Builder dialog = new AlertDialog.Builder(EditorTransaksiTarik.this);
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

    private void setDataFromIntentExtra() {
        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit Transaksi " + nama_user.toString());

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
        et_tanggal_tarik.setFocusableInTouchMode(false);
        et_tanggal_tarik.setFocusable(false);
        et_id_user.setFocusableInTouchMode(true);
        et_nama_user.setFocusableInTouchMode(true);
        et_saldo.setFocusableInTouchMode(true);
        et_jumlah_tarik.setFocusableInTouchMode(true);
        et_keterangan.setFocusableInTouchMode(true);
    }
}
