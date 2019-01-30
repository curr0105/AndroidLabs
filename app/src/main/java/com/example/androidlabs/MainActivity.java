package com.example.androidlabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sp;
    EditText email;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.thisEmailIsPassedToPage2);
        sp = getSharedPreferences("Lab3", Context.MODE_PRIVATE);
        String savedString = sp.getString("Email", "0");

        email.setText(savedString);
        Log.e(ACTIVITY_NAME, "In Function onCreate() in MainActivity:");

        Button login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                EditText et = (EditText)findViewById(R.id.thisEmailIsPassedToPage2);
                intent.putExtra("typed", et.getText().toString());
                startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        int i = 0;
        i++;
        //if request code is 2, then we are coming back from ProfileActivity
        if(requestCode == 2){
            EditText et = (EditText)findViewById(R.id.thisEmailIsPassedToPage2);
            String fromProfile = data.getStringExtra("previousTyped");
            et.setText(fromProfile);
            Log.i("Back", "Message");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();

        String whatWasTyped = email.getText().toString();
        editor.putString("Email", whatWasTyped);

        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
















