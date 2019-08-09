package com.Raamsa.raamsa.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.Raamsa.raamsa.entity.Projects;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PagerAdapterForCommingSoonProjects extends PagerAdapter {
    Context context;
    List<Projects> projectsList;
    private LayoutInflater layoutInflater;

    public PagerAdapterForCommingSoonProjects(Context context, List<Projects> projectsList) {
        this.context = context;
        this.projectsList = projectsList;
    }

    @Override
    public int getCount() {
        return projectsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }


    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.with(context).load(projectsList.get(position).getImage()).fit().into(imageView);
        ((ViewPager) container).addView(imageView, 0);
       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PropertyDetails.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });*/
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }


}
