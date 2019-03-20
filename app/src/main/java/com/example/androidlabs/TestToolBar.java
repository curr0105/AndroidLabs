package com.example.androidlabs;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextSelection;
import android.widget.EditText;
import android.widget.Toast;


public class TestToolBar extends AppCompatActivity {

    private Toolbar myToolbar;
    private String item4 = "You clicked on the overflow menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool_bar);
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu items for the ToolBar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "This is the initial message", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                alert();
                break;
            case R.id.item3:
                snack();
                break;
            case R.id.item4: //this is supposed to be the overflow
                //item4 is a String that will store what is typed temporarly each time it's changed
                Toast.makeText(this, item4, Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    public void alert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(TestToolBar.this);
        LayoutInflater inflator = getLayoutInflater();

        //pulling xml new message view into this screen
        final View middle = inflator.inflate(R.layout.new_message_string, null);


        //build the actions for the alert dialog
        builder.setView(middle).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //if the user clicks on the accept button of the dialog box, then the message
                //in item4 changes to whatever is typed in
                EditText et = middle.findViewById(R.id.view_edit_text);
                //extract the text that is typed in the editText, you need make sure your pointing to
                //the right context or you will get a nullPointerException
                item4 = et.getText().toString();

            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing on Cancel
                    }
                }).show();

    }
    public void snack(){
        Snackbar sb = Snackbar.make(myToolbar, "Go Back to Profile Page?", Snackbar.LENGTH_LONG)
                .setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //kills this activity and goes back to previous one
                        finish();
                    }
                });
        View snackView = sb.getView();
        snackView.setBackgroundColor(ContextCompat.getColor(TestToolBar.this,R.color.snackBarBackground));
        sb.show();

    }
}


