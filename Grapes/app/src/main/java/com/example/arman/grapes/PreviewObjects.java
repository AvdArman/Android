package com.example.arman.grapes;

public class PreviewObjects {
    private int img;
    private String text;
    private String logoText;

    public PreviewObjects(int img, String text, String logoText) {
        this.img = img;
        this.text = text;
        this.logoText = logoText;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLogoText() {
        return logoText;
    }

    public void setLogoText(String logoText) {
        this.logoText = logoText;
    }
}
