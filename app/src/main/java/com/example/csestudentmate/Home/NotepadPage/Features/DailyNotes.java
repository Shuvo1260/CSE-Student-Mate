package com.example.csestudentmate.Home.NotepadPage.Features;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csestudentmate.Home.NotepadPage.Adapter.NotepadViewAdapter;
import com.example.csestudentmate.R;

import java.util.ArrayList;
import java.util.List;

public class DailyNotes extends Fragment {
    private FloatingActionButton addNote;


    private String[] noteTitle;
    private String[] noteSummery;
    private String[] noteDetails;
    private List<Note> noteList = new ArrayList<>();
    private TextView emptyText;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_daily_notes, container, false);

        addNote = view.findViewById(R.id.addNoteId);
        emptyText = view.findViewById(R.id.emptyNoteId);

        final RecyclerView recyclerView;

        recyclerView = view.findViewById(R.id.notePadRecyclerViewId);


        tempMessage();

        emptyChecker();

        final NotepadViewAdapter notepadViewAdapter = new NotepadViewAdapter(getActivity(), noteList, addNote);

        recyclerView.setAdapter(notepadViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notepadViewAdapter.isDeletion()){

                    List<Boolean> isChecked = notepadViewAdapter.getCheckedItem();
                    for(int index = 0; index < noteList.size(); ){
                        if(isChecked.get(index)){
                            noteList.remove(index);
                            isChecked.remove(index);
                        }else{
                            index++;
                        }
                    }
                    notepadViewAdapter.notifyDataSetChanged();
                    addNote.setImageDrawable(getActivity().getDrawable(R.drawable.ic_add_white));
                    recyclerView.setAdapter(notepadViewAdapter);
                    emptyChecker();
                    Snackbar.make(view, "Deleted", Snackbar.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getActivity().getApplicationContext(), WriteNote.class);
                    intent.putExtra("toolbarName", "Write Note");
                    startActivity(intent);
                }
            }
        });

        return view;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//    }

    public void emptyChecker(){
        if(!noteList.isEmpty()){
            emptyText.setVisibility(View.GONE);
        }else{
            emptyText.setVisibility(View.VISIBLE);
        }
    }
    public void tempMessage(){
        noteTitle = new String[5];
        noteSummery = new String[5];

        noteTitle[0] = "Shuvo";
        noteTitle[1] = "Habiba";
        noteTitle[2] = "Faiza";
        noteTitle[3] = "Yasfa";
        noteTitle[4] = "yohahaha";

        noteSummery[0] = "alfsdkjlkfa dsafejdaslkjfsaldk;jfdlaskjdfksllkfdslkjdfsjlk\n" + "dskdfs\nlskdfj\nsdfklj\nsdflkjsd\nfdslkjd\nfd\nfd\nfd\nfd\nfds" +
                "ldskjdfdlkdfsjlkdfsaj\nsdfl\nfdlk\ndsflkj\ndsfklj\ndsflkj\nlast\ndsflkj\nlast\ndsflkj\nlast\ndsflkj\nlast\ndsflkj\nlast\ndsflkj\nlast";
        noteSummery[1] = "Habiba is a good girl";
        noteSummery[2] = "Faiza is a good girl";
        noteSummery[3] = "Yasfa is a good girl";
        noteSummery[4] = "Yo is a goodfdskjlaj\nfdslk\nfdslkj\nfdsklj\ndsflkj";

        Note note;
        for(int counter = 0; counter < 5; counter++){
            note = new Note(Integer.toString(counter+1), noteTitle[counter], noteSummery[counter]);
            noteList.add(note);
        }
    }
}
