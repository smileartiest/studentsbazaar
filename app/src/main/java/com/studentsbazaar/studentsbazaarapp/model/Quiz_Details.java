package com.studentsbazaar.studentsbazaarapp.model;

public class Quiz_Details {

    private String Quiz_Id;
    private String Quiz_ques;
    private String Option1;
    private String Option2;
    private String Option3;
    private String Option4;
    private String Crct_Ans;
    private String Created_Date;

    public Quiz_Details(String quiz_Id, String quiz_ques, String option1, String option2, String option3, String option4, String crct_Ans, String created_Date) {
        Quiz_Id = quiz_Id;
        Quiz_ques = quiz_ques;
        Option1 = option1;
        Option2 = option2;
        Option3 = option3;
        Option4 = option4;
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

    public String getOption1() {
        return Option1;
    }

    public String getOption2() {
        return Option2;
    }

    public String getOption3() {
        return Option3;
    }

    public String getOption4() {
        return Option4;
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

    public void setOption1(String Option1) {
        this.Option1 = Option1;
    }

    public void setOption2(String Option2) {
        this.Option2 = Option2;
    }

    public void setOption3(String Option3) {
        this.Option3 = Option3;
    }

    public void setOption4(String Option4) {
        this.Option4 = Option4;
    }

    public void setCrct_Ans(String Crct_Ans) {
        this.Crct_Ans = Crct_Ans;
    }

    public void setCreated_Date(String Created_Date) {
        this.Created_Date = Created_Date;
    }


}
