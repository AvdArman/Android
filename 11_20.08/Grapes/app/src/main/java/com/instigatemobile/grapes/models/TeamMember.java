package com.instigatemobile.grapes.models;

public class TeamMember {
    private int mImage;
    private String mName;

    public TeamMember(int image, String name) {
        this.mImage = image;
        this.mName = name;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
