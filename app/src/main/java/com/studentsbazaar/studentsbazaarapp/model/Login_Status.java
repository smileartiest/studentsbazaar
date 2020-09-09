package com.studentsbazaar.studentsbazaarapp.model;

public class Login_Status {

    private String User_Id;

    private String Log;


    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getLog() {
        return Log;
    }

    public void setLog(String log) {
        Log = log;
    }

    public Login_Status(String user_Id, String log) {
        User_Id = user_Id;
        Log = log;
    }
}
