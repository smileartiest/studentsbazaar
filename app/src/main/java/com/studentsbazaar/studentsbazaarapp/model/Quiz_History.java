package com.studentsbazaar.studentsbazaarapp.model;

public class Quiz_History {

    int Result_ID ,Quiz_ID ,User_ID ,Score ;
    String Question_Pic , Answer_Pic , Correct_Answer, Submit_Answer;
    String Create_Date;

    public int getResult_ID() {
        return Result_ID;
    }

    public void setResult_ID(int result_ID) {
        Result_ID = result_ID;
    }

    public int getQuiz_ID() {
        return Quiz_ID;
    }

    public void setQuiz_ID(int quiz_ID) {
        Quiz_ID = quiz_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getCreate_Date() {
        return Create_Date;
    }

    public void setCreate_Date(String create_Date) {
        Create_Date = create_Date;
    }

    public String getQuestion_Pic() {
        return Question_Pic;
    }

    public void setQuestion_Pic(String question_Pic) {
        Question_Pic = question_Pic;
    }

    public String getAnswer_Pic() {
        return Answer_Pic;
    }

    public void setAnswer_Pic(String answer_Pic) {
        Answer_Pic = answer_Pic;
    }

    public String getCorrect_Answer() {
        return Correct_Answer;
    }

    public void setCorrect_Answer(String correct_Answer) {
        Correct_Answer = correct_Answer;
    }

    public String getSubmit_Answer() {
        return Submit_Answer;
    }

    public void setSubmit_Answer(String submit_Answer) {
        Submit_Answer = submit_Answer;
    }
}
