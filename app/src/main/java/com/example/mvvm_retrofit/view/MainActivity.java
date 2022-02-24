package com.example.mvvm_retrofit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mvvm_retrofit.R;
import com.example.mvvm_retrofit.databinding.ActivityMainBinding;
import com.example.mvvm_retrofit.model.ItemModel;
import com.example.mvvm_retrofit.model.User;
import com.example.mvvm_retrofit.viewmodel.MainActivityViewModel;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    public static final String MAIN_ACTIVITY_TAG = "MainActivity";
    MainActivityViewModel mainActivityViewModel;
    ActivityMainBinding binding;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        initializeRecyclerView();


        Observable.range(0, 25).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())

                .buffer(2)
                .map(new Function<List<Integer>, List<String>>() {
                    @Override
                    public List<String> apply(List<Integer> integers) throws Throwable {
                        List<String> list = new ArrayList<String>();


                        for (Integer i:integers
                             ) {
                            list.add(String.valueOf(i));


                        }
                        return list;
                    }
                })


                .subscribe(new io.reactivex.rxjava3.core.Observer<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<String> strings) {
                        Log.i("Main", "----------------");

                        for (String s : strings
                        ) {
                            Log.i("Main", s);
                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
//                for (int i = 0; i < 1000000; i++) {
//
//                    emitter.onNext(String.valueOf(i));
//
//                }
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new io.reactivex.rxjava3.core.Observer<String>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull String s) {
//                        Log.i("Main", s);
//
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


//        mainActivityViewModel.getFromObservable().observe(this, new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//                for (User user : users
//                ) {
//                    Log.i("Main", user.getName());
//                }
//            }
//        });
//

//        try {
//            mainActivityViewModel.getUsers().get().subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new io.reactivex.rxjava3.core.Observer<List<User>>() {
//                        @Override
//                        public void onSubscribe(@NonNull Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(@NonNull List<User> users) {
//                            for (User user:users
//                                 ) {
//                                Log.i("Main",user.getName());
//                            }
//                        }
//
//                        @Override
//                        public void onError(@NonNull Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//

    }

    public void initializeRecyclerView() {

        recyclerView = binding.recyclerView;
        RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        mainActivityViewModel.getItems().observe(this, new Observer<List<ItemModel>>() {
            @Override
            public void onChanged(List<ItemModel> itemModels) {
                adapter.setItems(itemModels.subList(0, 10));
//                for (ItemModel i : itemModels.subList(0, 9)
//                ) {
//
//                    Log.i(MAIN_ACTIVITY_TAG, i.getUrl());
//
//                }
            }
        });
    }


}