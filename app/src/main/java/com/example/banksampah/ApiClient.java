package com.example.banksampah;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiClient {
    private static final String BASE_URL = "http://192.168.1.6/Bank-Sampah/backend/user/";
    public static final String URL_DELETE_USER = "http://192.168.1.6/Bank-Sampah/backend/user/hapususer.php?id=";
    private static Retrofit retrofit;

    static Retrofit getApiClient(){

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
