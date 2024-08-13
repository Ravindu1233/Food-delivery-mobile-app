package com.example.food_deliver_mobileapp;

public class ShopModal {

    private String name;
    private String address;
    private String city;
    private String contact;
    private String email;
    private String open;
    private String close;
    private byte[] image;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public ShopModal(int id, String name, String address, String city, String contact, String email, String open, String close, byte[] image) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.contact = contact;
        this.email = email;
        this.open = open;
        this.close = close;
        this.image = image;
    }
}
