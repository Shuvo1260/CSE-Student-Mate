package com.example.csestudentmate;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DailyNotes extends Fragment {
    private FloatingActionButton addNote;


    private String[] noteTitle;
    private String[] noteSummery;
    private String[] noteDetails;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_daily_notes, container, false);

        addNote = view.findViewById(R.id.addNoteId);


        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(noteTitle, noteSummery);


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Add new node", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
