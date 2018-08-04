package com.example.toshiba.pushnotificationusingfirebace.Model;

public class Notification {
   private String from;
    private   String message;

    public Notification(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notification() {
    }
}
