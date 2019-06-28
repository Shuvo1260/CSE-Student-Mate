package com.example.csestudentmate.Home.NotepadPage.Features;

import android.app.Activity;
import android.content.Intent;
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

import com.example.csestudentmate.Home.NotepadPage.Adapter.NotepadViewAdapter;
import com.example.csestudentmate.Home.NotepadPage.Database.NotepadDatabaseQuery;
import com.example.csestudentmate.R;

import java.util.ArrayList;
import java.util.List;

public class DailyNotes extends Fragment {
    private FloatingActionButton addNote;

    private List<Note> noteList = new ArrayList<>();
    private TextView emptyText;
    private int WRITE_NOTE_ACTIVITY_CODE = 1;
    private NotepadViewAdapter notepadViewAdapter;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_daily_notes, container, false);

        addNote = view.findViewById(R.id.addNoteId);
        emptyText = view.findViewById(R.id.emptyNoteId);

        recyclerView = view.findViewById(R.id.notePadRecyclerViewId);

        retrieveNotes();
        emptyChecker();

        notepadViewAdapter = new NotepadViewAdapter(getActivity(), noteList, addNote);
        recyclerView.setAdapter(notepadViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notepadViewAdapter.isDeletion()){
                    final NotepadDatabaseQuery notepadDatabaseQuery = new NotepadDatabaseQuery(getContext());
                    final List<Boolean> isChecked = notepadViewAdapter.getCheckedItem();
                    final List<Boolean> tempChecked = new ArrayList<>();
                    tempChecked.addAll(isChecked);
                    for(int index = 0; index < noteList.size(); ){
                        if(tempChecked.get(index)){
                            noteList.remove(index);
                            tempChecked.remove(index);
                        }else
                            index++;
                    }
                    notepadViewAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(notepadViewAdapter);

                    Snackbar snackbar = Snackbar.make(view, "Successfully Deleted", Snackbar.LENGTH_LONG);

                    snackbar.setAction("Undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            retrieveNotes();
                            notepadViewAdapter.notifyDataSetChanged();
                            for(int index = 0; index < noteList.size(); index++){
                                isChecked.set(index,false);
                            }
                            notepadViewAdapter.isCheckedBuild(isChecked);
                            recyclerView.setAdapter(notepadViewAdapter);
                            emptyChecker();
                        }
                    });

                    snackbar.addCallback(new Snackbar.Callback(){
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);
                            if(event != DISMISS_EVENT_ACTION){
                                retrieveNotes();
                                for(int index = 0; index < noteList.size(); ){
                                    if(isChecked.get(index)){
                                        if(notepadDatabaseQuery.delete(noteList.get(index)) != -1) {
                                            noteList.remove(index);
                                            isChecked.remove(index);
                                        }
                                    }else{
                                        index++;
                                    }
                                }
                            }
                            notepadViewAdapter.isCheckedBuild(isChecked);
                            recyclerView.setAdapter(notepadViewAdapter);
                        }
                    });
                    snackbar.show();

                    addNote.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_white));
                    emptyChecker();
                }else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), WriteNote.class);
                    intent.putExtra("toolbarName", "Write Note");
                    startActivityForResult(intent, WRITE_NOTE_ACTIVITY_CODE);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == WRITE_NOTE_ACTIVITY_CODE){
            if(resultCode == Activity.RESULT_OK){
                long noteId = data.getLongExtra("id", -1);
                String newTitle = data.getStringExtra("title");
                String newNote = data.getStringExtra("note");
                List<Boolean> isChecked = new ArrayList<>();

                Note note = new Note(noteId, newTitle, newNote);
                if(noteList.isEmpty()){
                    noteList.add(note);
                }else {
                    noteList.add(noteList.get(noteList.size() - 1));
                    for (int index = noteList.size() - 1; index > 0; index--) {
                        noteList.set(index, noteList.get(index - 1));
                    }
                    noteList.set(0, note);
                }

                for(int index = 0; index < noteList.size(); index++){
                    isChecked.add(false);
                }

                notepadViewAdapter.isCheckedBuild(isChecked);
                emptyChecker();
                recyclerView.setAdapter(notepadViewAdapter);
                Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        retrieveNotes();
        notepadViewAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(notepadViewAdapter);
    }

    public void emptyChecker(){
        if(!noteList.isEmpty()){
            emptyText.setVisibility(View.GONE);
        }else{
            emptyText.setVisibility(View.VISIBLE);
        }
    }

    public void retrieveNotes(){
        noteList.clear();
        NotepadDatabaseQuery notepadDatabaseQuery = new NotepadDatabaseQuery(getContext());
        noteList.addAll(notepadDatabaseQuery.getAllNotes());
    }
}
