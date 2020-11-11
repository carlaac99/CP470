package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
    ChatAdapter messageAdapter;
    private ChatDatabaseHelper dbOpen;
    private SQLiteDatabase db;
    private String[] columns = {ChatDatabaseHelper.KEY_MESSAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        chat= findViewById(R.id.chatView);

        message= findViewById(R.id.sendText);
        sendButton =findViewById(R.id.sendButton);

        messageAdapter= new ChatAdapter(this);
        chat.setAdapter(messageAdapter);



        dbOpen= new ChatDatabaseHelper(this);

        db = dbOpen.getWritableDatabase();

        Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME,
                columns, null, null,
                null, null, null);

        cursor.moveToFirst();
        while( !cursor.isAfterLast() ) {
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            messages.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));

            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
            cursor.moveToNext();

        }



        for(int columnIndex=0; columnIndex< cursor.getColumnCount(); columnIndex++ ){
            Log.i(ACTIVITY_NAME,cursor.getColumnName(columnIndex));

        }
        cursor.close();






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
    }

}
