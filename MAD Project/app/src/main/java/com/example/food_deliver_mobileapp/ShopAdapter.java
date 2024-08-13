package com.example.food_deliver_mobileapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private final Context context;
    private final List<com.example.foodappmadcw04.Shop> shopList;

    public ShopAdapter(Context context, List<com.example.foodappmadcw04.Shop> shopList) {
        this.context = context;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        com.example.foodappmadcw04.Shop shop = shopList.get(position);
        holder.shopNameTextView.setText(shop.getShopName());
        holder.shopLocationTextView.setText(shop.getShopLocation());
        holder.shopRatingTextView.setText(shop.getShopRating()); // Display the rating

        byte[] imageBytes = shop.getShopImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.shopImageView.setImageBitmap(bitmap); // Set image from byte array
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView shopNameTextView;
        TextView shopLocationTextView;
        TextView shopRatingTextView;
        ImageView shopImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameTextView = itemView.findViewById(R.id.shop_name);
            shopLocationTextView = itemView.findViewById(R.id.shop_location);
            shopRatingTextView = itemView.findViewById(R.id.shop_rating);
            shopImageView = itemView.findViewById(R.id.shop_image);
        }
    }
}
