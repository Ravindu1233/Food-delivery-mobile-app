package com.example.foodappmadcw04;

public class Shop {
    private final String shopName;
    private final String shopLocation;
    private final String shopRating;
    private final byte[] shopImage; // Use byte[] for image

    // Constructor
    public Shop(String shopName, String shopLocation, byte[] shopImage, String shopRating) {
        this.shopName = shopName;
        this.shopLocation = shopLocation;
        this.shopImage = shopImage;
        this.shopRating = shopRating;
    }

    // Getters
    public String getShopName() {
        return shopName;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public byte[] getShopImage() {
        return shopImage;
    }

    public String getShopRating() {
        return shopRating;
    }
}
