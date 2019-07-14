package com.example.csestudentmate.Home.Reminders.Features;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private final int ADD_REMINDER_REQUEST_CODE = 1;
    private final int UPDATE_REMINDER_REQUEST_CODE = 2;
    private ReminderAdapter reminderAdapter;
    private RecyclerView recyclerView;
    private View view;

    private List<Reminder> reminderList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reminders, container, false);

        floatingActionButton = view.findViewById(R.id.addReminderId);
        emptyText = view.findViewById(R.id.emptyReminderId);
        recyclerView = view.findViewById(R.id.reminderRecyclerViewId);

        retrieveReminders();

        setRecyclerView();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reminderAdapter.isDeletion()){
                    deleteReminder();
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

    // Checking data list empty or not
    private void emptyChecker(){
        // Checking the reminder list empty or not
        if(!reminderList.isEmpty()){
            emptyText.setVisibility(View.GONE);
        }else{
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    // Reminders collection method from database
    private void retrieveReminders(){
        // Reminders retrieving from database
        reminderList.clear();
        ReminderDatabaseQuery reminderDatabaseQuery = new ReminderDatabaseQuery(getContext());
        reminderList.addAll(reminderDatabaseQuery.getAllReminders());
    }

    // RecyclerView creation method
    private void setRecyclerView(){
        // reminderAdapter setting in the recyclerView
        reminderAdapter = new ReminderAdapter(getActivity(), reminderList, floatingActionButton);
        recyclerView.setAdapter(reminderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Determining which operation have to be taken
        reminderAdapter.setOnReminderClickListener(new ReminderAdapter.OnReminderClickListener() {

            @Override
            public void onItemClick(final CardView cardView, final int position, final List<Boolean> isChecked) {
                 // Determining the operation that have to be taken into reminder
                if(!isChecked.get(position) && !reminderAdapter.getAnyItemChecked()){
                    Log.d("Activation Code", "Code: "+ reminderList.get(position).getActivated());
                    // Calling update method
                    updateReminder(reminderList.get(position));
                }
                else if(isChecked.get(position) && reminderAdapter.getAnyItemChecked()){
                    // Restoring the view of unchecked item
                    cardView.setCardBackgroundColor(Color.WHITE);
                    isChecked.set(position, false);
                }else if(reminderAdapter.getAnyItemChecked()){
                    // Checked item view creationg
                    cardView.setCardBackgroundColor(getContext().getColor(R.color.noteSelectionColor));
                    isChecked.set(position, true);
                }

                // Finding any item is checked or not
                for(int index = 0; index < reminderList.size(); index++){
                    if(isChecked.get(index)){
                        reminderAdapter.setAnyItemChecked(true);
                        break;
                    }else{
                        reminderAdapter.setAnyItemChecked(false);
                    }
                }

                // If any item mark as selected then the floating action button will work as a delete button
                if(!reminderAdapter.getAnyItemChecked()){
                    floatingActionButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_add_white));
                }
            }
        });
    }

    // New reminder creation method
    private void addReminder(){
        // Creating reminder dialog to get informations
        ReminderDialog reminderDialog = new ReminderDialog();

        Calendar calendar = Calendar.getInstance();

        // Setting current time in the dialog
        String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
        String date = DateFormat.getDateInstance().format(calendar.getTime());

        // Setting reminder dialog field
        reminderDialog.setField(ADD_REMINDER_REQUEST_CODE,"", "", time, date);

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

    // Reminder deletion method
    private void deleteReminder(){
        final ReminderDatabaseQuery reminderDatabaseQuery = new ReminderDatabaseQuery(getContext());
        final List<Boolean> isChecked = reminderAdapter.getCheckedItem();
        final List<Boolean> tempChecked = new ArrayList<>();
        final List<Reminder> deletedList = new ArrayList<>();
        tempChecked.addAll(isChecked);

        // Temporary deletion of reminder
        for(int index = 0; index < reminderList.size(); ){
            if(tempChecked.get(index)){
                deletedList.add(reminderList.get(index));
                reminderList.remove(index);
                tempChecked.remove(index);
            }else
                index++;
        }
        reminderAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(reminderAdapter);

        Snackbar snackbar = Snackbar.make(view, "Successfully Deleted", Snackbar.LENGTH_LONG);

        // Undo operation to retrieve deleted reminder
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

        // Deleting reminder from the list permanently
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);

                // Button view changing and setting all item is unchecked
                floatingActionButton.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_white));
                reminderAdapter.setAnyItemChecked(false);
                Log.d("Reminder Button", "Deleted Reminder");

                if(event != DISMISS_EVENT_ACTION){
                    // Permanent deletion
                    for(int index = 0; index < isChecked.size(); ){
                        if(isChecked.get(index)){
                            isChecked.remove(index);
                        }else{
                            index++;
                        }
                    }

                    // Permanent deletion from database
                    for(int index = 0; index < deletedList.size(); index++){
                        if(reminderDatabaseQuery.delete(deletedList.get(index)) != -1) {
                            Log.d("Reminder List", "Deleted Reminder");
                        }
                    }
                    deletedList.clear();

                }
                reminderAdapter.isCheckedBuild(isChecked);
                recyclerView.setAdapter(reminderAdapter);
            }
        });
        snackbar.show();

        floatingActionButton.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_white));
        emptyChecker();
    }

    // Reminder update method
    private void updateReminder(final Reminder reminder){
        // Creating reminder dialog to get informations
        ReminderDialog reminderDialog = new ReminderDialog();

        // Setting reminder dialog field
        reminderDialog.setField(UPDATE_REMINDER_REQUEST_CODE,reminder);

        try {
            reminderDialog.setDissmissListener(new ReminderDialog.OnDismissListener() {
                @Override
                public void onDismiss(ReminderDialog reminderDialog) {

                    // Updating recycler view with new reminders

                    retrieveReminders();
                    reminderAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(reminderAdapter);
                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        reminderDialog.show(getChildFragmentManager(), "Reminder");
    }
}
