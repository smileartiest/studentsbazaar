package com.studentsbazaar.studentsbazaarapp.model;

public class Memes_Details {



    private String User_Id;
    private String User_Name;
    private String Caption;
    private String Memes;
    private String Smile;
    private String Notbad;
    private String Created_Date;


    // Getter Methods
    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }
    public String getUser_Name() {
        return User_Name;
    }

    public String getCaption() {
        return Caption;
    }

    public String getMemes() {
        return Memes;
    }

    public String getSmile() {
        return Smile;
    }

    public String getNotbad() {
        return Notbad;
    }

    public String getCreated_Date() {
        return Created_Date;
    }

    // Setter Methods

    public void setUser_Name(String User_Name) {
        this.User_Name = User_Name;
    }

    public void setCaption(String Caption) {
        this.Caption = Caption;
    }

    public void setMemes(String Memes) {
        this.Memes = Memes;
    }

    public void setSmile(String Smile) {
        this.Smile = Smile;
    }

    public void setNotbad(String Notbad) {
        this.Notbad = Notbad;
    }

    public void setCreated_Date(String Created_Date) {
        this.Created_Date = Created_Date;
    }

}
