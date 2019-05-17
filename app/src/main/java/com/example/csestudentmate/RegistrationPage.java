package com.example.csestudentmate;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class RegistrationPage extends AppCompatActivity implements View.OnFocusChangeListener {

    private Button signUpButton;

    private TextInputEditText fullnameText, usernameText, emailText;
    private TextInputEditText contactNoText, passwordText, confirmPasswordText;

    private String fullname, username, email;
    private String contactNo, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration_page);

        signUpButton = findViewById(R.id.signUpId);

        fullnameText = findViewById(R.id.fullnameId);
        usernameText = findViewById(R.id.registerUsernameId);
        emailText = findViewById(R.id.emailId);
        contactNoText = findViewById(R.id.contactNoId);
        passwordText = findViewById(R.id.registerPasswordId);
        confirmPasswordText = findViewById(R.id.confirmPasswordId);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //
    }
}
