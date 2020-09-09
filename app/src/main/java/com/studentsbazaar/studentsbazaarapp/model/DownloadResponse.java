package com.studentsbazaar.studentsbazaarapp.model;

import java.util.List;

public class DownloadResponse {

    private List<Project_details> Events_Details;
    private List<Login_Details> Login_Details;
    private List<Score_Details> Score_Details;
    private List<Campus> campus_details;
    private List<Profile_Details> Profile_Details;
    private List<Quiz_Details> Quiz_Details;
    private List<College_Details> College_Details;
    private List<Posters_Details> Posters_Details;
    private List<Memes_Details> Memes_Details;
    private List<Tech_News_model> Tech_News_model;
    private List<LogUsername> logusername;
    private List<Quiz_History> Quiz_History;
    private List<feb> feb;

    public List<com.studentsbazaar.studentsbazaarapp.model.Quiz_History> getQuiz_History() {
        return Quiz_History;
    }

    public void setQuiz_History(List<com.studentsbazaar.studentsbazaarapp.model.Quiz_History> quiz_History) {
        Quiz_History = quiz_History;
    }

    public List<com.studentsbazaar.studentsbazaarapp.model.feb> getFeb() {
        return feb;
    }

    public void setFeb(List<com.studentsbazaar.studentsbazaarapp.model.feb> feb) {
        this.feb = feb;
    }

    public List<LogUsername> getLogusername() {
        return logusername;
    }

    public void setLogusername(List<LogUsername> logusername) {
        this.logusername = logusername;
    }

    public List<Tech_News_model> getTech_News_model() {
        return Tech_News_model;
    }

    public List<com.studentsbazaar.studentsbazaarapp.model.Score_Details> getScore_Details() {
        return Score_Details;
    }

    public void setScore_Details(List<com.studentsbazaar.studentsbazaarapp.model.Score_Details> score_Details) {
        Score_Details = score_Details;
    }

    public List<Login_Details> getLogin_Details() {
        return Login_Details;
    }

    public void setLogin_Details(List<Login_Details> login_Details) {
        Login_Details = login_Details;
    }

    public List<com.studentsbazaar.studentsbazaarapp.model.Profile_Details> getProfile_Details() {
        return Profile_Details;
    }

    public void setProfile_Details(List<com.studentsbazaar.studentsbazaarapp.model.Profile_Details> profile_Details) {
        Profile_Details = profile_Details;
    }

    public void setTech_News_model(List<Tech_News_model> tech_News_model) {
        Tech_News_model = tech_News_model;
    }


    public List<Memes_Details> getMemes_details() {
        return Memes_Details;
    }

    public void setMemes_details(List<Memes_Details> memes_details) {
        this.Memes_Details = memes_details;
    }


    public List<Posters_Details> getPosters_details() {
        return Posters_Details;
    }

    public void setPosters_details(List<Posters_Details> posters_details) {
        this.Posters_Details = posters_details;
    }


    public List<College_Details> getCollege_Details() {
        return College_Details;
    }

    public void setCollege_Details(List<College_Details> college_Details) {
        College_Details = college_Details;
    }


    private List<Login_Status> Login_Status;

    public void setLogin_Status(List<Login_Status> Login_Status) {
        this.Login_Status = Login_Status;
    }

    public List<Login_Status> getLogin_Status() {
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
