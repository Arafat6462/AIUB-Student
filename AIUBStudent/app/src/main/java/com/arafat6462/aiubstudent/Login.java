package com.arafat6462.aiubstudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
                    if (id.length() != 10 || id.charAt(2) != '-' || id.charAt(8) != '-') {
                        userName.setError("Valid User ID Required");
                        userName.requestFocus();// courser will blink here
                        return;
                    }

                    if (TextUtils.isEmpty(pass)) {
                        password.setError("Password Required");
                        password.requestFocus(); // courser will blink here
                        return;
                    }



                // checking internet connection
                if (!isConnected(getApplicationContext())) {
                    //  Log.d("new5", "is connecded false ");
                    showCustomDialog();
                } else {

               // checking internet connection


                    //  pass id password for parse data
                    // ParseDataFromPortal parseDataFromPortal = new ParseDataFromPortal(id,pass);
                    // Log.d("new1", "call parse data:");
                    // openMainActivity();

                    new ParseDataFromPortal(id, pass, Login.this).execute(); // send activity also for start new activity form data class
                }
            }
        });
    }


    ///////.......... internet connection .............///////////
    private boolean isConnected(Context context) {

        // Log.d("new5", "is connecded   ");

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }
    ///////.......... internet connection .............///////////


    private void showCustomDialog() {


        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Login.this, R.style.NoInternetTheme);
        View view = LayoutInflater.from(Login.this).inflate(R.layout.network_error, (ConstraintLayout) findViewById(R.id.layout_network_error));
        builder.setView(view); // set the view
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //////............................ go to network setting .................////////////

        view.findViewById(R.id.connect_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
             }
        });
        //////............................ option check .................////////////


//        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
//        builder.setMessage("please connect to the internet").setCancelable(false).setPositiveButton("connect", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//            }
//        })
//                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                //startActivity(new Intent(getApplicationContext(),Login.class));
//                finish();
//            }
//        });

    }










    // open main activity from login page
//    public void openMainActivity() {
//        Log.d("new1","main activity call:");
//
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//     }
}