package com.example.csestudentmate.Home.NotepadPage.Features;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.csestudentmate.R;

public class WriteNote extends AppCompatActivity {

    private EditText titleEditText, noteEditText;
    private String title, note, toolbarName;
    private FloatingActionButton saveButton;
    private String dialogeTitle, dialogeMessage;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        titleEditText = findViewById(R.id.titleId);
        noteEditText = findViewById(R.id.noteId);
        saveButton = findViewById(R.id.saveButtonId);
        saveButton.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();

        toolbarName = bundle.getString("toolbarName").toString();

        setActionBar(toolbarName);


        if(toolbarName.trim().matches("Edit Note")){
            title = bundle.getString("title", "").toString();
            note = bundle.getString("note", "").toString();
            titleEditText.setText(title);
            noteEditText.setText(note);
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleEditText.getText().toString();
                note = noteEditText.getText().toString();

                Intent intent = new Intent();

                intent.putExtra("title", title);
                intent.putExtra("note", note);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        titleEditText.addTextChangedListener(textWatcher);
        noteEditText.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(toolbarName.trim().matches("Edit Note")){
                if(title.matches(titleEditText.getText().toString()) && note.matches(noteEditText.getText().toString())){
                    saveButton.setVisibility(View.GONE);
                }else{
                    saveButton.setVisibility(View.VISIBLE);
                }
            }else{
                title = titleEditText.getText().toString();
                note = noteEditText.getText().toString();
                if(title.isEmpty() && note.isEmpty()){
                    saveButton.setVisibility(View.GONE);
                }else{
                    saveButton.setVisibility(View.VISIBLE);
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {
            //
        }
    };

    private void setActionBar(String toolbarName){
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.theme));
        getSupportActionBar().setTitle(this.toolbarName);
    }

    @Override
    public boolean onSupportNavigateUp() {

        setBuilder();

        alertDialog = builder.create();


        if(toolbarName.trim().matches("Edit Note")){
            if(!title.matches(titleEditText.getText().toString()) || !note.matches(noteEditText.getText().toString())){
                alertDialog.show();
            }else{
                finish();
            }
        }else{
            if(!titleEditText.getText().toString().isEmpty() || !noteEditText.getText().toString().isEmpty()) {
                alertDialog.show();
            }else{
                finish();
            }
        }


        return super.onSupportNavigateUp();
    }

    public void setBuilder(){
        builder = new AlertDialog.Builder(this);

        dialogeTitle = "Exit";
        dialogeMessage = "Do you want to exit without saving?";
        builder.setMessage(dialogeMessage).setTitle(dialogeTitle);
        builder.setIcon(R.drawable.warning);
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    }
    @Override
    public void onBackPressed() {

        setBuilder();
        alertDialog = builder.create();


        if(toolbarName.trim().matches("Edit Note")){
            if(!title.matches(titleEditText.getText().toString()) || !note.matches(noteEditText.getText().toString())){
                alertDialog.show();
            }else{
                finish();
            }
        }else{
            if(!titleEditText.getText().toString().isEmpty() || !noteEditText.getText().toString().isEmpty()) {
                alertDialog.show();
            }else{
                finish();
            }
        }
    }
}
