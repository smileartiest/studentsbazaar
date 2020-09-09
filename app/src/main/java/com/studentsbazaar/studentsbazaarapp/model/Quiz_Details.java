package com.studentsbazaar.studentsbazaarapp.model;

public class Quiz_Details {

    private String User_ID,Result_ID,Quiz_Id,Quiz_Type,Quiz_ques,Quiz_Ans,Crct_Ans,Created_Date,Modified_Date,Submit_Answer,msg;
    private int Viewers,Flag,Score;

    public String getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(String user_ID) {
        User_ID = user_ID;
    }

    public String getResult_ID() {
        return Result_ID;
    }

    public void setResult_ID(String result_ID) {
        Result_ID = result_ID;
    }

    public String getModified_Date() {
        return Modified_Date;
    }

    public void setModified_Date(String modified_Date) {
        Modified_Date = modified_Date;
    }

    public String getSubmit_Answer() {
        return Submit_Answer;
    }

    public void setSubmit_Answer(String submit_Answer) {
        Submit_Answer = submit_Answer;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getQuiz_Id() {
        return Quiz_Id;
    }

    public void setQuiz_Id(String quiz_Id) {
        Quiz_Id = quiz_Id;
    }

    public String getQuiz_Type() {
        return Quiz_Type;
    }

    public void setQuiz_Type(String quiz_Type) {
        Quiz_Type = quiz_Type;
    }

    public String getQuiz_ques() {
        return Quiz_ques;
    }

    public void setQuiz_ques(String quiz_ques) {
        Quiz_ques = quiz_ques;
    }

    public String getQuiz_Ans() {
        return Quiz_Ans;
    }

    public void setQuiz_Ans(String quiz_Ans) {
        Quiz_Ans = quiz_Ans;
    }

    public String getCrct_Ans() {
        return Crct_Ans;
    }

    public void setCrct_Ans(String crct_Ans) {
        Crct_Ans = crct_Ans;
    }

    public String getCreated_Date() {
        return Created_Date;
    }

    public void setCreated_Date(String created_Date) {
        Created_Date = created_Date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getViewers() {
        return Viewers;
    }

    public void setViewers(int viewers) {
        Viewers = viewers;
    }

    public int getFlag() {
        return Flag;
    }

    public void setFlag(int flag) {
        Flag = flag;
    }
}
