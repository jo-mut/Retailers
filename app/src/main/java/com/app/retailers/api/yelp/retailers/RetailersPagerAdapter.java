package com.app.retailers.api.yelp.retailers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by J.EL on 11/3/2017.
 */

public class RetailersPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Retailer> retailers;

    public RetailersPagerAdapter(FragmentManager fm, ArrayList<Retailer> retailers) {
        super(fm);
        this.retailers = retailers;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return retailers.get(position).getName();
    }

}
