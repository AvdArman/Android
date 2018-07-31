package com.example.arman.userlist;

import java.util.List;

public class User {
    private String fullName;
    private String phoneNumber;
    private String mailAddress;
    private String description;
    private List<String> imgUrlList;

    public User(String fullName, String phoneNumber,
                String mailAddress, String description, List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.mailAddress = mailAddress;
        this.description = description;
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

    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public String getFullName() {
        return fullName;
    }
}
