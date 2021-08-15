package com.example.banksampah.ui.data_user;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banksampah.R;
import com.example.banksampah.ui.sampah.RequestHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorUser extends AppCompatActivity {
    private ApiInterface apiInterface;
    private EditText edt_nik, edt_password, edt_alamat, edt_rt, edt_telepon, edt_email, edt_saldo, edt_role;
    private TextView edt_nama;
    private Button btn_ubah, btn_hapus;
    private int id;
    private String nik, password, nama, alamat, rt, telepon, email, saldo, role;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_user);

        edt_nik = findViewById(R.id.eu_nik);
        edt_password = findViewById(R.id.eu_password);
        edt_alamat = findViewById(R.id.eu_alamat);
        edt_rt = findViewById(R.id.eu_rt);
        edt_telepon = findViewById(R.id.eu_telepon);
        edt_email = findViewById(R.id.eu_email);
        edt_saldo = findViewById(R.id.eu_saldo);
        edt_role = findViewById(R.id.eu_role);
        btn_ubah = findViewById(R.id.eu_btnubah);
        btn_hapus = findViewById(R.id.eu_btnhapus);
        edt_nama = findViewById(R.id.eu_nama);

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData("update", id);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edt_nik, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditorUser.this);
                dialog.setMessage("Delete " +nama+ "?");
                dialog.setPositiveButton("Yes" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id);
                        startActivity(new Intent(EditorUser.this, DataUserMain.class));
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
        nik = intent.getStringExtra("nik");
        password = intent.getStringExtra("password");
        nama = intent.getStringExtra("nama");
        alamat = intent.getStringExtra("alamat");
        rt = intent.getStringExtra("rt");
        telepon = intent.getStringExtra("telepon");
        email = intent.getStringExtra("email");
        saldo = intent.getStringExtra("saldo");
        role = intent.getStringExtra("role");

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
                        EditorUser.this,"Menghapus Data",
                        "Tunggu Sebentar",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(EditorUser.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                RequestHandler rh = new RequestHandler();
                return rh.sendGetRequestParam(ApiClient.URL_DELETE_USER, String.valueOf(EditorUser.this.id));
            }
        }
        new deleteData().execute();
    }

    private void setDataFromIntentExtra() {
        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + nama.toString());

            edt_nik.setText(nik);
            edt_password.setText(password);
            edt_nama.setText(nama);
            edt_alamat.setText(alamat);
            edt_rt.setText(rt);
            edt_telepon.setText(telepon);
            edt_email.setText(email);
            edt_saldo.setText(saldo);
            edt_role.setText(role);
        } else {
            getSupportActionBar().setTitle("Add a Sampah");
        }
    }

    void readMode(){
        edt_nik.setFocusableInTouchMode(false);
        edt_password.setFocusableInTouchMode(false);
        edt_alamat.setFocusableInTouchMode(false);
        edt_rt.setFocusableInTouchMode(false);
        edt_telepon.setFocusableInTouchMode(false);
        edt_saldo.setFocusableInTouchMode(false);
        edt_email.setFocusableInTouchMode(false);
        edt_role.setFocusableInTouchMode(false);

        edt_nik.setFocusable(false);
        edt_password.setFocusable(false);
        edt_alamat.setFocusable(false);
        edt_rt.setFocusable(false);
        edt_telepon.setFocusable(false);
        edt_email.setFocusable(false);
        edt_saldo.setFocusable(false);
        edt_role.setFocusable(false);
    }

    private void editMode(){
        edt_nik.setFocusableInTouchMode(true);
        edt_password.setFocusableInTouchMode(true);
        edt_alamat.setFocusableInTouchMode(true);
        edt_rt.setFocusableInTouchMode(true);
        edt_telepon.setFocusableInTouchMode(true);
        edt_saldo.setFocusableInTouchMode(true);
        edt_email.setFocusableInTouchMode(true);
        edt_role.setFocusableInTouchMode(true);
    }

    private void editData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String nik = edt_nik.getText().toString().trim();
        String password = edt_password.getText().toString().trim();
        String nama = edt_nama.getText().toString().trim();
        String alamat = edt_alamat.getText().toString().trim();
        String rt = edt_rt.getText().toString().trim();
        String telepon = edt_telepon.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String saldo = edt_saldo.getText().toString().trim();
        String role = edt_role.getText().toString().trim();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<User> call = apiInterface.updateUser(key, id, nik, password, nama, alamat, rt, telepon, email, saldo, role);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                progressDialog.dismiss();

                Log.i(EditorUser.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditorUser.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditorUser.this,DataUserMain.class));
                } else {
                    Toast.makeText(EditorUser.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditorUser.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
