package com.example.csestudentmate;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.List;


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

        String[] pageNames = new String[]{"Incourse Result", " College Final Result", "Attendance"};
        PagerAdapter classPageAdapter = new PageAdapter(getChildFragmentManager(), pageNames, 3);
        ((PageAdapter) classPageAdapter).addFragment(new IncourseResult());
        ((PageAdapter) classPageAdapter).addFragment(new FinalResult());
        ((PageAdapter) classPageAdapter).addFragment(new Attendance());
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
