package com.example.csestudentmate;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class PageAdapter extends FragmentPagerAdapter {
    private String[] pageNames;
    private int totalPages;
    private Fragment fragment;
    private String fragmentName;

    public PageAdapter(FragmentManager fm, String[] pageNames, int totalPages, String fragmentName) {
        super(fm);
        this.pageNames = pageNames;
        this.totalPages = totalPages;
        this.fragmentName = fragmentName;
    }

    @Override
    public Fragment getItem(int i) {
        fragment = null;
        if(fragmentName.matches("Home")){
            if(i == 0){
                fragment = new CalendarPage();
            }else if(i == 1){
                fragment = new AlarmPage();
            }
        }else if(fragmentName.matches("Class")){
            if(i == 0){
                fragment = new IncourseResult();
            }else if(i == 1){
                fragment = new FinalResult();
            }else if (i == 2){
                fragment = new Attendance();
            }
        }else if (fragmentName.matches("NUResult")){
            if(i == 0){
                fragment = new SemesterResult();
            }else if(i == 1){
                fragment = new TotalCGPA();
            }
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
