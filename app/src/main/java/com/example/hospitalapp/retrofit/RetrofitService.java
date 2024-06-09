package com.example.hospitalapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitService {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http:/192.168.1.9:8080/api/";

    public RetrofitService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient) // Set the custom OkHttpClient here
                .build();
    }

    public static Retrofit getRetrofitInstance() {
        return retrofit;
    }

    public DepartmentApi getDepartmentApi() {
        return getRetrofitInstance().create(DepartmentApi.class);
    }

    public PatientsApi getPatientsApi() {
        return getRetrofitInstance().create(PatientsApi.class);
    }

    public ClinicalDataApi getClinicalDataApi() {
        return getRetrofitInstance().create(ClinicalDataApi.class);
    }
}
