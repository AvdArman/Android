package com.codekidlabs.storagechooser.models;

import android.content.SharedPreferences;

import com.codekidlabs.storagechooser.Content;
import com.codekidlabs.storagechooser.StorageChooser;

import java.util.List;

/**
 * Model to save configs passed to the Builder without passing too many things to the constructor
 * of its super class.
 */
public class Config {

    private android.app.FragmentManager mFragmentManager;
    private String mPredefinedPath;
    private boolean mShowMemoryBar;
    private float mMemorybarHeight;
    private boolean mActionSave;
    private SharedPreferences mPreference;
    private int mMemoryThreshold;
    private String mThresholdSuffix;
    private String mDialogTitle;
    private String mInternalStorageText;
    private boolean mAllowCustomPath;
    private boolean mAllowAddFolder;
    private boolean mShowHidden;
    private boolean mSkipOverview;
    private boolean mApplyThreshold;
    private String mPrimaryPath;
    private boolean mIsGridView;
    private boolean mResumeSession;
    //typeface config
    private String mHeadingFont;
    private String mListFont;
    private boolean mHeadingFromAssets;
    private boolean mListFromAssets;

    //multi
    private boolean mMultiSelect = true;

    private String mSecondaryAction;

    private Content mContent;
    private int[] mScheme;
    private StorageChooser.FileType mSingleFilter;
    private List<String> mCustomEnum;
    private boolean mCustomFilter;


    public android.app.FragmentManager getmFragmentManager() {
        return mFragmentManager;
    }

    public void setmFragmentManager(android.app.FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

    public String getmPredefinedPath() {
        return mPredefinedPath;
    }

    public void setmPredefinedPath(String mPredefinedPath) {
        this.mPredefinedPath = mPredefinedPath;
    }

    public boolean ismShowMemoryBar() {
        return mShowMemoryBar;
    }

    public void setmShowMemoryBar(boolean mShowMemoryBar) {
        this.mShowMemoryBar = mShowMemoryBar;
    }

    public boolean ismActionSave() {
        return mActionSave;
    }

    public void setmActionSave(boolean mActionSave) {
        this.mActionSave = mActionSave;
    }

    public SharedPreferences getmPreference() {
        return mPreference;
    }

    public void setmPreference(SharedPreferences mPreference) {
        this.mPreference = mPreference;
    }

    public int getmMemoryThreshold() {
        return mMemoryThreshold;
    }

    public void setmMemoryThreshold(int mMemoryThreshold) {
        this.mMemoryThreshold = mMemoryThreshold;
    }

    public String getmThresholdSuffix() {
        return mThresholdSuffix;
    }

    public void setmThresholdSuffix(String mThresholdSuffix) {
        this.mThresholdSuffix = mThresholdSuffix;
    }

    public String getmDialogTitle() {
        return mDialogTitle;
    }

    public void setmDialogTitle(String mDialogTitle) {
        this.mDialogTitle = mDialogTitle;
    }

    public String getmInternalStorageText() {
        return mInternalStorageText;
    }

    public void setmInternalStorageText(String mInternalStorageText) {
        this.mInternalStorageText = mInternalStorageText;
    }

    public boolean ismAllowCustomPath() {
        return mAllowCustomPath;
    }

    public void setmAllowCustomPath(boolean mAllowCustomPath) {
        this.mAllowCustomPath = mAllowCustomPath;
    }

    public boolean ismAllowAddFolder() {
        return mAllowAddFolder;
    }

    public void setmAllowAddFolder(boolean mAllowAddFolder) {
        this.mAllowAddFolder = mAllowAddFolder;
    }

    public boolean ismShowHidden() {
        return mShowHidden;
    }

    public void setmShowHidden(boolean mShowHidden) {
        this.mShowHidden = mShowHidden;
    }


    public String getmSecondaryAction() {
        return mSecondaryAction;
    }

    public void setmSecondaryAction(String mSecondaryAction) {
        this.mSecondaryAction = mSecondaryAction;
    }


    public String getmPrimaryPath() {
        return mPrimaryPath;
    }

    public void setmPrimaryPath(String mPrimaryPath) {
        this.mPrimaryPath = mPrimaryPath;
    }

    public boolean ismSkipOverview() {
        return mSkipOverview;
    }

    public void setmSkipOverview(boolean mSkipOverview) {
        this.mSkipOverview = mSkipOverview;
    }

    public boolean ismApplyThreshold() {
        return mApplyThreshold;
    }

    public void setmApplyThreshold(boolean mApplyThreshold) {
        this.mApplyThreshold = mApplyThreshold;
    }


    public Content getmContent() {
        return mContent;
    }

    public void setmContent(Content mContent) {
        this.mContent = mContent;
    }


    public StorageChooser.FileType getmSingleFilter() {
        return mSingleFilter;
    }

    public void setmSingleFilter(StorageChooser.FileType mSingleFilter) {
        this.mSingleFilter = mSingleFilter;
    }

    public List<String> getmCustomEnum() {
        return mCustomEnum;
    }

    public void setmCustomEnum(List<String> mCustomEnum) {
        this.mCustomEnum = mCustomEnum;
    }

    public boolean ismCustomFilter() {
        return mCustomFilter;
    }

    public void setmCustomFilter(boolean mCustomFilter) {
        this.mCustomFilter = mCustomFilter;
    }

    public boolean ismIsGridView() {
        return mIsGridView;
    }

    public void setmIsGridView(boolean mIsGridView) {
        this.mIsGridView = mIsGridView;
    }

    public boolean ismResumeSession() {
        return mResumeSession;
    }

    public void setmResumeSession(boolean mResumeSession) {
        this.mResumeSession = mResumeSession;
    }

    public int[] getmScheme() {
        return mScheme;
    }

    public void setmScheme(int[] mScheme) {
        this.mScheme = mScheme;
    }

    public float getmMemorybarHeight() {
        return mMemorybarHeight;
    }

    public void setmMemorybarHeight(float mMemorybarHeight) {
        this.mMemorybarHeight = mMemorybarHeight;
    }

    public String getmHeadingFont() {
        return mHeadingFont;
    }

    public void setmHeadingFont(String mHeadingFont) {
        this.mHeadingFont = mHeadingFont;
    }

    public String getmListFont() {
        return mListFont;
    }

    public void setmListFont(String mListFont) {
        this.mListFont = mListFont;
    }

    public boolean ismHeadingFromAssets() {
        return mHeadingFromAssets;
    }

    public void setmHeadingFromAssets(boolean mHeadingFromAssets) {
        this.mHeadingFromAssets = mHeadingFromAssets;
    }

    public boolean ismListFromAssets() {
        return mListFromAssets;
    }

    public void setmListFromAssets(boolean mListFromAssets) {
        this.mListFromAssets = mListFromAssets;
    }

    public boolean ismMultiSelect() {
        return mMultiSelect;
    }

    public void setmMultiSelect(boolean mMultiSelect) {
        this.mMultiSelect = mMultiSelect;
    }
}
