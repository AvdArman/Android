package com.codekidlabs.storagechooser;

/**
 * Content class handles changes to mDialog's view and whatever is in it
 */

public class Content {

    private String mSelectLabel = "Select";
    private String mCreateLabel = "Create";
    private String mNewFolderLabel = "New Folder";
    private String mCancelLabel = "Cancel";
    private String mOverviewHeading = "Choose Storage";
    private String mInternalStorageText = "Internal Storage";
    private String mFreeSpaceText = "%s free";
    private String mFolderCreatedToastText = "Folder Created";
    private String mFolderErrorToastText = "Error occured while creating folder. Try again.";
    private String mTextfieldHintText = "Folder Name";
    private String mTextfieldErrorText = "Empty Folder Name";


    public String getmSelectLabel() {
        return mSelectLabel;
    }

    public void setmSelectLabel(String mSelectLabel) {
        this.mSelectLabel = mSelectLabel;
    }

    public String getmCreateLabel() {
        return mCreateLabel;
    }

    public void setmCreateLabel(String mCreateLabel) {
        this.mCreateLabel = mCreateLabel;
    }

    public String getmNewFolderLabel() {
        return mNewFolderLabel;
    }

    public void setmNewFolderLabel(String mNewFolderLabel) {
        this.mNewFolderLabel = mNewFolderLabel;
    }

    public String getmCancelLabel() {
        return mCancelLabel;
    }

    public void setmCancelLabel(String mCancelLabel) {
        this.mCancelLabel = mCancelLabel;
    }

    public String getmOverviewHeading() {
        return mOverviewHeading;
    }

    public void setmOverviewHeading(String mOverviewHeading) {
        this.mOverviewHeading = mOverviewHeading;
    }

    public String getmInternalStorageText() {
        return mInternalStorageText;
    }

    public void setmInternalStorageText(String mInternalStorageText) {
        this.mInternalStorageText = mInternalStorageText;
    }

    public String getmFreeSpaceText() {
        return mFreeSpaceText;
    }

    public void setmFreeSpaceText(String mFreeSpaceText) {
        this.mFreeSpaceText = mFreeSpaceText;
    }

    public String getmFolderCreatedToastText() {
        return mFolderCreatedToastText;
    }

    public void setmFolderCreatedToastText(String mFolderCreatedToastText) {
        this.mFolderCreatedToastText = mFolderCreatedToastText;
    }

    public String getmFolderErrorToastText() {
        return mFolderErrorToastText;
    }

    public void setmFolderErrorToastText(String mFolderErrorToastText) {
        this.mFolderErrorToastText = mFolderErrorToastText;
    }

    public String getmTextfieldHintText() {
        return mTextfieldHintText;
    }

    public void setmTextfieldHintText(String mTextfieldHintText) {
        this.mTextfieldHintText = mTextfieldHintText;
    }

    public String getmTextfieldErrorText() {
        return mTextfieldErrorText;
    }

    public void setmTextfieldErrorText(String mTextfieldErrorText) {
        this.mTextfieldErrorText = mTextfieldErrorText;
    }
}
