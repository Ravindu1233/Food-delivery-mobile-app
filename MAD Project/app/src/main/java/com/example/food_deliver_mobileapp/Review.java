package com.example.food_deliver_mobileapp;

public class Review {

    private final int reviewId;
    private final int itemId;
    private final int userId;
    private final String reviewMessage;



    public Review(int reviewId, int itemId, int userId, String reviewMessage) {
        this.reviewId = reviewId;
        this.itemId = itemId;
        this.userId = userId;
        this.reviewMessage = reviewMessage;
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
}