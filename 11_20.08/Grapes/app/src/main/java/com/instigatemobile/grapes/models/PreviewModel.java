package com.instigatemobile.grapes.models;

public class PreviewModel {
    private int mImage;
    private String mText;
    private String mLogoText;

    public PreviewModel(int img, String text, String logoText) {
        mImage = img;
        mText = text;
        mLogoText = logoText;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        this.mImage = image;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getLogoText() {
        return mLogoText;
    }
}
