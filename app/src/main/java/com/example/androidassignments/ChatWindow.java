package com.example.androidassignments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "ChatWindow.java";
    ArrayList<String> messages= new ArrayList<>();
    EditText message;
    ListView chat;
    Button sendButton;
    Boolean FrameExists;
    ChatAdapter messageAdapter;
    FrameLayout frame;
    Cursor cursor;
    int pos;
    private ChatDatabaseHelper dbOpen;
    public static String DETAILS="bundle";
    private SQLiteDatabase db;
    private SQLiteDatabase newdb;
    private String[] columns = {ChatDatabaseHelper.KEY_MESSAGE,ChatDatabaseHelper.KEY_ID};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat_window);
        chat= findViewById(R.id.chatView);

        if(findViewById(R.id.fragmentFrame)!=null){
            FrameExists=true;

        }
        else{
            FrameExists=false;
        }

        message= findViewById(R.id.sendText);
        sendButton =findViewById(R.id.sendButton);

        messageAdapter= new ChatAdapter(this);
        chat.setAdapter(messageAdapter);



        dbOpen= new ChatDatabaseHelper(this);

        db = dbOpen.getWritableDatabase();

        cursor = db.query(ChatDatabaseHelper.TABLE_NAME,
                columns, null, null,
                null, null, null);

        cursor.moveToFirst();
        if(cursor.moveToFirst()) {
            while (cursor != null && !cursor.isAfterLast()) ;{


                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                messages.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

                Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
                cursor.moveToNext();


            }
        }




        for(int columnIndex=0; columnIndex< cursor.getColumnCount(); columnIndex++ ){
            Log.i(ACTIVITY_NAME,"column name "+cursor.getColumnName(columnIndex));

        }
        //cursor.close();


        chat.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                long databaseID=messageAdapter.getItemId(position);
                Bundle bundle = new Bundle();
                bundle.putLong("databaseID",databaseID);
                bundle.putInt("position",position);
                String passMessage=messageAdapter.getItem(position);
                bundle.putString("messageString",passMessage);

                if (FrameExists) {


                    MessageFragment firstFragment = new MessageFragment();
                    firstFragment.setArguments(bundle);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();


                    ft.replace(R.id.fragmentFrame, firstFragment);
                    ft.commit();
                }
                else{

                    Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                    intent.putExtra(DETAILS,bundle);

                    startActivityForResult(intent,10);


                }


                //String value = (String)adapter.getItemAtPosition(position);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==20){
            long databaseID=data.getLongExtra("databaseID",0);
            pos=data.getIntExtra("position",0);

            newdb = dbOpen.getWritableDatabase();


            newdb.delete(ChatDatabaseHelper.TABLE_NAME, ChatDatabaseHelper.KEY_ID + "=" +databaseID, null);
            Log.d(ACTIVITY_NAME, "DELETE MESSAGE: " + databaseID);




            messages.remove(pos);
            messageAdapter.notifyDataSetChanged();



        }


    }

    public void saveMessage(View view){

        messages.add(message.getText().toString());
        messageAdapter.notifyDataSetChanged();


        ContentValues values = new ContentValues();

        values.put(ChatDatabaseHelper.KEY_MESSAGE,message.getText().toString());
        db.insert(ChatDatabaseHelper.TABLE_NAME,"null",values);
        message.setText("");



    }

    @Override
    protected void onDestroy() {
        dbOpen.close();
        cursor.close();
        newdb.close();

        super.onDestroy();

    }


    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx){
            super(ctx,0);
        }
        public int getCount(){

            return messages.size();
        }
        public String getItem(int position){
            return messages.get(position);
        }

        public View getView (int position, View convertView, ViewGroup parent){
            LayoutInflater inflater= ChatWindow.this.getLayoutInflater();
            View result=null;
            if (position%2==0){
                result=inflater.inflate(R.layout.chat_row_incoming,null);

            }else{
                result=inflater.inflate(R.layout.chat_row_outgoing,null);

            }
            TextView message=(TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));

            return result;

        }
        public long getItemId(int position){
            cursor.moveToPosition(position);
            int id =cursor.getInt(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
            Log.i(ACTIVITY_NAME, "Position: " + position +  " Message ID: " + id);

            return id;

        }
    }

}
