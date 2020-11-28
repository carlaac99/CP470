package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MessageDetails extends AppCompatActivity {
    Bundle bundle;
    MessageFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        bundle = getIntent().getExtras().getBundle(ChatWindow.DETAILS);

        MessageFragment fragment = new MessageFragment();
        //fragment = (MessageFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMessage);
        //Button deleteMessage = (Button) findViewById(R.id.DeleteMessage);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //fragment.setArguments(bundle);
        Log.i("messageDetails","in oncreate()");
        ft.replace(R.id.emptyMessage, fragment);
        ft.commit();




    }
}




