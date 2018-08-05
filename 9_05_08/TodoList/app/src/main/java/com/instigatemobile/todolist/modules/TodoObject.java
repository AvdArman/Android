package com.instigatemobile.todolist.modules;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class TodoObject implements Serializable {
    private String mImage;
    private String mTitle;
    private String mDescription;
    private String mDateTime;

    public TodoObject() {
    }

    public TodoObject(String mImage, String mTitle, String mDescription, String mDateTime) {
        this.mImage = mImage;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDateTime = mDateTime;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmDateTime() {
        return mDateTime;
    }

}
