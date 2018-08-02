package com.instigatemobile.getdatafromstorage;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class PostObject implements Serializable {
    public String title;
    public String description;
    public String storageUrl;
    public DatabaseReference delReference;

    public PostObject(String title, String description, String storageUrl) {
        this.title = title;
        this.storageUrl = storageUrl;
        this.description = description;
    }

    public PostObject() {}
}
