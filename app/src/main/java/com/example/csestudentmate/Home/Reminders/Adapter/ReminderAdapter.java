package com.example.csestudentmate.Home.Reminders.Adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csestudentmate.Home.Reminders.Features.Reminder;
import com.example.csestudentmate.Home.Reminders.Features.ReminderDialog;
import com.example.csestudentmate.Home.Reminders.Features.TimeDateFormatter;
import com.example.csestudentmate.R;

import java.util.ArrayList;
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
        void onItemClick(int position);
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

        activationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(fragmentActivity.getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
            }
        });



//
//        // Determining the operation that have to be taken into reminder
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!isChecked.get(i) && !anyItemChecked){
//                    // Calling update method
//                    reminderList.set(i, updateReminder(reminderList.get(i)));
//                    notifyDataSetChanged();
//                }
//                else if(isChecked.get(i) && anyItemChecked){
//                    // Restoring the view of unchecked item
//                    cardView.setCardBackgroundColor(Color.WHITE);
//                    isChecked.set(i, false);
//                }else if(anyItemChecked){
//                    // Checked item view creationg
//                    cardView.setCardBackgroundColor(fragmentActivity.getColor(R.color.noteSelectionColor));
//                    isChecked.set(i, true);
//                }
//
//                // Finding any item is checked or not
//                for(int index = 0; index < reminderList.size(); index++){
//                    if(isChecked.get(index)){
//                        anyItemChecked = true;
//                        break;
//                    }else{
//                        anyItemChecked = false;
//                    }
//                }
//
//                // If any item mark as selected then the floating action button will work as a delete button
//                if(!anyItemChecked){
//                    floatingActionButton.setImageDrawable(fragmentActivity.getDrawable(R.drawable.ic_add_white));
//                }
//            }
//        });
//
//        // Long pressing clicker for select the reminder item
//        cardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if(!anyItemChecked){
//                    // Changing the selected item view
//                    cardView.setCardBackgroundColor(fragmentActivity.getColor(R.color.noteSelectionColor));
//                    isChecked.set(i, true);
//                    anyItemChecked = true;
//
//                    // Converting the floating button as delete button
//                    floatingActionButton.setImageDrawable(fragmentActivity.getDrawable(R.drawable.ic_delete_white));
//                }
//
//                return true;
//            }
//        });
    }

    // Counting total item
    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    // Adapter view holder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onReminderClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onReminderClickListener.onItemClick(position);
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

    // Building the checker
    public void isCheckedBuild(List<Boolean> isChecked){
        this.isChecked = isChecked;
    }

    private Reminder updateReminder(final Reminder reminder){
        // Creating reminder dialog to get informations
        ReminderDialog reminderDialog = new ReminderDialog();

        reminderDialog.setField(2,reminder);

        try {
            reminderDialog.setDissmissListener(new ReminderDialog.OnDismissListener() {
                @Override
                public Reminder onDismiss(ReminderDialog reminderDialog) {

                    // Updating recycler view with new reminders
                    return reminderDialog.getField();
                }
            });
        }catch (Exception e){
            Toast.makeText(fragmentActivity.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        reminderDialog.show(fragmentActivity.getSupportFragmentManager(), "Reminder");
        return reminder;
    }
}

