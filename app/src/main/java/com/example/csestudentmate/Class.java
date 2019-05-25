package com.example.csestudentmate;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Class extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_class, container, false);

        tabLayout = view.findViewById(R.id.TabsId);
        viewPager = view.findViewById(R.id.ViewPagerId);

        SectionPageAdapter sectionPageAdapter = new SectionPageAdapter(getFragmentManager());
        String[] pageNames = new String[]{"Incourse Result", "Final Result", "Attendance"};
        sectionPageAdapter.addPage(pageNames, 3, "Home", view.getContext());
        viewPager.setAdapter(sectionPageAdapter);
        tabLayout.setTabTextColors(Color.WHITE,Color.GREEN);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
