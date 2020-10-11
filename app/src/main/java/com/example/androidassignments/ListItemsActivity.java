package com.example.androidassignments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends AppCompatActivity {
    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        Switch sw = (Switch) findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CharSequence text;
                int duration;

                if (isChecked) {
                    text = "Switch is On";
                    duration = Toast.LENGTH_SHORT;
                }else{
                    text = "Switch is Off";
                    duration = Toast.LENGTH_LONG;
                }

                Toast toast = Toast.makeText(ListItemsActivity.this , text, duration); //this is the ListActivity
                toast.show(); //display your message box
            }
        });

        CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                // 2. Chain together various setter methods to set the dialog characteristics


                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.dialog_message) //Add a dialog message to strings.xml

                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                Intent resultIntent = new Intent(  );
                                resultIntent.putExtra("Response", "Here is my response");
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .show();
            }
        }
        );

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

    public void onClickImage(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton2);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageButton.setImageBitmap(imageBitmap);
        }
    }

    //public Switch s = (Switch) findViewById(R.id.switch1);
    //
//    public void onCheckedChangeListener(CompoundButton buttonView, boolean isChecked) {
//        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
//                Toast.LENGTH_SHORT).show();
//        if(isChecked) {
//                //do stuff when Switch is ON
//        } else {
//            //do stuff when Switch if OFF
//        }
//
//    }
//    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
//
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                CharSequence text = "";
//                int duration;
//                if (isChecked) {
//                    text = "Switch is On";// "Switch is Off"
//                    duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
//
//
//
//                } else {
//
//                    text = "Switch is Off";// "Switch is Off"
//                    duration = Toast.LENGTH_LONG; //= Toast.LENGTH_LONG if Off
//
//                }
//            //Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"), Toast.LENGTH_SHORT).show(); //this is the ListActivity
//            Toast toast=Toast.makeText(ListItemsActivity.this,text,duration);
//            toast.show(); //display your message box
//        }
//    };


//
//
//        if (s.isChecked() == true) {
//            CharSequence text = "Switch is On";
//            int duration = Toast.LENGTH_SHORT;
//            Toast toast = Toast.makeText(this, text, duration); //this is the ListActivity
//            toast.show(); //display your message box
//
//
//        } else {
//            CharSequence text = "Switch is off";
//            int duration = Toast.LENGTH_LONG;
//            Toast toast = Toast.makeText(this, text, duration); //this is the ListActivity
//            toast.show(); //display your message box
//
//        }
//        //CharSequence text = "Switch is On";// "Switch is Off"
//        // int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off




}