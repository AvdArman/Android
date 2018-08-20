package com.codekidlabs.storagechooser.adapters;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codekidlabs.storagechooser.StorageChooser;
import com.codekidlabs.storagechooser.fragments.ChooserDialogFragment;
import com.codekidlabs.storagechooser.utils.FileUtil;
import com.codekidlabs.storagechooser.utils.ResourceUtil;
import com.codekidlabs.storagechooser.utils.ThumbnailUtil;
import com.instigatemobile.storagechooser.R;

import java.util.ArrayList;
import java.util.List;

public class SecondaryChooserAdapter extends BaseAdapter {

    public static boolean mShouldEnable = true;
    public ArrayList<Integer> mSelectedPaths;
    public String mPrefixPath;
    private List<String> mStoragesList;
    private Context mContext;
    private int[] mScheme;
    private ThumbnailUtil mThumbnailUtil;
    private ResourceUtil mResourceUtil;
    private String mListTypeface;
    private boolean mFromAssets;


    public SecondaryChooserAdapter(List<String> storagesList, Context mContext, int[] scheme,
                                   String listTypeface, boolean fromAssets) {
        this.mStoragesList = storagesList;
        this.mContext = mContext;
        this.mScheme = scheme;
        this.mListTypeface = listTypeface;
        this.mFromAssets = fromAssets;

        // create instance once
        mThumbnailUtil = new ThumbnailUtil(mContext);
        mResourceUtil = new ResourceUtil(mContext);
        mSelectedPaths = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mStoragesList.size();
    }

    @Override
    public Object getItem(int i) {
        return mStoragesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = inflater.inflate(R.layout.row_custom_paths, viewGroup, false);

        ImageView pathFolderIcon = rootView.findViewById(R.id.path_folder_icon);
        if (FileUtil.isDir(mPrefixPath + "/" + mStoragesList.get(i))) {
            applyFolderTint(pathFolderIcon);
        }

        mThumbnailUtil.init(pathFolderIcon, mStoragesList.get(i));

        TextView storageName = rootView.findViewById(R.id.storage_name);
        storageName.setText(mStoragesList.get(i));

        if (mListTypeface != null) {
            storageName.setTypeface(ChooserDialogFragment.getSCTypeface(mContext, mListTypeface,
                    mFromAssets));
        }


        storageName.setTextColor(mScheme[StorageChooser.Theme.SEC_TEXT_INDEX]);

        if (mSelectedPaths.contains(i)) {
            rootView.setBackgroundColor(mResourceUtil.getPrimaryColorWithAlpha());
        }

        return rootView;
    }


    /**
     * return the spannable index of character '('
     *
     * @param str SpannableStringBuilder to apply typeface changes
     * @return index of '('
     */
    private int getSpannableIndex(SpannableStringBuilder str) {
        return str.toString().indexOf("(") + 1;
    }

    public String getmPrefixPath() {
        return mPrefixPath;
    }

    public void setmPrefixPath(String path) {
        this.mPrefixPath = path;
    }

    private void applyFolderTint(ImageView im) {
        im.setColorFilter(mScheme[StorageChooser.Theme.SEC_FOLDER_TINT_INDEX]);
    }

    @Override
    public boolean isEnabled(int position) {
        return mShouldEnable;
    }

}
