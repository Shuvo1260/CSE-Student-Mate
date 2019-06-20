package com.example.csestudentmate.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.csestudentmate.Home.AlarmClockPage.AlarmPage;
import com.example.csestudentmate.Home.NotepadPage.Features.DailyNotes;
import com.example.csestudentmate.Home.Reminders.Features.RemindersList;
import com.example.csestudentmate.Adapter.PageAdapter;
import com.example.csestudentmate.R;


public class Home extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = view.findViewById(R.id.TabsId);
        viewPager = view.findViewById(R.id.ViewPagerId);

        PagerAdapter homePageAdapter = new PageAdapter(getChildFragmentManager());
        ((PageAdapter) homePageAdapter).addFragment(new DailyNotes(),"Daily Notes");
        ((PageAdapter) homePageAdapter).addFragment(new RemindersList(),"RemindersList");
        ((PageAdapter) homePageAdapter).addFragment(new AlarmPage(), "Alarm Clock");

        viewPager.setAdapter(homePageAdapter);
        tabLayout.setTabTextColors(Color.WHITE,Color.GREEN);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
