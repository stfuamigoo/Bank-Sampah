package com.example.banksampah;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "login";
    private static final String LOGIN = "is_login";
    public static final String ID = "id";
    public static final String NIK = "nik";
    public static final String PASSWORD = "password";
    public static final String NAMA = "nama";
    public static final String ALAMAT = "alamat";
    public static final String RT = "rt";
    public static final String TELEPON = "telepon";
    public static final String EMAIL = "email";
    public static final String SALDO = "saldo";
    public static final String ROLE = "role";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id, String nik, String password, String nama, String alamat, String rt, String telepon, String email, String saldo, String role){
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.putString(NIK, nik);
        editor.putString(PASSWORD, password);
        editor.putString(NAMA, nama);
        editor.putString(ALAMAT, alamat);
        editor.putString(RT, rt);
        editor.putString(TELEPON, telepon);
        editor.putString(EMAIL, email);
        editor.putString(SALDO, saldo);
        editor.putString(ROLE, role);
        editor.apply();
    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkloggin(){
        if (!this.isLoggin()){
            if (ROLE.equals("Admin")){
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            } else {
                Intent intent = new Intent(context, UserMainActivity.class);
                context.startActivity(intent);
            }
        }
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(NIK, sharedPreferences.getString(NIK, null));
        user.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        user.put(NAMA, sharedPreferences.getString(NAMA, null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT, null));
        user.put(RT, sharedPreferences.getString(RT, null));
        user.put(TELEPON, sharedPreferences.getString(TELEPON, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(SALDO, sharedPreferences.getString(SALDO, null));
        user.put(ROLE, sharedPreferences.getString(ROLE, null));
        user.put(ID, sharedPreferences.getString(ID, null));

        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, Login.class);
        context.startActivity(i);
    }
}
