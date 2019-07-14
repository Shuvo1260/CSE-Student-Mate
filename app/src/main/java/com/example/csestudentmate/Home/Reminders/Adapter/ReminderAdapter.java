package com.example.csestudentmate.Home.Reminders.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csestudentmate.Home.Reminders.Database.ReminderDatabaseQuery;
import com.example.csestudentmate.Home.Reminders.Features.Reminder;
import com.example.csestudentmate.Home.Reminders.Features.ReminderDialog;
import com.example.csestudentmate.Home.Reminders.Features.ReminderManager;
import com.example.csestudentmate.Home.Reminders.Features.TimeDateFormatter;
import com.example.csestudentmate.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private List<Reminder> reminderList;
    private List<Boolean> isChecked;
    private boolean anyItemChecked;
    private FragmentActivity fragmentActivity;
    private FloatingActionButton floatingActionButton;
    private OnReminderClickListener onReminderClickListener;

    // Constructor of the Reminder Adapter
    public ReminderAdapter(FragmentActivity fragmentActivity, List<Reminder> reminderList, FloatingActionButton floatingActionButton){
        this.reminderList = reminderList;
        this.fragmentActivity = fragmentActivity;
        this.floatingActionButton = floatingActionButton;
        anyItemChecked = false;
        isChecked = new ArrayList<>();

        for(int index = 0; index < reminderList.size(); index++){
            isChecked.add(false);
        }
    }

    public interface OnReminderClickListener{
        void onItemClick(final CardView cardView,int position, List<Boolean> isChecked);
    }

    public void setOnReminderClickListener(OnReminderClickListener onReminderClickListener){
        this.onReminderClickListener = onReminderClickListener;
    }
    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Creating the view in cardView from the reminder_view layout
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reminder_view,viewGroup, false);
        return new ViewHolder(cardView);
    }

    // Creating each reminder view
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final CardView cardView = viewHolder.cardView;
        final TextView titleView, detailsView, timeView;
        final Switch activationSwitch;

        titleView = cardView.findViewById(R.id.reminderTitleId);
        detailsView = cardView.findViewById(R.id.reminderDetailsId);
        timeView = cardView.findViewById(R.id.reminderTimeId);
        activationSwitch = cardView.findViewById(R.id.reminderActivationButtonId);

        // Rescuing the time and date
        int hour = reminderList.get(i).getHour();
        int minute = reminderList.get(i).getMinute();
        int day = reminderList.get(i).getDay();
        int month = reminderList.get(i).getMonth();
        int year = reminderList.get(i).getYear();

        // Formating time and date
        TimeDateFormatter timeDateFormatter = new TimeDateFormatter();
        String reminderTime = timeDateFormatter.setTimeFormat(hour, minute) + ", "
                + timeDateFormatter.setDateFormat(day, month, year);

        // Setting the reminder name, details, time and date
        titleView.setText(reminderList.get(i).getTitle());
        detailsView.setText(reminderList.get(i).getDetails());
        timeView.setText(reminderTime);

        // Setting time into calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.HOUR_OF_DAY, hour);
        calendar.set(calendar.MINUTE, minute);
        calendar.set(calendar.DAY_OF_MONTH, day);
        calendar.set(calendar.MONTH, month);
        calendar.set(calendar.YEAR, year);

        // If the reminder activated but time is over then it deactivate the reminder
        if(reminderList.get(i).getActivated() == 1 && calendar.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()){
            reminderList.get(i).setActivated(0);
            // Updating Database to set the reminder is Old
            ReminderDatabaseQuery reminderDatabaseQuery = new ReminderDatabaseQuery(fragmentActivity.getApplicationContext());
            reminderDatabaseQuery.update(reminderList.get(i));
        }

        if(reminderList.get(i).getActivated() == 1){
            Log.d("Code: ", "Activation Code: "+ reminderList.get(i).getActivated());
            activationSwitch.setChecked(true);
        }else {
            activationSwitch.setChecked(false);
        }

        activationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int activated;

                // Setting Reminder Manager
                ReminderManager reminderManager = new ReminderManager(fragmentActivity.getApplicationContext(), reminderList.get(i));

                // Switch operation. Reminder activation or deactivation
                if(activationSwitch.isChecked()){

                    Log.d("Switch", "Switch is checked");
                    activated = 1;

                    // Setting reminder on reminder manager
                    reminderManager.setReminder();
                }else{
                    Log.d("Switch", "Switch is Unchecked");
                    activated = 0;

                    // Canceling reminder from reminder manager
                    reminderManager.cancelReminder();
                }

                reminderList.get(i).setActivated(activated);

                // Updating database
                ReminderDatabaseQuery reminderDatabaseQuery = new ReminderDatabaseQuery(fragmentActivity.getApplicationContext());
                if(reminderDatabaseQuery.update(reminderList.get(i)) != -1){
                } else {
                    Toast.makeText(fragmentActivity.getApplicationContext(), "Operation Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Long pressing clicker for select the reminder item
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!anyItemChecked){
                    // Changing the selected item view
                    cardView.setCardBackgroundColor(fragmentActivity.getColor(R.color.noteSelectionColor));
                    isChecked.set(i, true);
                    anyItemChecked = true;

                    // Converting the floating button as delete button
                    floatingActionButton.setImageDrawable(fragmentActivity.getDrawable(R.drawable.ic_delete_white));
                }

                return true;
            }
        });
    }

    // Counting total item
    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    // Adapter view holder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(final CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onReminderClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onReminderClickListener.onItemClick(cardView, position, isChecked);
                        }
                    }
                }
            });
        }
    }

    // This function determine item is selected or not
    public boolean isDeletion(){
        return anyItemChecked;
    }

    // To know which reminders are selected
    public List<Boolean> getCheckedItem(){
        anyItemChecked = false;
        return isChecked;
    }

    // Any Item is checked or not builder
    public void setAnyItemChecked(boolean anyItemChecked){
        this.anyItemChecked = anyItemChecked;
    }
    // Any Item is checked or not getter
    public boolean getAnyItemChecked(){
        return anyItemChecked;
    }

    // Building the checker
    public void isCheckedBuild(List<Boolean> isChecked){
        this.isChecked = isChecked;
    }

}

