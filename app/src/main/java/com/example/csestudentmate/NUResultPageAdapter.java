package com.example.csestudentmate;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class NUResultPageAdapter extends FragmentPagerAdapter {
    private String[] pageNames;
    private int totalPages;
    private Fragment fragment;

    public NUResultPageAdapter(FragmentManager fm, String[] pageNames, int totalPages) {
        super(fm);
        this.pageNames = pageNames;
        this.totalPages = totalPages;
    }

    @Override
    public Fragment getItem(int i) {
        fragment = null;
        if(i == 0){
            fragment = new SemesterResult();
        }else if(i == 1){
            fragment = new TotalCGPA();
        }
        return fragment;
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
}
