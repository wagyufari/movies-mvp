package com.nacoda.moviesmvpdagger2rxjava.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mayburger on 10/26/17.
 */

public class TabSliderAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> nameList = new ArrayList<>();


    public TabSliderAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        nameList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return nameList.get(position);
    }
}