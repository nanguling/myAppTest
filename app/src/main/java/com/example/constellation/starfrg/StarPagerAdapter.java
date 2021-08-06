package com.example.constellation.starfrg;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class StarPagerAdapter extends PagerAdapter {

    Context context;

    List<ImageView> ivList;

    public StarPagerAdapter(Context context, List<ImageView> ivList) {
        this.context = context;
        this.ivList = ivList;
    }

    @Override
    public int getCount() {
        return ivList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = ivList.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ImageView imageView = ivList.get(position);
        container.removeView(imageView);
    }
}
