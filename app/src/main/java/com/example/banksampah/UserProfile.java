package com.example.banksampah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.banksampah.ui.data_user.ApiInterface;
import com.example.banksampah.ui.data_user.DataUserMain;
import com.example.banksampah.ui.data_user.EditorUser;
import com.example.banksampah.ui.data_user.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {
    EditText et_nik, et_nama, et_password, et_rt, et_alamat, et_saldo, et_role, et_telepon, et_email;
    Button btnubah;
    SessionManager manager;
    String sId, sNama, sNik, sPassword, sRt, sAlamat, sTelepon, sEmail, sSaldo, sRole;
    private ApiInterface apiInterface;
    int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        et_nik = findViewById(R.id.up_nik);
        et_password = findViewById(R.id.up_password);
        et_nama = findViewById(R.id.up_nama);
        et_alamat = findViewById(R.id.up_alamat);
        et_rt = findViewById(R.id.up_rt);
        et_telepon = findViewById(R.id.up_telepon);
        et_email = findViewById(R.id.up_email);
        et_saldo = findViewById(R.id.up_saldo);
        et_role = findViewById(R.id.up_role);
        btnubah = findViewById(R.id.up_btnubah);

        manager = new SessionManager(this);
        manager.checkloggin();
        HashMap<String, String> user = manager.getUserDetail();
        sId = user.get(manager.ID);
        sNik = user.get(manager.NIK);
        sPassword = user.get(manager.PASSWORD);
        sNama = user.get(manager.NAMA);
        sAlamat = user.get(manager.ALAMAT);
        sRt = user.get(manager.RT);
        sTelepon = user.get(manager.TELEPON);
        sEmail = user.get(manager.EMAIL);
        sSaldo = user.get(manager.SALDO);
        sRole = user.get(manager.ROLE);

        et_nik.setText(sNik);
        et_password.setText(sPassword);
        et_nama.setText(sNama);
        et_alamat.setText(sAlamat);
        et_rt.setText(sRt);
        et_telepon.setText(sTelepon);
        et_email.setText(sEmail);
        et_saldo.setText(sSaldo);
        et_role.setText(sRole);

        id = Integer.parseInt(sId);

        btnubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData("update", id);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_nik, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private void editData(final String key, final int id) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        String nik = et_nik.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String nama = et_nama.getText().toString().trim();
        String alamat = et_alamat.getText().toString().trim();
        String rt = et_rt.getText().toString().trim();
        String telepon = et_telepon.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String saldo = et_saldo.getText().toString().trim();
        String role = et_role.getText().toString().trim();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<User> call = apiInterface.updateUser(key, id, nik, password, nama, alamat, rt, telepon, email, saldo, role);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                progressDialog.dismiss();

                Log.i(UserProfile.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(UserProfile.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserProfile.this, Profile.class));
                    finish();
                } else {
                    Toast.makeText(UserProfile.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserProfile.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
