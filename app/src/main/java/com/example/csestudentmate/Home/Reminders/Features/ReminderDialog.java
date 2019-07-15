package com.example.csestudentmate.Home.Reminders.Features;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.csestudentmate.Home.Reminders.Database.ReminderDatabaseQuery;
import com.example.csestudentmate.R;

import java.util.Calendar;

public class ReminderDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private TextView ok, cancel;
    private TextView date, time, name, details, dialogTitle;
    private View dialogView;

    private String NameText;
    private String DetailsText;
    private int Hour;
    private int Minute;
    private int Day;
    private int Month;
    private int Year;
    private long ReminderId;
    private int activated;

    private Reminder oldReminder;
    private CardView cardView;

    private String dialogTime, dialogDate;
    private LinearLayout nameLayout, detailsLayout, timeLayout, dateLayout;
    private int requestCode;
    private OnDismissListener onDismissListener;
    private TimeDateFormatter timeDateFormatter = new TimeDateFormatter();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        dialogView = layoutInflater.inflate(R.layout.add_update_reminder, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView);

        setFindViewById();

        setOnClickListenerAtView();

        setDialogField();

        return builder.create();
    }

    // Receiving values from activity
    public void setField(int requestCode, String NameText, String DetailsText, String dialogTime, String dialogDate){
        // Setting the add reminder dialog information
        this.requestCode = requestCode;
        this.NameText = NameText;
        this.DetailsText = DetailsText;
        this.dialogTime = dialogTime;
        this.dialogDate = dialogDate;
        this.activated = 1;

        final Calendar calendar = Calendar.getInstance();

        Hour = calendar.get(calendar.HOUR_OF_DAY);
        Minute = calendar.get(calendar.MINUTE);
        Day = calendar.get(calendar.DAY_OF_MONTH);
        Month = calendar.get(calendar.MONTH);
        Year = calendar.get(calendar.YEAR);
    }
    public void setField(int requestCode, Reminder reminder){
        // Setting the update reminder dialog information
        this.requestCode = requestCode;
        this.NameText = reminder.getTitle();
        this.DetailsText = reminder.getDetails();

        this.oldReminder = reminder;

        this.ReminderId = reminder.getId();
        this.Hour = reminder.getHour();
        this.Minute = reminder.getMinute();
        this.Day = reminder.getDay();
        this.Month = reminder.getMonth();
        this.Year = reminder.getYear();
        this.activated = reminder.getActivated();

        this.dialogTime = timeDateFormatter.setTimeFormat(Hour, Minute);
        this.dialogDate = timeDateFormatter.setDateFormat(Day, Month, Year);
    }

    @Override
    public void onClick(View v) {

        final AlertDialog.Builder editTextDialogBuilder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.edit_text_dialog_view, null);
        final EditText editText = view.findViewById(R.id.editTextId);
        final TextView editTextTitle, editTextOk, editTextCancel;
        editTextTitle = view.findViewById(R.id.editTextTitleId);
        editTextOk = view.findViewById(R.id.editTextOkId);
        editTextCancel = view.findViewById(R.id.editTextCancelId);
        editTextDialogBuilder.setView(view);

        final AlertDialog editTextDialog = editTextDialogBuilder.create();
        editTextDialog.setCanceledOnTouchOutside(false);

        editTextCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDialog.dismiss();
            }
        });

        if(v.getId() == R.id.reminderNameLayoutId){
            editTextTitle.setText("Name");
            if(!name.getText().toString().matches("None"))
                editText.setText(name.getText().toString());
            editTextOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NameText = editText.getText().toString();
                    name.setText(NameText);
                    editTextDialog.dismiss();
                }
            });
            editTextDialog.show();

        }else if(v.getId() == R.id.reminderDetailsLayoutId){
            editTextTitle.setText("Details");
            if(!details.getText().toString().matches("None"))
                editText.setText(details.getText().toString());
            editTextOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsText = editText.getText().toString();
                    details.setText(DetailsText);
                    editTextDialog.dismiss();
                }
            });
            editTextDialog.show();

        }else if(v.getId() == R.id.reminderTimeLayoutId){

            // Opening Time Picker Dialog to get the desire time
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    // Time formating
                    dialogTime = timeDateFormatter.setTimeFormat(hourOfDay, minute);

                    Hour = hourOfDay;
                    Minute = minute;
                    time.setText(dialogTime);

                }
            },Hour, Minute,false);
            timePickerDialog.show();

        }else if(v.getId() == R.id.reminderDateLayoutId){

            // Opening Date Picker Dialog to get the desire date

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    Year = year;
                    Month = month;
                    Day = dayOfMonth;

                    // Date formating
                    dialogDate = timeDateFormatter.setDateFormat(Day, Month, Year);
                    date.setText(dialogDate);
                }
            }, Year, Month, Day);
            datePickerDialog.show();

        }else if(v.getId() == R.id.okId){
            ReminderDatabaseQuery reminderDatabaseQuery = new ReminderDatabaseQuery(getContext());

            if(requestCode == 1) {

                Reminder reminder = new Reminder(NameText, DetailsText, Hour, Minute, Day, Month, Year, 1);
                long id = reminderDatabaseQuery.insert(reminder);
                // Saving data into database
                if (id != -1) {
                    reminder.setId(id);
                    Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Operation Failed", Toast.LENGTH_SHORT).show();
                }

                // Setting alarm on reminder
                ReminderManager reminderManager = new ReminderManager(getContext(),reminder);
                reminderManager.setReminder();
            }else if(requestCode == 2){

                activated = 1;
                Reminder reminder = new Reminder(ReminderId, NameText, DetailsText, Hour, Minute, Day, Month, Year, activated);
                // Updating data into database
                if(reminderDatabaseQuery.update(reminder) != -1){
                    Toast.makeText(getContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Operation Failed", Toast.LENGTH_SHORT).show();
                }
                ReminderManager reminderManager = new ReminderManager(getContext(), reminder);
                reminderManager.setReminder();

            }

        }else if(v.getId() == R.id.cancelId){
            dismiss();
        }
    }

    public Reminder getField() {
        Reminder reminder = new Reminder(ReminderId, NameText, DetailsText, Hour, Minute, Day, Month, Year, activated);
        return reminder;
    }

    // Finding the views id
    private void setFindViewById(){
        ok = dialogView.findViewById(R.id.okId);
        cancel = dialogView.findViewById(R.id.cancelId);

        dialogTitle = dialogView.findViewById(R.id.dialogTitleId);

        name = dialogView.findViewById(R.id.reminderNameId);
        details = dialogView.findViewById(R.id.reminderDetailsId);
        time = dialogView.findViewById(R.id.reminderTimeId);
        date = dialogView.findViewById(R.id.reminderDataId);

        nameLayout = dialogView.findViewById(R.id.reminderNameLayoutId);
        detailsLayout = dialogView.findViewById(R.id.reminderDetailsLayoutId);
        timeLayout = dialogView.findViewById(R.id.reminderTimeLayoutId);
        dateLayout = dialogView.findViewById(R.id.reminderDateLayoutId);
    }

    // On ClickListener setting method
    private void setOnClickListenerAtView(){
        nameLayout.setOnClickListener(this);
        detailsLayout.setOnClickListener(this);
        timeLayout.setOnClickListener(this);
        dateLayout.setOnClickListener(this);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    // Initializing the dialog fields value
    private void setDialogField(){
        if(requestCode == 1) {
            dialogTitle.setText("Add Reminder");
        }else{
            dialogTitle.setText("Update Reminder");
        }

        if(NameText.isEmpty()){
            name.setText("None");
        }else{
            name.setText(NameText);
        }

        if(DetailsText.isEmpty()){
            details.setText("None");
        }else{
            details.setText(DetailsText);
        }

        time.setText(dialogTime);
        date.setText(dialogDate);
    }

    public void setReminderView(CardView cardView){
        this.cardView = cardView;
    }

    // Making Dismiss interface to use in RemindersList fragment

    public interface OnDismissListener{
        void onDismiss(ReminderDialog reminderDialog);
    }

    public void setDissmissListener(OnDismissListener dissmissListener) {
        this.onDismissListener = dissmissListener;
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        if(onDismissListener != null){
            onDismissListener.onDismiss(this);
        }
    }
}
