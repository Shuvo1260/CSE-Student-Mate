package com.example.csestudentmate.NUResult;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.csestudentmate.Adapter.PageAdapter;
import com.example.csestudentmate.R;


public class NU_Result extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nu__result, container, false);
        tabLayout = view.findViewById(R.id.nuTabsId);
        viewPager = view.findViewById(R.id.nuViewPagerId);

        PageAdapter nuResultPageAdapter = new PageAdapter(getChildFragmentManager());
        nuResultPageAdapter.addFragment(new SemesterResult(), "Semester Result");
        nuResultPageAdapter.addFragment(new TotalCGPA(), "Total CGPA");

        viewPager.setAdapter(nuResultPageAdapter);
        tabLayout.setTabTextColors(Color.WHITE,Color.GREEN);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
