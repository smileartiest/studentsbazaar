package com.studentsbazaar.studentsbazaarapp.RoomDatabas;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "likes")
public class LikeModel {
    private String TABLE_NAME = "likes";

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Like_ID")
    private int LIKE_ID;

    @ColumnInfo(name = "Object_ID")
    private String OBJECT_ID = "Object_ID";

    @ColumnInfo(name = "User_ID")
    private String USER_ID = "User_ID";

    @ColumnInfo(name = "Type")
    private String TYPE = "Type";

    @ColumnInfo(name = "Like")
    private String LIKE = "Like";

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    public int getLIKE_ID() {
        return LIKE_ID;
    }

    public void setLIKE_ID(int LIKE_ID) {
        this.LIKE_ID = LIKE_ID;
    }

    public String getOBJECT_ID() {
        return OBJECT_ID;
    }

    public void setOBJECT_ID(String OBJECT_ID) {
        this.OBJECT_ID = OBJECT_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getLIKE() {
        return LIKE;
    }

    public void setLIKE(String LIKE) {
        this.LIKE = LIKE;
    }
}
