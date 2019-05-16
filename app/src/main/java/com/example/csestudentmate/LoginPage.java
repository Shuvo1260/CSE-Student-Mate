package com.example.csestudentmate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity implements View.OnClickListener {

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


        SignInButton.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        createNewAccount.setOnClickListener(this);

        usernameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                username = usernameText.getText().toString();
                if(!hasFocus){
                    if(username.isEmpty()){
                        usernameText.setError("Please enter your username");
                    }
                }
            }
        });

        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                password = passwordText.getText().toString();
                if(!hasFocus){
                    if(password.isEmpty()){
                        passwordText.setError("Please enter your password");
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.signInId){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if(v.getId() == R.id.forgetPasswordId){
            Toast.makeText(getApplicationContext(), "Forget Password", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.createNewAccountId){
            Intent intent = new Intent(this, RegistrationPage.class);
            startActivity(intent);
        }
    }
}
