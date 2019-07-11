package com.example.csestudentmate;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText usernameText;
    private TextInputEditText passwordText;
    private Button SignInButton;
    private TextView forgetPassword, createNewAccount;
    private String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_page);

        usernameText = findViewById(R.id.usernameId);
        passwordText = findViewById(R.id.passwordId);
        SignInButton = findViewById(R.id.signInId);
        forgetPassword = findViewById(R.id.forgetPasswordId);
        createNewAccount = findViewById(R.id.createNewAccountId);

        // Disabling Sign in Button at the beginning
        SignInButton.setEnabled(false);
        SignInButton.setBackgroundResource(R.drawable.disable_login_button_theme);
        SignInButton.setTextColor(Color.GRAY);


        SignInButton.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        createNewAccount.setOnClickListener(this);

        // Checking username is empty or not after changing focus
        usernameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                username = usernameText.getText().toString().trim();
                if(!hasFocus){
                    if(username.isEmpty()){
                        usernameText.setError("Username is required");
                    }
                }
            }
        });

        // Checking password is empty or not after changing focus
        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password = passwordText.getText().toString().trim();
                if(!hasFocus){
                    if(password.isEmpty()){
                        passwordText.setError("Password is required");
                    }
                }
            }
        });

        usernameText.addTextChangedListener(textWatcher);

        passwordText.addTextChangedListener(textWatcher);
    }

    // Checking the editText box in every single text change
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int maxLength = 50;
            username = usernameText.getText().toString().trim();
            password = passwordText.getText().toString().trim();

            // Button Disable and Enable feature

            boolean confirmation = !username.isEmpty() && !password.isEmpty();

            if(confirmation){
                SignInButton.setEnabled(true);
                SignInButton.setBackgroundResource(R.drawable.login_button_theme);
                SignInButton.setTextColor(Color.WHITE);
            }else{
                SignInButton.setEnabled(false);
                SignInButton.setBackgroundResource(R.drawable.disable_login_button_theme);
                SignInButton.setTextColor(Color.GRAY);
            }

            // Checking weather max length is crossing or not
            if(username.length() == maxLength){
                usernameText.setError("Not more than "+ maxLength + " character");
            }if(password.length() == maxLength){
                passwordText.setError("Not more than "+ maxLength + " character");
            }

            usernameText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
            passwordText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        }

        @Override
        public void afterTextChanged(Editable s) {
            //
        }
    };

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signInId){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else if(v.getId() == R.id.forgetPasswordId){
            Toast.makeText(getApplicationContext(), "Forget Password", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.createNewAccountId){
            Intent intent = new Intent(this, RegistrationPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
