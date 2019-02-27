package com.example.androidlabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Button;


public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private ImageButton mImageButton;
    private Button chatButton;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileactivity);
        this.setTitle("Lab 4 - Profile");

        MyDatabaseOpenHelper myDb = new MyDatabaseOpenHelper(this);
        Intent fromPrevious = getIntent();
        String previousTyped = fromPrevious.getStringExtra("typed");

        EditText enterText = (EditText) findViewById(R.id.editText6);
        enterText.setText(previousTyped);

        mImageButton = (ImageButton) this.findViewById(R.id.imageButton);
        mImageButton.setOnClickListener(bt -> {
               dispatchTakePictureIntent();
        });
        chatButton = (Button) this.findViewById(R.id.goToChatButton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent chat = new Intent(ProfileActivity.this,ChatRoomActivity.class);
                startActivity(chat);
            }
        });
    }


    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    //    Log.e(ACTIVITY_NAME, "In Function onPause():");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    //    Log.e(ACTIVITY_NAME, "In Function onDestroy():");
    }

    @Override
    protected void onStop() {
        super.onStop();
    //    Log.e(ACTIVITY_NAME, "In Function onStop():");
    }

    @Override
    protected void onResume() {
        super.onResume();
    //    Log.e(ACTIVITY_NAME, "In Function onResume():");
    }

    @Override
    protected void onStart() {
        super.onStart();
    //    Log.e(ACTIVITY_NAME, "In Function onStart():");
    }

}






