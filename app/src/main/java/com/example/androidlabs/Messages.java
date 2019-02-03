package com.example.androidlabs;

public class Messages {

    String message;
    //using type 1 for send message and type 0 for receive message
    //if you press send button it calls number 1 and left side
    int type;

    //this is used in ChatRoomActivity class inside the arraylist
    public Messages(String message, int type){
        this.message = message;
        this.type = type;

    }
}
