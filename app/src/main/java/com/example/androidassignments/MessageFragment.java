package com.example.androidassignments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MessageFragment extends Fragment {

    private long messageID_NUM;
    private String messageString;
    private int messagePosition ;

    public MessageFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        // Inflate the layout for this fragment





        Log.i("Message Fragment", view.toString());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras().getBundle(ChatWindow.DETAILS);
        messageID_NUM= bundle.getLong("databaseID");
        messageString=bundle.getString("messageString");


        Button button = (Button) view.findViewById(R.id.DeleteMessage);
        TextView messageID=view.findViewById(R.id.showIDNum);
        TextView message=view.findViewById(R.id.showMessage);

        messageID.setText(Long.toString(messageID_NUM));
        message.setText(messageString);



        button.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                Intent result= new Intent();
                result.putExtra("databaseID",messageID_NUM);
                //result.putExtra("messageString",messageString);


                //Create the intent to start another activity

                getActivity().setResult(20, result);
                getActivity().finish();
            }
        });
    }
}