package com.codekidlabs.storagechooser.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codekidlabs.storagechooser.Content;
import com.codekidlabs.storagechooser.animators.MemorybarAnimation;
import com.codekidlabs.storagechooser.exceptions.MemoryNotAccessibleException;
import com.codekidlabs.storagechooser.fragments.ChooserDialogFragment;
import com.codekidlabs.storagechooser.models.Storages;
import com.codekidlabs.storagechooser.utils.MemoryUtil;
import com.instigatemobile.storagechooser.R;

import java.util.List;

import static com.codekidlabs.storagechooser.StorageChooser.Theme.OVERVIEW_INDICATOR_INDEX;
import static com.codekidlabs.storagechooser.StorageChooser.Theme.OVERVIEW_MEMORYBAR_INDEX;
import static com.codekidlabs.storagechooser.StorageChooser.Theme.OVERVIEW_STORAGE_TEXT_INDEX;

public class StorageChooserListAdapter extends BaseAdapter {

    private static int mMemoryPercentile;
    private List<Storages> mStoragesList;
    private Context mContext;
    private boolean mShouldShowMemoryBar;
    private ProgressBar mMemoryBar;
    private int[] mScheme;
    private float mMemorybarHeight;
    private String mListTypeface;
    private boolean mFromAssets;
    private Content mContent;


    public StorageChooserListAdapter(List<Storages> storagesList, Context mContext,
                                     boolean shouldShowMemoryBar, int[] scheme,
                                     float memorybarHeight, String listTypeface, boolean fromAssets,
                                     Content content) {
        this.mStoragesList = storagesList;
        this.mContext = mContext;
        this.mShouldShowMemoryBar = shouldShowMemoryBar;
        this.mScheme = scheme;
        this.mMemorybarHeight = memorybarHeight;
        this.mListTypeface = listTypeface;
        this.mFromAssets = fromAssets;
        this.mContent = content;
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
        mMemoryPercentile = -1;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.row_storage, viewGroup, false);

        //for animation set current position to provide animation delay
        TextView storageName = rootView.findViewById(R.id.storage_name);
        TextView memoryStatus = rootView.findViewById(R.id.memory_status);
        mMemoryBar = rootView.findViewById(R.id.memory_bar);

        // new scaled memorybar - following the new google play update!
        mMemoryBar.setScaleY(mMemorybarHeight);

        Storages storages = mStoragesList.get(i);
        final SpannableStringBuilder str = new SpannableStringBuilder(storages.getmStorageTitle() + " (" + storages.getmMemoryTotalSize() + ")");

        str.setSpan(new StyleSpan(Typeface.ITALIC), getSpannableIndex(str), str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        String availableText = String.format(mContent.getmFreeSpaceText(), storages.getmMemoryAvailableSize());
        storageName.setText(str);

        storageName.setTextColor(mScheme[OVERVIEW_STORAGE_TEXT_INDEX]);
        memoryStatus.setText(availableText);

        if (mListTypeface != null) {
            storageName.setTypeface(ChooserDialogFragment.getSCTypeface(mContext, mListTypeface,
                    mFromAssets));
            memoryStatus.setTypeface(ChooserDialogFragment.getSCTypeface(mContext, mListTypeface,
                    mFromAssets));
        }

        memoryStatus.setTextColor(mScheme[OVERVIEW_INDICATOR_INDEX]);
        DrawableCompat.setTint(mMemoryBar.getProgressDrawable(), mScheme[OVERVIEW_MEMORYBAR_INDEX]);

        try {
            mMemoryPercentile = getPercentile(storages.getmStoragePath());
        } catch (MemoryNotAccessibleException e) {
            e.printStackTrace();
        }
        // THE ONE AND ONLY MEMORY BAR
        if (mShouldShowMemoryBar && mMemoryPercentile != -1) {
            mMemoryBar.setMax(100);
            mMemoryBar.setProgress(mMemoryPercentile);
            runMemorybarAnimation(i);
        } else {
            mMemoryBar.setVisibility(View.GONE);
        }

        return rootView;

    }

    private void runMemorybarAnimation(int pos) {
        MemorybarAnimation animation = new MemorybarAnimation(mMemoryBar, 0, mMemoryPercentile);
        animation.setDuration(500);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());

        if (pos > 0) {
            animation.setStartOffset(300);
        }

        mMemoryBar.startAnimation(animation);
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

    /**
     * calculate percentage of memory left for memorybar
     *
     * @param path use same statfs
     * @return integer value of the percentage with amount of storage used
     */
    private int getPercentile(String path) throws MemoryNotAccessibleException {
        MemoryUtil mMemoryUtil = new MemoryUtil();
        int percent;

        long mAvailableMem = mMemoryUtil.getAvailableMemorySize(path);
        long mTotalMem = mMemoryUtil.getTotalMemorySize(path);

        if (mTotalMem > 0) {
            percent = (int) (100 - ((mAvailableMem * 100) / mTotalMem));
        } else {
            throw new MemoryNotAccessibleException("Cannot compute memory for " + path);
        }

        return percent;
    }

    /**
     * remove KiB,MiB,GiB text that we got from MemoryUtil.getAvailableMemorySize() &
     * MemoryUtil.getTotalMemorySize()
     *
     * @param size String in the format of user readable string, with MB, GiB .. suffix
     * @return integer value of the percentage with amount of storage used
     */
    private long getMemoryFromString(String size) {
        long mMemory;

        if (size.contains("MiB")) {
            mMemory = Integer.parseInt(size.replace(",", "").replace("MiB", ""));
        } else if (size.contains("GiB")) {
            mMemory = Integer.parseInt(size.replace(",", "").replace("GiB", ""));
        } else {
            mMemory = Integer.parseInt(size.replace(",", "").replace("KiB", ""));
        }

        Log.d("TAG", "Memory:" + mMemory);
        return mMemory;
    }
}
