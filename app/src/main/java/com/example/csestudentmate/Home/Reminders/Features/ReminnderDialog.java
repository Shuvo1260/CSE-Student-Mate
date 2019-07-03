package com.example.csestudentmate.Home.Reminders.Features;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csestudentmate.R;

public class ReminnderDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private TextView ok, cancel;
    private TextView date, time, name, details;

    private LinearLayout nameLayout, detailsLayout, timeLayout, dateLayout;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.add_update_reminder, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView);

        ok = dialogView.findViewById(R.id.okId);
        cancel = dialogView.findViewById(R.id.cancelId);

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

        return builder.create();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.reminderNameLayoutId){
            Toast.makeText(getContext(), "Name", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.reminderDetailsLayoutId){
            Toast.makeText(getContext(), "Details", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.reminderTimeLayoutId){
            Toast.makeText(getContext(), "Time", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.reminderDateLayoutId){
            Toast.makeText(getContext(), "Date", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.okId){
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
            dismiss();
        }else if(v.getId() == R.id.cancelId){
            dismiss();
        }
    }
}
