package com.instigatemobile.grapes.models;

public class DataTransfer {
    private final boolean isDownload;
    private final String mName;
    private String mSpeed;
    private int mProgress;

    public DataTransfer(boolean isDownload, String name, String speed, int progress) {
        this.isDownload = isDownload;
        this.mName = name;
        this.mSpeed = speed;
        this.mProgress = progress;
    }

    public void setSpeed(String speed) {
        this.mSpeed = speed;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public String getName() {
        return mName;
    }

    public String getSpeed() {
        return mSpeed;
    }

    public int getProgress() {
        return mProgress;
    }

}