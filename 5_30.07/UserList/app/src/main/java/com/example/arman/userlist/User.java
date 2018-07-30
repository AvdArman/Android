package com.example.arman.userlist;

public class User {
    private String imageUrl;
    private String fullName;
    private String phoneNumber;
    private String mailAddress;
    private String description;
    private float rating;

    public User(String fullName, String phoneNumber,
                String mailAddress, String description, float rating, String imageUrl) {
        this.imageUrl = imageUrl;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.mailAddress = mailAddress;
        this.description = description;
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
