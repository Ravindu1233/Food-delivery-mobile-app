package com.example.food_deliver_mobileapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {

    private ArrayList<Order> orderList;

    public UserOrderAdapter(ArrayList<Order> orders) {
        this.orderList = orders;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_order_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderIdTextView.setText("Order ID :"+order.getOrderId());
        holder.orderDateTextView.setText(order.getOrderDate());
        holder.orderTotalAmountTextView.setText(String.format("$%.2f", order.getOrderTotalAmount()));
        holder.orderStateTextView.setText(order.getOrderState());

        if(Objects.equals(order.getOrderState(), "Pending")){
            holder.giveFeedbackButton.setEnabled(false);
        }else {
            holder.giveFeedbackButton.setEnabled(true);
            holder.giveFeedbackButton.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), FeedbackActivity.class);
                intent.putExtra("order_id", order.getOrderId());
                intent.putExtra("shop_id", order.getShopId());
                intent.putExtra("user_id", order.getUserId());
                intent.putExtra("item_id", order.getItemId());
                v.getContext().startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderIdTextView;
        public TextView orderDateTextView;
        public TextView orderTotalAmountTextView;
        public TextView orderStateTextView;
        public Button giveFeedbackButton;

        public ViewHolder(View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id);
            orderDateTextView = itemView.findViewById(R.id.order_date);
            orderTotalAmountTextView = itemView.findViewById(R.id.order_total_amount);
            orderStateTextView = itemView.findViewById(R.id.order_state);
            giveFeedbackButton = itemView.findViewById(R.id.give_feedback_button);
        }
    }
}


