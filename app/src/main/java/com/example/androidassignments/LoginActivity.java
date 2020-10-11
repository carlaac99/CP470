package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "LoginActivity";
    private Object DefaultEmail;
    //private Button button;
     //private EditText ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=shared.edit();
        edit.putString("DefaultEmail","email@domain.com");
        edit.commit();

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
    public void saveEmail(View view){
        SharedPreferences shared = getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=shared.edit();

        EditText text= (EditText)findViewById(R.id.login_email);
        edit.putString("userEmail",text.getText().toString());
        edit.commit();

        //Log.i(ACTIVITY_NAME,shared.getString("DefaultEmail", null));



        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }

}