package com.example.student.film;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public class Movie {
    private String title;
    private String description;
    private Drawable image;
    private Float rating;
    private boolean like;
    private String url;



    public Movie(String title, String description, Drawable image, Float rating) {
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}