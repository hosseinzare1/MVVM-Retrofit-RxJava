package com.example.mvvm_retrofit.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    MutableLiveData<List<ItemModel>> itemsList = new MutableLiveData<>();

    public LiveData<List<User>> getUsersFromObservable(){

        return LiveDataReactiveStreams.fromPublisher(RetrofitInstance.getGsonHolderInstance().getUsersFlowable()
                .subscribeOn(Schedulers.io()));

    }

    public MutableLiveData<List<ItemModel>> getItems() {

        RetrofitInstance.getGsonHolderInstance().getItems().enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response) {
                if (response.isSuccessful()) {

                    itemsList.setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<List<ItemModel>> call, Throwable t) {

            }
        });


        return itemsList;
    }

    public Future<Observable<List<User>>> getUsers() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<Observable<List<User>>> callable = new Callable<Observable<List<User>>>() {
            @Override
            public Observable<List<User>> call() throws Exception {
                return RetrofitInstance.getGsonHolderInstance().getUsers();
            }
        };


        Future<Observable<List<User>>> future = new Future<Observable<List<User>>>() {
            @Override
            public boolean cancel(boolean b) {
                if (b) {
                    executorService.shutdown();
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return executorService.isShutdown();
            }

            @Override
            public boolean isDone() {
                return executorService.isTerminated();
            }

            @Override
            public Observable<List<User>> get() throws ExecutionException, InterruptedException {
                return executorService.submit(callable).get();
            }

            @Override
            public Observable<List<User>> get(long l, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
                return executorService.submit(callable).get(l, timeUnit);
            }
        };

        return future;

    }

}
