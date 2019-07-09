package com.example.csestudentmate.Home.Reminders.Features;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csestudentmate.Home.Reminders.Adapter.ReminderAdapter;
import com.example.csestudentmate.Home.Reminders.Database.ReminderDatabaseQuery;
import com.example.csestudentmate.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RemindersList extends Fragment {

    private FloatingActionButton floatingActionButton;
    private TextView emptyText;
    private int WRITE_REMINDER_ACTIVITY_CODE = 1;
    private ReminderAdapter reminderAdapter;
    private RecyclerView recyclerView;

    private List<Reminder> reminderList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_reminders, container, false);

        floatingActionButton = view.findViewById(R.id.addReminderId);
        emptyText = view.findViewById(R.id.emptyReminderId);

        recyclerView = view.findViewById(R.id.reminderRecyclerViewId);

        retrieveReminders();

        reminderAdapter = new ReminderAdapter(getActivity(), reminderList, floatingActionButton);
        recyclerView.setAdapter(reminderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reminderAdapter.isDeletion()){
                    final ReminderDatabaseQuery reminderDatabaseQuery = new ReminderDatabaseQuery(getContext());
                    final List<Boolean> isChecked = reminderAdapter.getCheckedItem();
                    final List<Boolean> tempChecked = new ArrayList<>();
                    tempChecked.addAll(isChecked);
                    for(int index = 0; index < reminderList.size(); ){
                        if(tempChecked.get(index)){
                            reminderList.remove(index);
                            tempChecked.remove(index);
                        }else
                            index++;
                    }
                    reminderAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(reminderAdapter);

                    Snackbar snackbar = Snackbar.make(view, "Successfully Deleted", Snackbar.LENGTH_LONG);

                    snackbar.setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            retrieveReminders();
                            reminderAdapter.notifyDataSetChanged();
                            for(int index = 0; index < reminderList.size(); index++){
                                isChecked.set(index,false);
                            }
                            reminderAdapter.isCheckedBuild(isChecked);
                            recyclerView.setAdapter(reminderAdapter);
                            emptyChecker();
                        }
                    });

                    snackbar.addCallback(new Snackbar.Callback(){
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);
                            if(event != DISMISS_EVENT_ACTION){
                                retrieveReminders();
                                for(int index = 0; index < reminderList.size(); ){
                                    if(isChecked.get(index)){
                                        if(reminderDatabaseQuery.delete(reminderList.get(index)) != -1) {
                                            reminderList.remove(index);
                                            isChecked.remove(index);
                                        }
                                    }else{
                                        index++;
                                    }
                                }
                            }
                            reminderAdapter.isCheckedBuild(isChecked);
                            recyclerView.setAdapter(reminderAdapter);
                        }
                    });
                    snackbar.show();

                    floatingActionButton.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_white));
                    emptyChecker();
                }else{
                    addReminder();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Loading reminders from database
        retrieveReminders();
        reminderAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(reminderAdapter);

        emptyChecker();
    }

    public void emptyChecker(){
        // Checking the reminder list empty or not
        if(!reminderList.isEmpty()){
            emptyText.setVisibility(View.GONE);
        }else{
            emptyText.setVisibility(View.VISIBLE);
        }
    }
    public void retrieveReminders(){
        // Reminders retrieving from database
        reminderList.clear();
        ReminderDatabaseQuery reminderDatabaseQuery = new ReminderDatabaseQuery(getContext());
        reminderList.addAll(reminderDatabaseQuery.getAllReminders());
    }

    private void addReminder(){
        // Creating reminder dialog to get informations
        ReminderDialog reminderDialog = new ReminderDialog();

        Calendar calendar = Calendar.getInstance();

        // Setting current time in the dialog
        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        String date = DateFormat.getDateInstance().format(calendar.getTime());
        reminderDialog.setField(1,"", "", time, date);

        try {
            reminderDialog.setDissmissListener(new ReminderDialog.OnDismissListener() {
                @Override
                public void onDismiss(ReminderDialog reminderDialog) {

                    // Updating recycler view with new reminders
                    retrieveReminders();
                    List<Boolean> ischecked = new ArrayList<>();
                    for(int index = 0; index < reminderList.size(); index++)
                        ischecked.add(index,false);
                    reminderAdapter.isCheckedBuild(ischecked);
                    reminderAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(reminderAdapter);
                    emptyChecker();
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        reminderDialog.show(getChildFragmentManager(), "Reminder");

    }
}
