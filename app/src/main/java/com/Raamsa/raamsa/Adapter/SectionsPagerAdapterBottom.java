package com.Raamsa.raamsa.Adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.Raamsa.raamsa.fragments.HomeFragment;
import com.Raamsa.raamsa.fragments_Owner.EmptyFragment;
import com.Raamsa.raamsa.fragments_Owner.HistoryFragment_Owner;
import com.Raamsa.raamsa.fragments_Owner.Owener_HomeFragment;
import com.Raamsa.raamsa.fragments_Owner.ProfileFragment_Owener;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapterBottom extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapterBottom(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1);
        Fragment fragment;
        if (position == 0) {
            fragment = new ProfileFragment_Owener();
        } else if (position == 1) {
            fragment = new Owener_HomeFragment();
        } else if (position == 2) {
            fragment = new HistoryFragment_Owner();
        } else {
            fragment = new EmptyFragment();
        }
        return fragment;
    }

   /* @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }*/

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}