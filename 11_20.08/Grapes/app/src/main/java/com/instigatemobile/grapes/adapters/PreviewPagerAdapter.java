package com.instigatemobile.grapes.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.instigatemobile.grapes.models.PreviewModel;
import com.instigatemobile.grapes.R;

import java.util.List;

public class PreviewPagerAdapter extends PagerAdapter {

    private List<PreviewModel> mPreviewModelList;

    public PreviewPagerAdapter(List<PreviewModel> list) {
        this.mPreviewModelList = list;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.preview_content, container, false);
        PreviewModel previewModel = mPreviewModelList.get(position);
        ImageView img = itemView.findViewById(R.id.preview_image);
        TextView tvText = itemView.findViewById(R.id.tv_text);
        img.setImageResource(previewModel.getImage());
        tvText.setText(previewModel.getText());
        if (previewModel.getLogoText() != null) {
            TextView tvLogoText = itemView.findViewById(R.id.tv_logo_text);
            tvLogoText.setText("Grapes");
        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mPreviewModelList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}