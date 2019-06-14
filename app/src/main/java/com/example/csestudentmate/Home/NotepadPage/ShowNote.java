package com.example.csestudentmate.Home.NotepadPage;

<<<<<<< HEAD:app/src/main/java/com/example/csestudentmate/Home/NotepadPage/ShowNote.java
import android.content.Intent;
=======
>>>>>>> parent of 3b335c5... WriteNote Activity designed and connected with DailyNotes and ShowNote.:app/src/main/java/com/example/csestudentmate/ShowNote.java
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
    private TextView titleTextView, descriptionTextView;
    private String title, description;
    private FloatingActionButton editNoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.theme));
        getSupportActionBar().setTitle("Note");

        titleCardView = findViewById(R.id.showNoteTitleId);

        titleTextView = findViewById(R.id.titleTextId);
        descriptionTextView = findViewById(R.id.descriptionTextId);

        editNoteButton = findViewById(R.id.editNoteId);

        title = getIntent().getStringExtra("title");

        description = getIntent().getStringExtra("description");

        titleTextView.setText(title);
        descriptionTextView.setText(description);

        titleCardView.setOnClickListener(this);
        descriptionTextView.setOnClickListener(this);
        editNoteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.editNoteId || id == R.id.showNoteTitleId || id == R.id.descriptionTextId){
            Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_SHORT).show();
        }
    }
}
