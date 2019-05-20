package com.example.csestudentmate;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.regex.Pattern;

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

        // Disabling Sign up Button at the beginning
        signUpButton.setEnabled(false);
        signUpButton.setBackgroundResource(R.drawable.disable_login_button_theme);

        fullnameText.setOnFocusChangeListener(this);
        usernameText.setOnFocusChangeListener(this);
        emailText.setOnFocusChangeListener(this);
        contactNoText.setOnFocusChangeListener(this);
        passwordText.setOnFocusChangeListener(this);
        confirmPasswordText.setOnFocusChangeListener(this);

        fullnameText.addTextChangedListener(textWatcher);
        usernameText.addTextChangedListener(textWatcher);
        emailText.addTextChangedListener(textWatcher);
        contactNoText.addTextChangedListener(textWatcher);
        passwordText.addTextChangedListener(textWatcher);
        confirmPasswordText.addTextChangedListener(textWatcher);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Registration Complete", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();

        if(id == R.id.fullnameId && !hasFocus){
            fullname = fullnameText.getText().toString().trim();
            if(fullname.isEmpty()){
                fullnameText.setError("Enter your full name");
            }else if(fullname.length() < 3){
                fullnameText.setError("Enter a valid name");
            }
        }else if(id == R.id.registerUsernameId && !hasFocus){
            username = usernameText.getText().toString().trim();
            if(username.isEmpty()){
                usernameText.setError("Enter a username");
            }else if(username.length() < 5){
                usernameText.setError("At least 4 character");
            }
        }else if(id == R.id.emailId && !hasFocus){
            email = emailText.getText().toString().trim();
//            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if(email.isEmpty()){
                emailText.setError("Enter your email id");
            }else if(isValidEmailId(email)){
                emailText.setError("Enter a valid email");
            }
        }else if(id == R.id.contactNoId && !hasFocus){
            contactNo = contactNoText.getText().toString().trim();
            if(contactNo.isEmpty()){
                contactNoText.setError("Enter your contact no");
            }else if(contactNo.length() < 11){
                contactNoText.setError("Enter a valid contact no");
            }
        }else if(id == R.id.registerPasswordId && !hasFocus){
            password = passwordText.getText().toString().trim();
            if(password.isEmpty()){
                passwordText.setError("Enter a password");
            }else if(password.length() < 6){
                passwordText.setError("At least 6 character");
            }
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int maxLength = 50;

            fullname = fullnameText.getText().toString().trim();
            username = usernameText.getText().toString().trim();
            email = emailText.getText().toString().trim();
            contactNo = contactNoText.getText().toString().trim();
            password = passwordText.getText().toString().trim();
            confirmPassword = confirmPasswordText.getText().toString().trim();

            // Button Disable and Enable feature
            boolean confirmation = !fullname.isEmpty() && !username.isEmpty() && !email.isEmpty() && !contactNo.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty();

            if(confirmation){
                signUpButton.setEnabled(true);
                signUpButton.setBackgroundResource(R.drawable.login_button_theme);
            }else{
                signUpButton.setEnabled(false);
                signUpButton.setBackgroundResource(R.drawable.disable_login_button_theme);
            }

            // Checking weather max length is crossing or not
            if(fullname.length() == maxLength){
                fullnameText.setError("Not more than "+ maxLength + " character");
            }if(username.length() == maxLength){
                usernameText.setError("Not more than "+ maxLength + " character");
            }if(email.length() == maxLength){
                emailText.setError("Not more than "+ maxLength + " character");
            }if(contactNo.length() == maxLength){
                contactNoText.setError("Not more than "+ maxLength + " character");
            }if(password.length() == maxLength){
                passwordText.setError("Not more than "+ maxLength + " character");
            }if(confirmPassword.length() == maxLength){
                confirmPasswordText.setError("Not more than "+ maxLength + " character");
            }

            fullnameText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
            usernameText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
            emailText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
            contactNoText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
            passwordText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
            confirmPasswordText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        }

        @Override
        public void afterTextChanged(Editable s) {
            //
        }
    };

    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
}
