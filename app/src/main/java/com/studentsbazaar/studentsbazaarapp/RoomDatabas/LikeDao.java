package com.studentsbazaar.studentsbazaarapp.RoomDatabas;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LikeDao {

    @Insert
    void DataInsert(LikeModel likeModel);

    @Query("SELECT * FROM likes WHERE Object_ID = :oid")
    List<LikeModel> findModel(int oid);

}
