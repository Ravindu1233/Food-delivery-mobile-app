package com.example.food_deliver_mobileapp;

public class Order {
    private int orderId;
    private int shopId;
    private int userId;
    private int itemId;
    private String orderDate;
    private String orderState;
    private int orderQuantity;
    private double orderTotalAmount;

    public Order(int orderId, int shopId, int userId, int itemId, String orderDate, String orderState, int orderQuantity, double orderTotalAmount) {
        this.orderId = orderId;
        this.shopId = shopId;
        this.userId = userId;
        this.itemId = itemId;
        this.orderDate = orderDate;
        this.orderState = orderState;
        this.orderQuantity = orderQuantity;
        this.orderTotalAmount = orderTotalAmount;
    }

    // Getters
    public int getOrderId() { return orderId; }
    public int getShopId() { return shopId; }
    public int getUserId() { return userId; }
    public int getItemId() { return itemId; }
    public String getOrderDate() { return orderDate; }
    public String getOrderState() { return orderState; }
    public int getOrderQuantity() { return orderQuantity; }
    public double getOrderTotalAmount() { return orderTotalAmount;}

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
