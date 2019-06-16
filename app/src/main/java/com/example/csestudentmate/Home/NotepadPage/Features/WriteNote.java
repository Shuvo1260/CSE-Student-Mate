package com.example.csestudentmate.Home.NotepadPage.Features;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.csestudentmate.R;

public class WriteNote extends AppCompatActivity {

    private EditText titleEditText, descriptionEditText;
    private String title, description, toolbarName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        titleEditText = findViewById(R.id.titleId);
        descriptionEditText = findViewById(R.id.descriptionId);

        Bundle bundle = getIntent().getExtras();

        toolbarName = bundle.getString("toolbarName").toString();

        setActionBar(toolbarName);


        if(toolbarName.trim().matches("Edit Note")){
            title = bundle.getString("title", "").toString();
            description = bundle.getString("description", "").toString();
            titleEditText.setText(title);
            descriptionEditText.setText(description);
        }

    }

    private void setActionBar(String toolbarName){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.theme));
        getSupportActionBar().setTitle(this.toolbarName);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
