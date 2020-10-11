package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        Button button= (Button)findViewById(R.id.button);
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

    public void clickHandler(View view){

        Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);

        startActivityForResult(intent,10);

    }

    public void onActivityResult(int requestCode,int responseCode,Intent data){
        super.onActivityResult(requestCode,responseCode,data);
        if (requestCode==0){
            Log.i(ACTIVITY_NAME,"Returned to MainActivity.onActivityResult");
        }
        if(responseCode== Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            Toast toast = Toast.makeText(MainActivity.this ,messagePassed, Toast.LENGTH_LONG); //this is the ListActivity
            toast.show(); //display your message box
        }

    }
}