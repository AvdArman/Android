package com.example.arman.grapes;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class CustomPagerAdapter extends PagerAdapter {

    private List<PreviewObjects> previewObjectsList;

    public CustomPagerAdapter(List<PreviewObjects> list) {
        this.previewObjectsList = list;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.preview_activity, container, false);
        PreviewObjects previewObjects = previewObjectsList.get(position);
        ImageView img = itemView.findViewById(R.id.image);
        TextView tvText = itemView.findViewById(R.id.tv_text);
        img.setImageResource(previewObjects.getImg());
        tvText.setText(previewObjects.getText());
        if (previewObjects.getLogoText() != null) {
            img.setMaxWidth(50);
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
        return previewObjectsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}