package com.example.food_deliver_mobileapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private ArrayList<Order> orderList;
    private DBHandler dbHandler;

    public OrderAdapter(ArrayList<Order> orderList, DBHandler dbHandler) {
        this.orderList = orderList;
        this.dbHandler = dbHandler;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_layout, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        String userEmail = dbHandler.getUserEmailById(order.getUserId());
        holder.orderIdTextView.setText("Order ID: " + order.getOrderId());
        holder.userEmailTextView.setText("User Email: " + userEmail);
        holder.orderDateTextView.setText("Date: " + order.getOrderDate());
        holder.orderTotalTextView.setText("Total Amount: " + order.getOrderTotalAmount());
        holder.orderStateTextView.setText("Status: " + order.getOrderState());

        // Check the order state and disable the button if the order is completed
        if ("Completed".equals(order.getOrderState())) {
            holder.completeOrderButton.setEnabled(false);
            holder.completeOrderButton.setText("Order Completed");
        } else {
            holder.completeOrderButton.setEnabled(true);
            holder.completeOrderButton.setText("Complete Order");

            holder.completeOrderButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update the order state in the database
                    dbHandler.updateOrderState(order.getOrderId(), "Completed");

                    // Update the order object with the new state
                    order.setOrderState("Completed");

                    // Notify the adapter to refresh the UI
                    notifyItemChanged(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView orderIdTextView;
        public TextView userEmailTextView;
        public TextView orderDateTextView;
        public TextView orderTotalTextView;
        public TextView orderStateTextView;
        public Button completeOrderButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderIdTextView = itemView.findViewById(R.id.order_id_text_view);
            userEmailTextView = itemView.findViewById(R.id.user_email_text_view);
            orderDateTextView = itemView.findViewById(R.id.order_date_text_view);
            orderTotalTextView = itemView.findViewById(R.id.order_total_text_view);
            orderStateTextView = itemView.findViewById(R.id.order_state_text_view);
            completeOrderButton = itemView.findViewById(R.id.btn_complete_order);
        }
    }
}
