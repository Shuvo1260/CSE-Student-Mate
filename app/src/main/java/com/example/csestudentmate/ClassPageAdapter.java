package com.example.csestudentmate;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class ClassPageAdapter extends FragmentPagerAdapter {
    private String[] pageNames;
    private int totalPages;
    private Fragment fragment;

    public ClassPageAdapter(FragmentManager fm, String[] pageNames, int totalPages) {
        super(fm);
        this.pageNames = pageNames;
        this.totalPages = totalPages;
    }

    @Override
    public Fragment getItem(int i) {
        fragment = null;
        if(i == 0){
            fragment = new IncourseResult();
        }else if(i == 1){
            fragment = new FinalResult();
        } else if(i == 2){
            fragment = new Attendance();
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
