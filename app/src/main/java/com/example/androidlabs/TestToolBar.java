package com.example.androidlabs;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolBar extends AppCompatActivity {

    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        Button alertDialogButton = findViewById(R.id.item2);
        alertDialogButton.setOnClickListener( click ->  alert() );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu items for the ToolBar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                alert();
                break;
            case R.id.item3:
                Snackbar sb = Snackbar.make(myToolbar, "Go Back?", Snackbar.LENGTH_LONG)
                  .setAction("Action text", e -> Log.e("TestToolBar", "Clicked Undo"));
                     sb.show();
                break;
            case R.id.item4: //this is supposed to be the overflow
                Toast.makeText(this, "You clicked on the overflow menu", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void alert() {
        View middle = getLayoutInflater().inflate(R.layout.new_message_string,null);
        EditText et = middle.findViewById(R.id.view_edit_text);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.spider)
            .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ///if the user clicks on the positive button of the dialog box, then the message
                    //in item4 changes to whatever is typed in
                    }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //Do nothing on Cancel
                }
            }).setView(middle);

        builder.create().show();
    }
}


