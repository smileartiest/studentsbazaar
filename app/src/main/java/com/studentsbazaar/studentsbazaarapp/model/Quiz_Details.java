package com.studentsbazaar.studentsbazaarapp.model;

public class Quiz_Details {

    private String Quiz_Id;
    private String Quiz_Type;
    private String Quiz_ques;
    private String Quiz_Ans;
    private String Crct_Ans;
    private int Viewers;
    private int Flag;
    private String Created_Date;
    private String Modified_Date;
    private String responsedata;
    private String id;

    public int getViewers() {
        return Viewers;
    }

    public void setViewers(int viewers) {
        Viewers = viewers;
    }

    public String getModified_Date() {
        return Modified_Date;
    }

    public void setModified_Date(String modified_Date) {
        Modified_Date = modified_Date;
    }

    public int getFlag() {
        return Flag;
    }

    public void setFlag(int flag) {
        Flag = flag;
    }

    public String getQuiz_Type() {
        return Quiz_Type;
    }

    public void setQuiz_Type(String quiz_Type) {
        Quiz_Type = quiz_Type;
    }

    public String getQuiz_Ans() {
        return Quiz_Ans;
    }

    public void setQuiz_Ans(String quiz_Ans) {
        Quiz_Ans = quiz_Ans;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResponsedata() {
        return responsedata;
    }

    public void setResponsedata(String responsedata) {
        this.responsedata = responsedata;
    }

    public Quiz_Details(String quiz_Id, String quiz_ques,  String crct_Ans, String created_Date) {
        Quiz_Id = quiz_Id;
        Quiz_ques = quiz_ques;
        Crct_Ans = crct_Ans;
        Created_Date = created_Date;
    }

// Getter Methods



    public String getQuiz_Id() {
        return Quiz_Id;
    }

    public String getQuiz_ques() {
        return Quiz_ques;
    }


    public String getCrct_Ans() {
        return Crct_Ans;
    }

    public String getCreated_Date() {
        return Created_Date;
    }

    // Setter Methods
    public void setQuiz_Id(String Quiz_Id) {
        this.Quiz_Id = Quiz_Id;
    }

    public void setQuiz_ques(String Quiz_ques) {
        this.Quiz_ques = Quiz_ques;
    }


    public void setCrct_Ans(String Crct_Ans) {
        this.Crct_Ans = Crct_Ans;
    }

    public void setCreated_Date(String Created_Date) {
        this.Created_Date = Created_Date;
    }


}
