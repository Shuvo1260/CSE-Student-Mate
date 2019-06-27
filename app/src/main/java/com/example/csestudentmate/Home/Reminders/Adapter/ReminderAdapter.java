package com.example.csestudentmate.Home.Reminders.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csestudentmate.Home.NotepadPage.Features.ShowNote;
import com.example.csestudentmate.Home.Reminders.Features.Reminder;
import com.example.csestudentmate.R;

import java.util.ArrayList;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private List<Reminder> reminderList;
    private List<Boolean> isChecked;
    private boolean anyItemChecked;
    private FragmentActivity fragmentActivity;
    private FloatingActionButton floatingActionButton;

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

    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reminder_view,viewGroup, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final CardView cardView = viewHolder.cardView;
        final TextView titleView, detailsView, timeView;
        final Switch activationSwitch;

        titleView = cardView.findViewById(R.id.reminderTitleId);
        detailsView = cardView.findViewById(R.id.reminderDetailsId);
        timeView = cardView.findViewById(R.id.reminderTimeId);
        activationSwitch = cardView.findViewById(R.id.reminderActivationButtonId);

        int hour = reminderList.get(i).getHour();
        int minute = reminderList.get(i).getMinute();
        int day = reminderList.get(i).getDay();
        int month = reminderList.get(i).getMonth();
        int year = reminderList.get(i).getYear();

//        String reminderTime = hour + "." + minute + ", " + day + "/" + month + "/" + year;

        String reminderTime = timeBuilder(hour, minute, day, month, year);

        titleView.setText(reminderList.get(i).getTitle());
        detailsView.setText(reminderList.get(i).getDetails());
        timeView.setText(reminderTime);

        activationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isChecked.get(i) && !anyItemChecked){
                    Toast.makeText(fragmentActivity.getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                }
                else if(isChecked.get(i) && anyItemChecked){
                    cardView.setCardBackgroundColor(Color.WHITE);
                    isChecked.set(i, false);
                }else if(anyItemChecked){
                    cardView.setCardBackgroundColor(fragmentActivity.getColor(R.color.noteSelectionColor));
                    isChecked.set(i, true);
                }

                for(int index = 0; index < reminderList.size(); index++){
                    if(isChecked.get(index)){
                        anyItemChecked = true;
                        break;
                    }else{
                        anyItemChecked = false;
                    }
                }

                if(!anyItemChecked){
                    floatingActionButton.setImageDrawable(fragmentActivity.getDrawable(R.drawable.ic_add_white));
                }
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!anyItemChecked){
                    cardView.setCardBackgroundColor(fragmentActivity.getColor(R.color.noteSelectionColor));
                    isChecked.set(i, true);
                    anyItemChecked = true;
                    floatingActionButton.setImageDrawable(fragmentActivity.getDrawable(R.drawable.ic_delete_white));
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    public boolean isDeletion(){
        return anyItemChecked;
    }

    public List<Boolean> getCheckedItem(){
        anyItemChecked = false;
        return isChecked;
    }

    private String timeBuilder(int hour, int minute, int day, int month, int year){
        String time = "";
        if(hour < 10){
            time+= "0" + hour;
        }else
            time+= hour;
        time+= ":";
        if(minute < 10)
            time+= "0" + minute;
        else
            time+= minute;
        time += ", ";

        if(day < 10)
            time += "0" + day;
        else
            time += day;

        time += "/";

        if(month < 10)
            time += "0" + month;
        else
            time += month;

        time += "/" + year;

        return time;
    }

    public void isCheckedBuild(List<Boolean> isChecked){
        this.isChecked = isChecked;
    }
}
