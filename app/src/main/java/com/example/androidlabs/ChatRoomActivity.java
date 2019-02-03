package com.example.androidlabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.AdapterView;
import android.util.Log;
import android.widget.TextView;
import android.content.Context;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    //create an arraylist that stores Messages object
    //Messages object contains a String for the message and a type for whether its send or receive
    final ArrayList<Messages> messageList = new ArrayList<Messages>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        setTitle("ChatRoom");

        //call all four elements from the activity_chatroom xml file
        ListView listView = findViewById(R.id.messages_view);
        Button send = findViewById(R.id.SendButton);
        EditText editText = findViewById(R.id.editText);
        Button receive = findViewById(R.id.ReceiveButton);


        //This class needs 4 functions to work properly:
        //ArrayAdapter -- look it up
        //this adapater is being dropped inside the Listview
        class ChatRoomAdapter extends ArrayAdapter<String> {

            //create constructor with the parameter Context - look it up
            public ChatRoomAdapter(Context context) {
                super(context, 0);
            }

            @Override
            public int getCount() {
                //return the <arrayname>.size to get the size of the array
                return messageList.size();
            }

            @Override
            //String is from your array of message objects
            public String getItem(int position) {
                //goes to an element of the array in a specific position
                //returns the message at that position
                return messageList.get(position).message;
            }

            public int getItemType(int position) {
                //goes to an element of the array in a specific position
                //returns the type at that position
                return messageList.get(position).type;
            }

            @Override
            public long getItemId(int position) {
                //goes to an element of the array and finds the position
                //keeps track of the position for SQL purposes
                return position;
            }

            @Override
            //where the complexity goes - what is going to happen in the screen
            public View getView(int position, View convertView, ViewGroup parent) {
                //this is where you call the layout inflators (chatroom_send, chatroom_receive)
                LayoutInflater inflator = ChatRoomActivity.this.getLayoutInflater();
                //create a placeholder for the view that is going to be inflated
                View result = null;
                //find out what type is in that position
                if (getItemType(position) == 1) {
                    //if type is 1, then call the applicable xml file to send a message
                    result = inflator.inflate(R.layout.chatroom_send, null);
                } else {
                    result = inflator.inflate(R.layout.chatroom_receive, null);
                }
                //finding the textview of the inflated xml files
                TextView displayMessage = result.findViewById(R.id.chatTextView);
                //putting that text in the correct position
                displayMessage.setText(getItem(position));

                return result;
            }
        }
        //instantiate ChatRoomAdapter
        ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter(this);

        //setAdapter is one of the functions in ListView
        //take all elements and make them do something
        listView.setAdapter(chatRoomAdapter);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                messageList.add(new Messages(editText.getText().toString(),1 ));
                //this tells the adapter that something changed, and to update
                chatRoomAdapter.notifyDataSetChanged();
                //this erases the text that was in the text field
                editText.setText("");
            }

        });
        receive.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                messageList.add(new Messages(editText.getText().toString(),0 ));
                //this tells the adapter that something changed, and to update
                chatRoomAdapter.notifyDataSetChanged();
                //this erases the text that was in the text field
                editText.setText("");

            }
        });

    }
}