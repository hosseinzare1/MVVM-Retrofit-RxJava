package com.example.mvvm_retrofit.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvvm_retrofit.R;
import com.example.mvvm_retrofit.databinding.ItemBinding;
import com.example.mvvm_retrofit.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    List<ItemModel> itemModelList = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setItem(itemModelList.get(position));

        Glide.with(holder.imageView.getContext()).load(itemModelList.get(position).getUrl()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return itemModelList.size();
    }

    public void setItems(List<ItemModel> items) {

        this.itemModelList = items;
        notifyDataSetChanged();
//        items.add(new ItemModel("10001","uri","title","uri2"));
    }

    public void addItem(ItemModel item) {
        this.itemModelList.add(item);
        notifyDataSetChanged();
//        items.add(new ItemModel("10001","uri","title","uri2"));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemBinding binding;
        ImageView imageView;

        public ViewHolder(@NonNull ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            imageView = binding.imageView;
        }
    }
}
