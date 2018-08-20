package com.codekidlabs.storagechooser.models;

/**
 * A model that is used as the element to store any data regarding storage volumes/disks
 */
public class Storages {

    String mStorageTitle;
    String mStoragePath;
    String mMemoryTotalSize;
    String mMemoryAvailableSize;


    public String getmStorageTitle() {
        return mStorageTitle;
    }

    public void setmStorageTitle(String mStorageTitle) {
        this.mStorageTitle = mStorageTitle;
    }

    public String getmStoragePath() {
        return mStoragePath;
    }

    public void setmStoragePath(String mStoragePath) {
        this.mStoragePath = mStoragePath;
    }

    public String getmMemoryTotalSize() {
        return mMemoryTotalSize;
    }

    public void setmMemoryTotalSize(String mMemoryTotalSize) {
        this.mMemoryTotalSize = mMemoryTotalSize;
    }

    public String getmMemoryAvailableSize() {
        return mMemoryAvailableSize;
    }

    public void setmMemoryAvailableSize(String mMemoryAvailableSize) {
        this.mMemoryAvailableSize = mMemoryAvailableSize;
    }
}
