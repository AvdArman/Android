package com.instigatemobile.userlistlocation.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.instigatemobile.userlistlocation.models.User;

import java.util.List;

public class ApiResponse {

    @SerializedName("results")
    @Expose
    private List<User> users = null;

    public List<User> getResults() {
        return users;
    }

    public void setResults(List<User> results) {
        this.users = results;
    }
}