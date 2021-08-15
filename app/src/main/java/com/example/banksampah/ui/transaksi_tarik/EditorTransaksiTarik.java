package com.example.banksampah.ui.transaksi_tarik;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banksampah.R;
import com.example.banksampah.ui.sampah.RequestHandler;

public class EditorTransaksiTarik extends AppCompatActivity {
    EditText et_jumlah_tarik, et_keterangan, et_tanggal_tarik, et_saldo, et_id_user;
    Spinner et_spinner;
    Button button_ubah, button_hapus;
    private int id;
    String tanggal_tarik, id_user, jumlah_tarik, keterangan, saldo_user, nama_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_transaksi_tarik);
        et_tanggal_tarik = findViewById(R.id.ett_tanggal_tarik);
        et_spinner = findViewById(R.id.ett_spinnernama);
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

    }
}
