package com.instigatemobile.grapes.models;

import java.util.ArrayList;
import java.util.List;

public class FileModel {

    private String mSize;
    private String mIcon;
    private String mName;
    private String mPath;
    private String mDate;
    private String mExtension;
    private List<String> mMacAddresses;

    public FileModel(String size, String icon, String name, String path,
                     String date, String extension) {
        mSize = size;
        mIcon = icon;
        mName = name;
        mPath = path;
        mDate = date;
        mExtension = extension;
        mMacAddresses = new ArrayList<>();
    }

    public String getSize() {
        return mSize;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        this.mIcon = icon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getDate() {
        return mDate;
    }

    public String getExtension() {
        return mExtension;
    }

    public List<String> getMacAddresses() {
        return mMacAddresses;
    }

    public void setMacAddresses(List<String> macAddresses) {
        mMacAddresses = macAddresses;
    }

    public void addMacAddress(String mac) {
        mMacAddresses.add(mac);
    }
}
