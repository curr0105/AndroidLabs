package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.androidlabs.MyDatabaseOpenHelper.COL_ID;
import static com.example.androidlabs.MyDatabaseOpenHelper.COL_MESSAGE;
import static com.example.androidlabs.MyDatabaseOpenHelper.TABLE_NAME;

public class ChatRoomActivity extends AppCompatActivity {

    //create an arraylist that stores the Messages object
    //Messages object contains a String for the message and a type (0 = receive,1 = send)
    private final ArrayList<Messages> messageList = new ArrayList<>();
    private static long id;
    private Cursor data;
    private MyDatabaseOpenHelper myDb;
    private SQLiteDatabase db;
    private static final String TAG = "Database Version";
    private static final String TAG1 = "Number of Columns";
    private static final String TAG2 = "Name of Column";
    private static final String TAG3 = "Number of Rows";
    private static final String TAG4 = "Messages";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        setTitle("Lab 6 - ChatRoom");

        //call all four elements from the activity_chatroom.xml file
        ListView listView = findViewById(R.id.messages_view);
        Button send = findViewById(R.id.SendButton);
        EditText editText = findViewById(R.id.editText);
        Button receive = findViewById(R.id.ReceiveButton);

        //check if the FrameLayout is loaded
        boolean isTablet = findViewById(R.id.fragmentLocation) != null;

        //get a database:
        //to make sure database is created check adb shell --> /data/data/com.example.androidlabs/databases
        myDb = new MyDatabaseOpenHelper(this);
        db = myDb.getWritableDatabase();

        data = db.query(false, TABLE_NAME, null, null,
                null, null, null, null, null);

        while (data.moveToNext()) {

        //isSent method is converting the integer in the database back to true or false
        messageList.add(new Messages(data.getString(data.getColumnIndex(COL_MESSAGE)),isSent(),
                data.getLong(data.getColumnIndex(COL_ID))));
        }
        //every time you use a cursor (called "data" in this example) it needs to be shut down.
        data.close();

        //instantiate ChatRoomAdapter
        ChatRoomAdapter chatRoomAdapter = new ChatRoomAdapter(this);

        //setAdapter is one of the functions in ListView
        //take all elements and make them do something
        listView.setAdapter(chatRoomAdapter);

        printCursor();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageList.add(new Messages(editText.getText().toString(), true, id));
                writeDB(editText.getText().toString(),1);
                //this tells the adapter that something changed, and to update
                chatRoomAdapter.notifyDataSetChanged();
                //this erases the text that was in the text field
                editText.setText("");
                id++;
            }

        });
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageList.add(new Messages(editText.getText().toString(), false, id));
                writeDB(editText.getText().toString(),0);
                //this tells the adapter that something changed, and to update
                chatRoomAdapter.notifyDataSetChanged();
                //this erases the text that was in the text field
                editText.setText("");
                id++;
            }

        });

    }
    //This class needs 4 functions to work properly (use Ctrl + O to see them all):
    //ArrayAdapter -- used by Android to treat a result set uniformly so that it can be
    //displayed in a UI element. It stores the data in a list
    //This adapter is being dropped inside the Listview
    class ChatRoomAdapter extends ArrayAdapter<String> {

        //create constructor with the parameter Context - It tells the compiler to which
        // context activity or application your current belongs to which you want to show.
        private ChatRoomAdapter(Context context) {
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

        private boolean getItemType(int position) {
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
        //what is going to happen in the screen
        public View getView(int position, View convertView, ViewGroup parent) {
            //this is where you call the layout inflaters (chatroom_send, chatroom_receive)
            //When you use a custom view in a ListView you must define the row layout.\
            // You create an xml where you place android widgets and then in the adapter's
            // code you have to do something
            LayoutInflater inflater = ChatRoomActivity.this.getLayoutInflater();
            //create a placeholder for the view that is going to be inflated
            View result;
            //find out what type is in that position
            if (getItemType(position)) {
                //if type is 1, then call the applicable xml file to send a message
                result = inflater.inflate(R.layout.chatroom_send, null);
            } else {
                result = inflater.inflate(R.layout.chatroom_receive, null);
            }
            //finding the textview of the inflated xml files
            TextView displayMessage = result.findViewById(R.id.chatTextView);
            //putting that text in the correct position
            displayMessage.setText(getItem(position));

            return result;
        }
    }

    private void printCursor() {

        Log.v(TAG, Integer.toString(myDb.getVersionNum()));
        Log.v(TAG1, Integer.toString(data.getColumnCount()));
        Log.v(TAG3, Integer.toString(data.getCount()));
        for (int i = 0; i < data.getColumnCount(); i++) {
            Log.v(TAG2, data.getColumnName(i));
        }
        Log.v(TAG4, getData());
    }

    public String getData() {
        String[] messages = new String[]{COL_MESSAGE};
        Cursor c = db.rawQuery("Select * from " +TABLE_NAME,null);
        String result = "";

        int msg = c.getColumnIndex(COL_MESSAGE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(msg) + "\n";
            c.close();
        }
        return result;

    }

    //this writes to the DB
    public void writeDB (String message,int type){
        //ContentValues is a reference type to make it easier to insert date into the database
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDb.COL_TYPE, type);
        contentValues.put(myDb.COL_MESSAGE, message);
        //creating the statement to be able to insert into the database ???
        long result = db.insert(TABLE_NAME, null, contentValues);
        //create query to insert into database
        //this is a rule that you follow everytime you use this function
        data = myDb.getWritableDatabase().query(TABLE_NAME, new String[]{
                        myDb.COL_TYPE, myDb.COL_MESSAGE},
                COL_ID + " = " + id, null, null,
                null, null);

    }

    //converting the int value stored in the database to a true(sent) or false(received) in order for
    // the app to know where to put the stored messages
    private boolean isSent () {
        if (data.getInt(data.getColumnIndex(myDb.COL_TYPE)) == 1) {
            return true;
        } else {
            return false;
        }
    }



    }



