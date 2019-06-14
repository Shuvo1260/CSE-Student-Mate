package com.example.csestudentmate.Class;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.csestudentmate.Class.AttendancePage.Attendance;
import com.example.csestudentmate.Class.FinalReusltPage.FinalResult;
import com.example.csestudentmate.Class.IncourseResultPage.IncourseResult;
import com.example.csestudentmate.Adapter.PageAdapter;
import com.example.csestudentmate.R;


public class Class extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class, container, false);

        tabLayout = view.findViewById(R.id.ClassTabsId);
        viewPager = view.findViewById(R.id.ClassViewPagerId);


        PagerAdapter classPageAdapter = new PageAdapter(getChildFragmentManager());
        ((PageAdapter) classPageAdapter).addFragment(new IncourseResult(), "Incourse Result");
        ((PageAdapter) classPageAdapter).addFragment(new FinalResult(), "College Final Result");
        ((PageAdapter) classPageAdapter).addFragment(new Attendance(), "Attendance");

        viewPager.setAdapter(classPageAdapter);
        tabLayout.setTabTextColors(Color.WHITE,Color.GREEN);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
