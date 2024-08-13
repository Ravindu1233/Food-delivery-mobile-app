package com.example.food_deliver_mobileapp;

public class Item {
    private final int item_id;
    private final String item_name;
    private final String item_description;
    private final float item_price;
    private final byte[] item_image;
    private final String item_category;

    public Item(int id,String name, String description, float price, byte[] image, String category) {
        this.item_id = id;
        this.item_name = name;
        this.item_description = description;
        this.item_price = price;
        this.item_image = image;
        this.item_category = category;
    }
    public int getId() {
        return item_id;
    }

    public String getName() {
        return item_name;
    }

    public String getDescription() {
        return item_description;
    }

    public float getPrice() {
        return item_price;
    }

    public byte[] getImage() {
        return item_image;
    }

    public String getCategory() {
        return item_category;
    }
}
