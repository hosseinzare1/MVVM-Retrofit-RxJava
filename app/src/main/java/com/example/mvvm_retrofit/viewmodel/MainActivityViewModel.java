package com.example.mvvm_retrofit.viewmodel;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.mvvm_retrofit.model.ItemModel;
import com.example.mvvm_retrofit.model.Repository;
import com.example.mvvm_retrofit.model.User;

import java.util.List;
import java.util.concurrent.Future;

import io.reactivex.rxjava3.core.Observable;

public class MainActivityViewModel extends ViewModel {

    MutableLiveData<List<ItemModel>> itemsList = new MutableLiveData<>();
    Repository repository = new Repository();

    public MutableLiveData<List<ItemModel>> getItems() {

        itemsList = repository.getItems();
        return itemsList;


    }

    public LiveData<List<User>> getFromObservable(){


        return repository.getUsersFromObservable();
    }


    public Future<Observable<List<User>>> getUsers(){

        return repository.getUsers();

    }


//    @BindingAdapter("imgURL")
//    public static void loadImage(ImageView imageView, String imgURL) {
//        Toast.makeText(imageView.getContext(),imgURL,Toast.LENGTH_LONG).show();
//        Glide.with(imageView.getContext()).load(imgURL).into(imageView);
//
//    }

}
