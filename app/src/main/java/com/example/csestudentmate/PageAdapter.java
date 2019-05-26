package com.example.csestudentmate;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


class PageAdapter extends FragmentPagerAdapter {
    private String[] pageNames;
    private int totalPages;
    private Fragment fragment;
    private String fragmentName;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();


    public PageAdapter(FragmentManager fm, String[] pageNames, int totalPages) {
        super(fm);
        this.pageNames = pageNames;
        this.totalPages = totalPages;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return totalPages;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageNames[position];
    }

    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }
}
