package com.example.csestudentmate.Home.Reminders.Features;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.csestudentmate.Home.Reminders.Adapter.ReminderAdapter;
import com.example.csestudentmate.R;

import java.util.ArrayList;
import java.util.List;

public class RemindersList extends Fragment {

    private FloatingActionButton floatingActionButton;
    private TextView emptyText;
    private int WRITE_REMINDER_ACTIVITY_CODE = 1;
    private ReminderAdapter reminderAdapter;
    private RecyclerView recyclerView;

    private List<Reminder> reminderList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);

        floatingActionButton = view.findViewById(R.id.addReminderId);
        emptyText = view.findViewById(R.id.emptyReminderId);

        recyclerView = view.findViewById(R.id.reminderRecyclerViewId);

        retrieveReminders();
        emptyChecker();

        reminderAdapter = new ReminderAdapter(getActivity(), reminderList, floatingActionButton);
        recyclerView.setAdapter(reminderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //

        return view;
    }

    public void emptyChecker(){
        if(!reminderList.isEmpty()){
            emptyText.setVisibility(View.GONE);
        }else{
            emptyText.setVisibility(View.VISIBLE);
        }
    }
    public void retrieveReminders(){
        reminderList.add(new Reminder("Test", "Testingklkljlkjkljklklklklkl" +
                "lkjlkjlklkj" +
                "lkjlkjlkjlkjlkjlkjlkjlkjlkj" +
                "lkjlkjlkjlkljlkjlklk" +
                "kljlkjlkjlkjlkjlkj" +
                "lkjlkjlkjlkjlklkkllklk last\nfd\nfs\nfsd\nfd\nfd\nfd\nsf\ner\ndf\nfsd\nfd\nfd\nfd\nsf\ner\ndf" +
                "sfajlk finish\nfinish" +
                "finish 1\n finish2", 10, 0, 12, 8, 2019));
    }
}
