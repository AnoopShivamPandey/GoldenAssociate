package com.Raamsa.raamsa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.Raamsa.raamsa.UI.PropertyDetails;


public class PagerAdapterForSlider extends PagerAdapter {
    Context context;
    int[] sliderImages;
    private LayoutInflater layoutInflater;

    public PagerAdapterForSlider(Context context, int[] sliderImages) {
        this.context = context;
        this.sliderImages = sliderImages;
    }

    @Override
    public int getCount() {
        return sliderImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }


    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(sliderImages[position]);
        //Picasso.with(context).load("YOUR IMAGE URL HERE").into(imageView);
        ((ViewPager) container).addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PropertyDetails.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }


}
