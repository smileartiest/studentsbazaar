package com.studentsbazaar.studentsbazaarapp.model;

public class Likes_Details {

    int Like_ID , Object_ID , User_ID ,Like ;
    String Type;

    public Likes_Details() {
    }

    public Likes_Details(int object_ID, int user_ID, int like, String type) {
        Object_ID = object_ID;
        User_ID = user_ID;
        Like = like;
        Type = type;
    }

    public Likes_Details(int like_ID, int object_ID, int user_ID, int like, String type) {
        Like_ID = like_ID;
        Object_ID = object_ID;
        User_ID = user_ID;
        Like = like;
        Type = type;
    }

    public int getLike_ID() {
        return Like_ID;
    }

    public void setLike_ID(int like_ID) {
        Like_ID = like_ID;
    }

    public int getObject_ID() {
        return Object_ID;
    }

    public void setObject_ID(int object_ID) {
        Object_ID = object_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
