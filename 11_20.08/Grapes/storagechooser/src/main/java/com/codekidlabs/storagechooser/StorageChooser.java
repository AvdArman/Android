package com.codekidlabs.storagechooser;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codekidlabs.storagechooser.fragments.ChooserDialogFragment;
import com.codekidlabs.storagechooser.models.Config;
import com.codekidlabs.storagechooser.utils.DiskUtil;
import com.instigatemobile.storagechooser.R;

import java.util.ArrayList;
import java.util.List;


public class StorageChooser {

    public static final String NONE = "none";
    public static final String DIRECTORY_CHOOSER = "dir";
    public static final String FILE_PICKER = "file";
    public static Dialog mDialog;
    public static Config mSConfig;
    public static OnSelectListener mOnSelectListener;
    public static OnCancelListener mOnCancelListener;
    public static OnMultipleSelectListener mOnMultipleSelectListener;
    public static String LAST_SESSION_PATH = null;
    private Activity mChooserActivity;
    private final String TAG = this.getClass().getName();

    /**
     * basic constructor of StorageChooser
     *
     * @param config to use with mDialog window addition
     */
    StorageChooser(Activity activity, Config config) {
        setmSConfig(config);
        setmChooserActivity(activity);
    }

    /**
     * blank constructor of StorageChooser
     * no params used only for internal library use
     */
    public StorageChooser() {
    }

    public static Config getmSConfig() {
        return mSConfig;
    }

    public static void setmSConfig(Config mSConfig) {
        StorageChooser.mSConfig = mSConfig;
    }

    private static Dialog getStorageChooserDialog(Activity activity) {
        return new Dialog(activity, R.style.DialogTheme);
    }

    /**
     * show() shows the storage chooser mDialog
     */
    public void show() {
        init();
    }

    /**
     * init() creates the storage chooser mDialog
     */
    private void init() {
        mDialog = getStorageChooserDialog(getmChooserActivity());

        // check if listeners are set to avoid crash
        if (mOnSelectListener == null) {
            mOnSelectListener = getDefaultOnSelectListener();
        }
        if (mOnCancelListener == null) {
            mOnCancelListener = getDefaultOnCancelListener();
        }
        if (mOnMultipleSelectListener == null) {
            mOnMultipleSelectListener = getDefaultMultipleSelectionListener();
        }

        if (mSConfig.ismResumeSession() && StorageChooser.LAST_SESSION_PATH != null) {
            DiskUtil.showSecondaryChooser(StorageChooser.LAST_SESSION_PATH, mSConfig);
        } else {

            // if dev needs to skip overview and the primary path is not mentioned the directory
            // chooser or file picker will default to internal storage
            if (mSConfig.ismSkipOverview()) {
                if (mSConfig.getmPrimaryPath() == null) {

                    // internal storage is always the first element (I took care of it :wink:)
                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    DiskUtil.showSecondaryChooser(dirPath, mSConfig);
                } else {

                    // path provided by dev is the goto path for chooser
                    DiskUtil.showSecondaryChooser(mSConfig.getmPrimaryPath(), mSConfig);
                }

            } else {
                ChooserDialogFragment chooserDialogFragment = new ChooserDialogFragment();
                chooserDialogFragment.show(mSConfig.getmFragmentManager(), "storagechooser_dialog");
            }
        }
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        StorageChooser.mOnSelectListener = onSelectListener;
    }


    public void setOnCancelListener(OnCancelListener onCancelListener) {
        StorageChooser.mOnCancelListener = onCancelListener;
    }

    public void setOnMultipleSelectListener(OnMultipleSelectListener onMultipleSelectListener) {
        StorageChooser.mOnMultipleSelectListener = onMultipleSelectListener;
    }

    private Activity getmChooserActivity() {
        return mChooserActivity;
    }

    private void setmChooserActivity(Activity mChooserActivity) {
        this.mChooserActivity = mChooserActivity;
    }

    private OnSelectListener getDefaultOnSelectListener() {
        return new OnSelectListener() {
            @Override
            public void onSelect(String path) {
                Log.e(TAG, "You need to setup OnSelectListener from your side. OUTPUT: " + path);
            }
        };
    }

    private OnCancelListener getDefaultOnCancelListener() {
        return new OnCancelListener() {
            @Override
            public void onCancel() {
                Log.e(TAG, "You need to setup OnCancelListener from your side. This is default OnCancelListener fired.");
            }
        };
    }

    private OnMultipleSelectListener getDefaultMultipleSelectionListener() {
        return new OnMultipleSelectListener() {
            @Override
            public void onDone(ArrayList<String> selectedFilePaths) {
                Log.e(TAG, "You need to setup OnMultipleSelectListener from your side. This is default OnMultipleSelectListener fired.");
            }
        };
    }

    public enum FileType {
        VIDEO, AUDIO, DOCS, IMAGES, ARCHIVE
    }

    public interface OnSelectListener {

        void onSelect(String path);
    }

    public interface OnCancelListener {

        void onCancel();
    }

    public interface OnMultipleSelectListener {
        void onDone(ArrayList<String> selectedFilePaths);
    }

    /**
     * @class Builder
     * - as the name suggests it gets all the configurations provided by the developer and
     * passes them to the StorageChooser class using the constructor.
     * <p>
     * NOTE: The mDialog is still not yet show even though the builder instance is present.
     * show() is called seperately on the builder because it does not return a builder but
     * triggers init() inside the StorageChooser class.
     */
    public static class Builder {

        Config devConfig;
        private Activity mActivity;
        private boolean mActionSave = false;
        private boolean mShowMemoryBar = false;
        private boolean mAllowCustomPath = false;
        private boolean mAllowAddFolder = false;
        private boolean mShowHidden = false;
        private boolean mSkipOverview = false;
        private boolean mApplyMemoryThreshold = false;
        private boolean mShowInGrid = false;
        private boolean mResumeSession = false;
        private boolean mHeadingFromAssets = false;
        private boolean mListFromAssets = false;
        private float mMemorybarHeight = 2f;
        private String type;
        private Content content;
        private Theme theme;
        private FileType filter;
        private ArrayList<FileType> multipleFilter;

        public Builder() {
            devConfig = new Config();
        }

        public Builder withActivity(Activity activity) {
            mActivity = activity;
            return this;
        }

        public Builder withFragmentManager(android.app.FragmentManager fragmentManager) {
            devConfig.setmFragmentManager(fragmentManager);
            return this;
        }

        public Builder withMemoryBar(boolean memoryBarBoolean) {
            mShowMemoryBar = memoryBarBoolean;
            return this;
        }

        public Builder setMemoryBarHeight(float height) {
            this.mMemorybarHeight = height;
            return this;
        }

        public Builder withPredefinedPath(String path) {
            devConfig.setmPredefinedPath(path);
            return this;
        }

        public Builder applyMemoryThreshold(boolean applyThreshold) {
            mApplyMemoryThreshold = applyThreshold;
            return this;
        }

        public Builder withThreshold(int size, String suffix) {
            devConfig.setmMemoryThreshold(size);
            devConfig.setmThresholdSuffix(suffix);
            return this;
        }

        public Builder withPreference(SharedPreferences sharedPreferences) {
            devConfig.setmPreference(sharedPreferences);
            return this;
        }

        public Builder actionSave(boolean save) {
            mActionSave = save;
            return this;
        }

        public Builder setDialogTitle(String title) {
            devConfig.setmDialogTitle(title);
            return this;
        }

        public Builder setInternalStorageText(String storageNameText) {
            devConfig.setmInternalStorageText(storageNameText);
            return this;
        }

        public Builder allowCustomPath(boolean allowCustomPath) {
            mAllowCustomPath = allowCustomPath;
            return this;
        }

        public Builder allowAddFolder(boolean addFolder) {
            mAllowAddFolder = addFolder;
            return this;
        }

        public Builder showHidden(boolean showHiddenFolders) {
            mShowHidden = showHiddenFolders;
            return this;
        }

        public Builder setType(String action) {
            type = action;
            return this;
        }

        public Builder setTheme(Theme theme) {
            this.theme = theme;
            return this;
        }

        public Builder skipOverview(boolean skip, String primaryPath) {
            mSkipOverview = skip;
            devConfig.setmPrimaryPath(primaryPath);
            return this;
        }

        public Builder skipOverview(boolean skip) {
            mSkipOverview = skip;
            return this;
        }

        public Builder withContent(Content content) {
            this.content = content;
            return this;
        }

        public Builder filter(@Nullable FileType filter) {
            this.filter = filter;
            return this;
        }

        public Builder crunch() {
            devConfig.setmCustomFilter(false);
            return this;
        }

        public Builder customFilter(List<String> formats) {
            devConfig.setmCustomFilter(true);
            devConfig.setmCustomEnum(formats);
            return this;
        }

        public Builder showFoldersInGrid(boolean showInGrid) {
            devConfig.setmIsGridView(showInGrid);
            return this;
        }

        public Builder shouldResumeSession(boolean resumeSession) {
            this.mResumeSession = resumeSession;
            return this;
        }

        // typefaces
        public Builder setHeadingTypeface(String path, boolean fromAssets) {
            devConfig.setmHeadingFont(path);
            mHeadingFromAssets = fromAssets;
            return this;
        }

        public Builder setListTypeface(String path, boolean fromAssets) {
            devConfig.setmListFont(path);
            mListFromAssets = fromAssets;
            return this;
        }

        public Builder disableMultiSelect() {
            devConfig.setmMultiSelect(false);
            return this;
        }


        public StorageChooser build() {
            devConfig.setmActionSave(mActionSave);
            devConfig.setmShowMemoryBar(mShowMemoryBar);
            devConfig.setmAllowCustomPath(mAllowCustomPath);
            devConfig.setmAllowAddFolder(mAllowAddFolder);
            devConfig.setmShowHidden(mShowHidden);
            devConfig.setmSkipOverview(mSkipOverview);
            devConfig.setmApplyThreshold(mApplyMemoryThreshold);
            devConfig.setmResumeSession(mResumeSession);
            devConfig.setmIsGridView(mShowInGrid);
            devConfig.setmContent(content);
            devConfig.setmSingleFilter(filter);
            devConfig.setmMemorybarHeight(mMemorybarHeight);
            devConfig.setmHeadingFromAssets(mHeadingFromAssets);
            devConfig.setmListFromAssets(mListFromAssets);

            type = (type == null) ? StorageChooser.NONE : type;
            devConfig.setmSecondaryAction(type);

            if (theme == null || theme.getScheme() == null) {
                theme = new Theme(mActivity);
                devConfig.setmScheme(theme.getDefaultScheme());
            } else {
                devConfig.setmScheme(theme.getScheme());
            }

            return new StorageChooser(mActivity, devConfig);
        }

    }

    public static class Theme {

        // Overview mDialog colors
        public static final int OVERVIEW_HEADER_INDEX = 0;
        public static final int OVERVIEW_TEXT_INDEX = 1;
        public static final int OVERVIEW_BG_INDEX = 2;
        public static final int OVERVIEW_STORAGE_TEXT_INDEX = 3;
        public static final int OVERVIEW_INDICATOR_INDEX = 4;
        public static final int OVERVIEW_MEMORYBAR_INDEX = 5;
        // Secondary mDialog colors
        public static final int SEC_FOLDER_TINT_INDEX = 6;
        public static final int SEC_BG_INDEX = 7;
        public static final int SEC_TEXT_INDEX = 8;
        public static final int SEC_ADDRESS_TINT_INDEX = 9;
        public static final int SEC_HINT_TINT_INDEX = 10;
        public static final int SEC_SELECT_LABEL_INDEX = 11;
        public static final int SEC_FOLDER_CREATION_BG_INDEX = 12;
        public static final int SEC_DONE_FAB_INDEX = 13;
        public static final int SEC_ADDRESS_BAR_BG = 14;
        Context context;
        int[] scheme;

        public Theme(Context context) {
            this.context = context;
        }

        public int[] getDefaultScheme() {
            return context.getResources().getIntArray(R.array.default_light);
        }

        public int[] getDefaultDarkScheme() {
            return context.getResources().getIntArray(R.array.default_dark);
        }


        public int[] getScheme() {
            return scheme;
        }

        public void setScheme(int[] scheme) {
            this.scheme = scheme;
        }
    }
}
