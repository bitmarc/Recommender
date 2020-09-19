package com.example.recommender;

public class Message {
    private String message;

    public Message(){
        message="";
    }
    public Message(String message, String code){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
