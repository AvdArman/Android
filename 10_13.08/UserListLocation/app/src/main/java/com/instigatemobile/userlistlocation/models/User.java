package com.instigatemobile.userlistlocation.models;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.List;

import javax.xml.transform.Result;

public class User {

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("name")
    @Expose
    private Name name;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("cell")
    @Expose
    private String cell;
    @SerializedName("picture")
    @Expose
    private Picture picture;
    private List<User> user;


    public String getGender() {
        return gender;
    }

    public String getName() {
        return name.title + " " + name.first + " " + name.last;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getCell() {
        return cell;
    }

    public String getPicture() {
        return picture.getMedium();
    }


    public class Coordinates {

        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }

    public class Location {

        @SerializedName("coordinates")
        @Expose
        private Coordinates coordinates;
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;

        public String getAddress() {
            return state + " " + city + " " + street;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }
    }

    public class Name {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("first")
        @Expose
        private String first;
        @SerializedName("last")
        @Expose
        private String last;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class Picture {

        @SerializedName("medium")
        @Expose
        private String medium;

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

    }

}