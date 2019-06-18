package com.example.csestudentmate.Home.NotepadPage.Features;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.csestudentmate.R;

public class ShowNote extends AppCompatActivity implements View.OnClickListener {

    private CardView titleCardView;
    private TextView titleTextView, noteTextView;
    private String title, note;
    private FloatingActionButton editNoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        setActionBar("Note");

        titleCardView = findViewById(R.id.showNoteTitleId);

        titleTextView = findViewById(R.id.titleTextId);
        noteTextView = findViewById(R.id.noteTextId);

        editNoteButton = findViewById(R.id.editNoteId);

        title = getIntent().getStringExtra("title");

        note = getIntent().getStringExtra("note");

        titleTextView.setText(title);
        noteTextView.setText(note);

        titleCardView.setOnClickListener(this);
        noteTextView.setOnClickListener(this);
        editNoteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.editNoteId || id == R.id.showNoteTitleId || id == R.id.noteTextId){
            Intent intent = new Intent(getApplicationContext(), WriteNote.class);
            intent.putExtra("toolbarName", "Edit Note");
            intent.putExtra("title", title);
            intent.putExtra("note", note);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setActionBar(String toolbarName){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.theme));
        getSupportActionBar().setTitle(toolbarName);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
