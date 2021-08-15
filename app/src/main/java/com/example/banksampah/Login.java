package com.example.banksampah;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.banksampah.Config.TAG_ALAMAT;
import static com.example.banksampah.Config.TAG_EMAIL;
import static com.example.banksampah.Config.TAG_ID_USER;
import static com.example.banksampah.Config.TAG_NAMA;
import static com.example.banksampah.Config.TAG_ROLE;
import static com.example.banksampah.Config.TAG_NIK;
import static com.example.banksampah.Config.TAG_RT;
import static com.example.banksampah.Config.TAG_SALDO;
import static com.example.banksampah.Config.TAG_TELEPON;
import static com.example.banksampah.Config.PASS_KEY;

public class Login extends AppCompatActivity {
    Button btnlogin, btnregister;
    EditText editTextusername, editTextpassword;
    ProgressBar pDialog;
    private static final String TAG = Login.class.getSimpleName();
    private static String url = Config.URL_LOGIN;
    SessionManager manager;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        manager = new SessionManager(this);

        editTextusername = findViewById(R.id.usernameedt);
        editTextpassword = findViewById(R.id.passwordedt);
        btnlogin = findViewById(R.id.btnlogin);
        btnregister = findViewById(R.id.btnregister);
        pDialog = findViewById(R.id.loading);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nik = editTextusername.getText().toString().trim();
                String password = editTextpassword.getText().toString().trim();

                // mengecek kolom yang kosong
                if (!nik.isEmpty() || !password.isEmpty()) {
                    Login(nik, password);
                } else {
                    editTextusername.setError("NIK tidak boleh kosong");
                    editTextpassword.setError("Password tidak boleh kosong");
                }
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                finish();
                startActivity(intent);
            }
        });
    }

    private void Login(final String nik, final String password) {
        pDialog.setVisibility(View.VISIBLE);
        btnlogin.setVisibility(View.GONE);
        btnregister.setVisibility(View.GONE);

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    String success = jObj.getString("success");
                    JSONArray array =jObj.getJSONArray("login");

                    // Check for error node in json
                    if (success.equals("1")) {
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);

                            String id = obj.getString(TAG_ID_USER);
                            String nik = obj.getString(TAG_NIK);
                            String password = obj.getString(PASS_KEY);
                            String nama = obj.getString(TAG_NAMA);
                            String saldo = obj.getString(TAG_SALDO);
                            String rt = obj.getString(TAG_RT);
                            String email = obj.getString(TAG_EMAIL);
                            String alamat = obj.getString(TAG_ALAMAT);
                            String telepon = obj.getString(TAG_TELEPON);
                            String role = obj.getString(TAG_ROLE);

                            if (role.equals("Admin")){
                                Intent intentd = new Intent(Login.this, MainActivity.class);
                                intentd.putExtra(TAG_ID_USER, id);
                                intentd.putExtra(TAG_NIK, nik);
                                intentd.putExtra(TAG_ROLE, role);
                                intentd.putExtra(TAG_NAMA, nama);
                                intentd.putExtra(TAG_TELEPON, telepon);
                                intentd.putExtra(TAG_EMAIL, email);
                                intentd.putExtra(TAG_ALAMAT, alamat);
                                intentd.putExtra(TAG_RT, rt);
                                intentd.putExtra(TAG_SALDO, saldo);
                                startActivity(intentd);
                                manager.createSession(id, nik, password, nama, alamat, rt, telepon, email, saldo, role);
                                finish();
                                Log.e("berhasil login = admin: ", jObj.toString());
                            } else if(role.equals("Nasabah")) {
                                Intent intentd = new Intent(Login.this, UserMainActivity.class);
                                intentd.putExtra(TAG_ID_USER, id);
                                intentd.putExtra(TAG_NIK, nik);
                                intentd.putExtra(PASS_KEY, password);
                                intentd.putExtra(TAG_NAMA, nama);
                                intentd.putExtra(TAG_ALAMAT, alamat);
                                intentd.putExtra(TAG_RT, rt);
                                intentd.putExtra(TAG_TELEPON, telepon);
                                intentd.putExtra(TAG_EMAIL, email);
                                intentd.putExtra(TAG_SALDO, saldo);
                                intentd.putExtra(TAG_ROLE, role);
                                startActivity(intentd);
                                manager.createSession(id, nik, password, nama, alamat, rt, telepon, email, saldo, role);
                                finish();
                                Log.e("berhasil login = nasabah: ", jObj.toString());
                            }

                            pDialog.setVisibility(View.GONE);
                        }
                    }
                } catch (JSONException e) {
                    // JSON error
                    pDialog.setVisibility(View.GONE);
                    btnlogin.setVisibility(View.VISIBLE);
                    btnregister.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.setVisibility(View.GONE);
                btnlogin.setVisibility(View.VISIBLE);
                btnregister.setVisibility(View.VISIBLE);
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("nik", nik);
                params.put("password", password);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(strReq);
    }
}
