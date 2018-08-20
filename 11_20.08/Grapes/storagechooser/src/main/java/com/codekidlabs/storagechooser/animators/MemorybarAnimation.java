package com.codekidlabs.storagechooser.animators;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

/**
 * display a nice animation when chooser mDialog is shown
 */

public class MemorybarAnimation extends Animation {

    private ProgressBar mProgressBar;
    private float mFrom;
    private float mTo;


    public MemorybarAnimation(ProgressBar progressBar, int from, int to) {
        this.mProgressBar = progressBar;
        this.mFrom = from;
        this.mTo = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float animatedProgress = mFrom + (mTo - mFrom) * interpolatedTime;
        mProgressBar.setProgress((int) animatedProgress);
    }
}
