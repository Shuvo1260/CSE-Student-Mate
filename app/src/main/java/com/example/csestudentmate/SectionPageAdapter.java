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


class SectionPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<Fragment>(10);
    private String fragmentName;
    private String[] pageNames;
    private int totalPages;
    private Context context;

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addPage(String[] pageNames, int totalPages, String fragmentName, Context context){
        this.pageNames = pageNames;
        this.totalPages = totalPages;
        this.fragmentName = fragmentName.trim();
        this.context = context;

        Toast.makeText(context, "fragmentName: " + fragmentName.trim(), Toast.LENGTH_SHORT).show();
        fragmentList.clear();

        fragmentList = new ArrayList<Fragment>();

        if(fragmentName.matches("Home")){
            fragmentList.add(new CalendarPage());
            fragmentList.add(new AlarmPage());
        }else if(fragmentName.matches("Class")){
            fragmentList.add(new IncourseResult());
            fragmentList.add(new FinalResult());
//            fragmentList.add(new Attendance());
        }else if(fragmentName.matches("NU Result")){
            fragmentList.add(new SemesterResult());
            fragmentList.add(new TotalCGPA());
        }
    }

    public void clearPage(){
        fragmentList.clear();
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
}
