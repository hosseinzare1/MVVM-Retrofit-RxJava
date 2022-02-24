package com.example.mvvm_retrofit.model;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    public static Retrofit retrofit = null;

    public static GsonHolderService getGsonHolderInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit.create(GsonHolderService.class);
    }


}
