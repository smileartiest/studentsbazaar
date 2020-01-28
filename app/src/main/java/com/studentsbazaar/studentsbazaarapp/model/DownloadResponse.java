package com.studentsbazaar.studentsbazaarapp.model;

import java.util.List;

public class DownloadResponse {
    private List<Project_details> Events_Details;
    private List<Campus> campus_details;
    private  List<Quiz_Details> Quiz_Details;
    private List<College_Details> College_Details;

    public List<College_Details> getCollege_Details() {
        return College_Details;
    }

    public void setCollege_Details(List<College_Details> college_Details) {
        College_Details = college_Details;
    }


    private List<Login_Status> Login_Status;

    public void setLogin_Status(List<Login_Status> Login_Status){
        this.Login_Status = Login_Status;
    }
    public List<Login_Status> getLogin_Status(){
        return this.Login_Status;
    }

    public List<Quiz_Details> getQuiz_details() {
        return Quiz_Details;
    }

    public void setQuiz_details(List<Quiz_Details> quiz_details) {
        this.Quiz_Details = quiz_details;
    }


    public List<Campus> getCampus_details() {
        return campus_details;
    }

    public void setCampus_details(List<Campus> campus_details) {
        this.campus_details = campus_details;
    }

    public List<Project_details> getProject_details() {
        return Events_Details;
    }

    public void setProject_details(List<Project_details> project_details) {
        this.Events_Details = project_details;
    }




}
