package com.example.r4_11_devmobile;

public class Message {

    private String dateM;

    private String message;


    public Message(String message, String dateM){
        this.message = message;
        this.dateM = dateM;

    }

    public String getDateM() {
        return dateM;
    }

    public String getMessage() {
        return message;
    }
}
