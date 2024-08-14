package com.example.food_deliver_mobileapp;

public class Review {

    private final int reviewId;
    private final int itemId;
    private final int userId;
    private final String reviewMessage;
    private final String username; // Add this line

    // Updated constructor to include username
    public Review(int reviewId, int itemId, int userId, String reviewMessage, String username) {
        this.reviewId = reviewId;
        this.itemId = itemId;
        this.userId = userId;
        this.reviewMessage = reviewMessage;
        this.username = username; // Initialize the username
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getUserId() {
        return userId;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public String getUsername() { // Add this getter method
        return username;
    }
}
