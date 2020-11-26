package com.example.team98;

import java.util.Date;

public class ChatData {
    private String userName;
    private String message;
    private Date d;

    public ChatData() { }

    public ChatData(String userName, String message,Date d) {
        this.userName = userName;
        this.message = message;
        this.d = d;

    }

    public Date getD() {
        return d;
    }

    public void setD(Date d) {
        this.d = d;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}