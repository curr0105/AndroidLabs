package com.example.androidlabs;

public class Messages {

    final String message;
    //using type 1 for send message and type 0 for receive message
    //if you press send button it calls number 1 and left side

//this needs to be changed to a Boolean
    final boolean type;
    private final long id;

    //this is used in ChatRoomActivity class inside the arraylist
    public Messages(String message, boolean type, long id){
        this.message = message;
        this.type = type;
        this.id = id;

    }
}
