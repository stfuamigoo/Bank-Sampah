package com.example.banksampah;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    ProgressDialog pDialog;
    private String url = Config.URL_REGISTER;
    EditText editTextusername, editTextpassword, editTextnama, editTextalamat, editTextrt, editTexttelepon, editTextemail, editTextsaldo, editTextrole;
    Button btnbuatakun;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextusername = findViewById(R.id.eusername);
        editTextpassword = findViewById(R.id.eusername);
        editTextnama = findViewById(R.id.enama);
        editTextalamat = findViewById(R.id.ealamat);
        editTextrt = findViewById(R.id.ert);
        editTexttelepon = findViewById(R.id.etelepon);
        editTextemail = findViewById(R.id.eemail);
        editTextsaldo = findViewById(R.id.esaldo);
        editTextrole = findViewById(R.id.erole);
        editTextrole.setText("Nasabah", TextView.BufferType.EDITABLE);

        btnbuatakun = findViewById(R.id.btnbuatakun);
        btnbuatakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nik = editTextusername.getText().toString();
                String password = editTextpassword.getText().toString();
                String nama = editTextnama.getText().toString();
                String alamat = editTextalamat.getText().toString();
                String rt = editTextrt.getText().toString();
                String telepon = editTexttelepon.getText().toString();
                String email = editTextemail.getText().toString();
                String saldo = editTextsaldo.getText().toString();
                String role = editTextrole.getText().toString();

                Registeration(nik, password, nama, alamat, rt, telepon, email, saldo, role);
                Toast.makeText(getApplicationContext(),
                        "Registrasi Berhasil", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

    }
    public void Registeration(final String nik, final String password,final String nama, final String alamat, final String rt, final String telepon, final String email, final String saldo, final String role){
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Register ...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        String success = jObj.getString("success");

                        // Check for error node in json
                        if (success.equals("1")) {
                            Log.e("Registrasi Berhasil!", jObj.toString());

                            editTextusername.setText("");
                            editTextpassword.setText("");
                            editTextnama.setText("");
                            editTextalamat.setText("");
                            editTextrt.setText("");
                            editTexttelepon.setText("");
                            editTextemail.setText("");
                            editTextsaldo.setText("");
                            editTextrole.setText("");

                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("nik", nik);
                    params.put("password", password);
                    params.put("nama", nama);
                    params.put("alamat", alamat);
                    params.put("rt", rt);
                    params.put("telepon", telepon);
                    params.put("email", email);
                    params.put("saldo", saldo);
                    params.put("role", role);
                    return params;
                }
            };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Register.this, Login.class);
        finish();
        startActivity(intent);
    }
}
