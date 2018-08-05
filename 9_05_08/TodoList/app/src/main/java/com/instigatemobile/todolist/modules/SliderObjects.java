package com.instigatemobile.todolist.modules;

public class SliderObjects {
    private int img;
    private String text;

    public SliderObjects(int img, String text) {
        this.img = img;
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
