package com.arafat6462.aiubstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private  ImageView loginButton;
    private EditText userName,password;
    public static Activity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = this; // for finish this activity from main activity when back press.
        // for full screen, it will hide status bar from login page
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

         loginDetails();

    }

    // use onResume method for clear the id,password text field
    // onResume always call after onCreate and do clear the name field.
    @Override
    protected void onResume() {
        super.onResume();
        userName.setText("");
        password.setText("");
    }

    // checking the login details
    private void loginDetails() {
        loginButton = findViewById(R.id.login_button);
        userName = findViewById(R.id.user_name);
        password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = userName.getText().toString().trim();  // trim remove white space
                String pass = password.getText().toString().trim();

                // checking the blank id & password
                if (id.length() != 10 || id.charAt(2) != '-' || id.charAt(8) != '-'){
                    userName.setError("Valid User ID Required");
                    return;
                }

                if (TextUtils.isEmpty(pass)){
                    password.setError("Password Required");
                    return;
                }

                //  pass id password for parse data
               // ParseDataFromPortal parseDataFromPortal = new ParseDataFromPortal(id,pass);
                new ParseDataFromPortal(id,pass,Login.this).execute(); // send activity also for start new activity form data class
                Log.d("new1","call parse data:");

               // openMainActivity();
            }
        });
    }

    // open main activity from login page
    public void openMainActivity() {
        Log.d("new1","main activity call:");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
     }
}