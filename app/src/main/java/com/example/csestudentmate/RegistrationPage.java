package com.example.csestudentmate;

import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.csestudentmate.R;

import java.util.regex.Pattern;

public class RegistrationPage extends AppCompatActivity implements View.OnFocusChangeListener {

    private Button signUpButton;

    private TextInputEditText fullnameText, usernameText, emailText;
    private TextInputEditText contactNoText, passwordText, confirmPasswordText;

    private String fullname, username, email;
    private String contactNo, password, confirmPassword;

    boolean confirmation;

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
        confirmation = true;
        signUpButton.setEnabled(false);
        signUpButton.setBackgroundResource(R.drawable.disable_login_button_theme);
        signUpButton.setTextColor(Color.GRAY);

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
                fullnameText.setError("Full name is required");
            }else if(fullname.length() < 3){
                fullnameText.setError("Invalid name");
            }
        }else if(id == R.id.registerUsernameId && !hasFocus){
            username = usernameText.getText().toString().trim();
            if(username.isEmpty()){
                usernameText.setError("Username is required");
            }else if(username.length() < 5){
                usernameText.setError("At least 4 character");
            }
        }else if(id == R.id.emailId && !hasFocus){
            email = emailText.getText().toString().trim();

            if(email.isEmpty()){
                emailText.setError("Email is required");
            }else if(!isValidEmailId(email)){
                emailText.setError("Invalid email");
            }
        }else if(id == R.id.contactNoId && !hasFocus){
            contactNo = contactNoText.getText().toString().trim();
            if(contactNo.isEmpty()){
                contactNoText.setError("Contact no is required");
            }else if(contactNo.length() < 11){
                contactNoText.setError("Invalid contact no");
            }
        }else if(id == R.id.registerPasswordId && !hasFocus){
            password = passwordText.getText().toString().trim();
            if(password.isEmpty()){
                passwordText.setError("Password is required");
            }else if(password.length() < 6){
                passwordText.setError("At least 6 character");
            }
        }else if(id == R.id.confirmPasswordId && !hasFocus){
            confirmPassword = confirmPasswordText.getText().toString().trim();
            if(confirmPassword.isEmpty()){
                confirmPasswordText.setError("Reenter password");
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

            fullname = fullnameText.getText().toString().trim();
            username = usernameText.getText().toString().trim();
            email = emailText.getText().toString().trim();
            contactNo = contactNoText.getText().toString().trim();
            password = passwordText.getText().toString().trim();
            confirmPassword = confirmPasswordText.getText().toString().trim();


            // Checking whether there are any error or not
            confirmation = !fullname.isEmpty() && !username.isEmpty() && !email.isEmpty() && !contactNo.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty();
            confirmation = confirmation && !(fullname.length() < 3) && !(username.length() < 5);
            confirmation = confirmation && !(contactNo.length() < 11) && !(password.length() < 6) && password.matches(confirmPassword);
            confirmation = confirmation && isValidEmailId(email);

            // Button Disable and Enable feature
            if(confirmation){
                signUpButton.setEnabled(true);
                signUpButton.setBackgroundResource(R.drawable.login_button_theme);
                signUpButton.setTextColor(Color.WHITE);
            }else{
                signUpButton.setEnabled(false);
                signUpButton.setBackgroundResource(R.drawable.disable_login_button_theme);
                signUpButton.setTextColor(Color.GRAY);
            }

            // Checking whether max length is crossing or not
            maxLengthChecker(50);

        }

        @Override
        public void afterTextChanged(Editable s) {
            //
        }
    };

    //This method checks whether any field empty or not
    private void maxLengthChecker(int maxLength) {
        String maxLenErrorMessage = "Not more than "+ maxLength + " character";

        if(fullname.length() == maxLength){
            fullnameText.setError(maxLenErrorMessage);
        }if(username.length() == maxLength){
            usernameText.setError(maxLenErrorMessage);
        }if(email.length() == maxLength){
            emailText.setError(maxLenErrorMessage);
        }if(contactNo.length() == maxLength){
            contactNoText.setError(maxLenErrorMessage);
        }if(password.length() == maxLength){
            passwordText.setError(maxLenErrorMessage);
        }if(confirmPassword.length() == maxLength){
            confirmPasswordText.setError(maxLenErrorMessage);
        }

        fullnameText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        usernameText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        emailText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        contactNoText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        passwordText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        confirmPasswordText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
    }

    private boolean isValidEmailId(String email){
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}
