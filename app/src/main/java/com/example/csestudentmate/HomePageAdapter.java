package com.example.csestudentmate;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


class HomePageAdapter extends FragmentPagerAdapter {
    private String[] pageNames;
    private int totalPages;
    private Fragment fragment;

    public HomePageAdapter(FragmentManager fm, String[] pageNames, int totalPages) {
        super(fm);
        this.pageNames = pageNames;
        this.totalPages = totalPages;
    }

    @Override
    public Fragment getItem(int i) {
        fragment = null;
        if(i == 0){
            fragment = new CalendarPage();
        }else{
            fragment = new AlarmPage();
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
