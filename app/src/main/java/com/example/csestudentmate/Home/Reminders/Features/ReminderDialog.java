package com.example.csestudentmate.Home.Reminders.Features;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReminderDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private TextView ok, cancel;
    private TextView date, time, name, details, dialogTitle;


    private String NameText;
    private String DetailsText;
    private int Hour;
    private int Minute;
    private int Day;
    private int Month;
    private int Year;

    private String dialogTime, dialogDate;

    private LinearLayout nameLayout, detailsLayout, timeLayout, dateLayout;

    private int requestCode;

    private OnDismissListener onDismissListener;

    private TimeDateFormatter timeDateFormatter = new TimeDateFormatter();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.add_update_reminder, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView);

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

        nameLayout.setOnClickListener(this);
        detailsLayout.setOnClickListener(this);
        timeLayout.setOnClickListener(this);
        dateLayout.setOnClickListener(this);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

        if(requestCode == 1) {
            dialogTitle.setText("Add Reminder");
        }else{
            //
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

        return builder.create();
    }

    public void setField(int requestCode, String NameText, String DetailsText, String dialogTime, String dialogDate){
        // Setting the add reminder dialog information
        this.requestCode = requestCode;
        this.NameText = NameText;
        this.DetailsText = DetailsText;
        this.dialogTime = dialogTime;
        this.dialogDate = dialogDate;

        final Calendar calendar = Calendar.getInstance();

        Hour = calendar.get(calendar.HOUR_OF_DAY);
        Minute = calendar.get(calendar.MINUTE);
        Day = calendar.get(calendar.DAY_OF_MONTH);
        Month = calendar.get(calendar.MONTH);
        Year = calendar.get(calendar.YEAR);
    }
    public void setField(int requestCode, String NameText, String DetailsText, int Hour, int Minute, int Day, int Month, int Year){
        // Setting the update reminder dialog information
        this.requestCode = requestCode;
        this.NameText = NameText;
        this.DetailsText = DetailsText;

        this.Hour = Hour;
        this.Minute = Minute;
        this.Day = Day;
        this.Month = Month;
        this.Year = Year;

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
                    name.setText(editText.getText().toString());
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
                    details.setText(editText.getText().toString());
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

            Reminder reminder = new Reminder(NameText, DetailsText, Hour, Minute, Day, Month, Year);

            // Saving data into database
            ReminderDatabaseQuery reminderDatabaseQuery = new ReminderDatabaseQuery(getContext());
            if( reminderDatabaseQuery.insert(reminder) != -1)
                Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();

            dismiss();
        }else if(v.getId() == R.id.cancelId){
            dismiss();
        }
    }

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
