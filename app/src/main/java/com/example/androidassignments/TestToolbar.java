package com.example.androidassignments;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    String message;
    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "floating button has been clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.menu_main,m);
        return true;

    }
    public boolean onOptionsItemSelected (MenuItem item){
        int id=item.getItemId();
        FloatingActionButton fab = findViewById(R.id.fab);





        switch (id) {
            case (R.id.action_one):
                Log.d("Toolbar", "Option 1 selected");

                if (message==null) {
                    message = "You selected item 1";
                }

                Snackbar.make(fab, message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;

            case(R.id.action_two):
                Log.d("Toolbar", "Option 2 selected");

                Snackbar.make(fab, "You selected item 2", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //builder.setTitle(R.string.pick_color);
                builder.setTitle(R.string.dialog_title2);
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        finish();

                    }
                });

                builder.setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {


                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();



                break;

            case(R.id.action_three):
                Log.d("Toolbar", "Option 3 selected");


                final AlertDialog.Builder m_builder = new AlertDialog.Builder(this);
                // Get the layout inflater
                LayoutInflater inflater = this.getLayoutInflater();

                // Inflate and set the layout for the dialog
                final View v= inflater.inflate(R.layout.custom_dialog, null);
                // Pass null as the parent view because its going in the dialog layout

                m_builder.setView(v)
                        // Add action buttons

                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                               input= v.findViewById(R.id.New_Message);
                               Editable value = input.getText();
                               message=value.toString();
                               //setContentView(R.layout.activity_test_toolbar);






                            }
                        })
                        .setNegativeButton(R.string.CANCEL, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //m_builder.this.getDialog().cancel();
                            }
                        });

                    AlertDialog dialog_m=m_builder.create();
                    dialog_m.show();



                break;

            case (R.id.About):
                Log.d("Toolbar", "About selected");
                Toast toast = Toast.makeText(TestToolbar.this ,"Version 1.0, by Carla Castaneda", Toast.LENGTH_LONG); //this is the ListActivity
                toast.show(); //display your message box
                break;



        }

        //change from True to actual value
        return true;



    }
}