package com.example.mvvm_retrofit.model;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GsonHolderService {

    @GET("photos")
    Call<List<ItemModel>> getItems();

    @GET("users")
    Observable<List<User>> getUsers();

    @GET("users")
    Flowable<List<User>> getUsersFlowable();

}
