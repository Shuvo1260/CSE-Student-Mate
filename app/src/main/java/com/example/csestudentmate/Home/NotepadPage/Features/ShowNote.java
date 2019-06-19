package com.example.csestudentmate.Home.NotepadPage.Features;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csestudentmate.R;

public class ShowNote extends AppCompatActivity implements View.OnClickListener {

    private CardView titleCardView;
    private TextView titleTextView, noteTextView;
    private String title, note;
    private long id;
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

        Bundle bundle = getIntent().getExtras();

        title = bundle.getString("title");

        note = bundle.getString("note");
        id = bundle.getLong("id");

        titleTextView.setText(title);
        noteTextView.setText(note);

        titleCardView.setOnClickListener(this);
        noteTextView.setOnClickListener(this);
        editNoteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        if(buttonId == R.id.editNoteId ||  buttonId == R.id.showNoteTitleId || buttonId == R.id.noteTextId){
            Intent intent = new Intent(getApplicationContext(), WriteNote.class);
            intent.putExtra("toolbarName", "Edit Note");
            intent.putExtra("id", id);
            intent.putExtra("title", title);
            intent.putExtra("note", note);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                title = data.getStringExtra("title");
                note = data.getStringExtra("note");

                titleTextView.setText(title);
                noteTextView.setText(note);
                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }
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
