package com.example.student.film;

import android.graphics.drawable.Drawable;

public class Movie {
    private String title;
    private String description;
    private Drawable image;
    private Integer rating;
    private boolean like;



    public Movie(String title, String description, Drawable image, Integer rating) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.like = like;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public Movie() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

}