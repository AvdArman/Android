package com.instigatemobile.todolist.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.instigatemobile.todolist.R;
import com.instigatemobile.todolist.modules.SliderObjects;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {

    private List<SliderObjects> sliderObjectsList;

    public SliderPagerAdapter(List<SliderObjects> list) {
        this.sliderObjectsList = list;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.slider_content, container, false);
        final SliderObjects sliderObjects = sliderObjectsList.get(position);
        final ImageView img = itemView.findViewById(R.id.image);
        final TextView tvText = itemView.findViewById(R.id.tv_text);
        img.setImageResource(sliderObjects.getImg());
        tvText.setText(sliderObjects.getText());
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return sliderObjectsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}