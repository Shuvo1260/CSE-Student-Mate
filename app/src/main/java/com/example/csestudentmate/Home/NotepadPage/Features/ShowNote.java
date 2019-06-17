package com.example.csestudentmate.Home.NotepadPage.Features;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

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

        setActionBar("Note");

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
            Intent intent = new Intent(getApplicationContext(), WriteNote.class);
            intent.putExtra("toolbarName", "Edit Note");
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            startActivity(intent);
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
