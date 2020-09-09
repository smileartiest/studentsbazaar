package com.studentsbazaar.studentsbazaarapp.RoomDatabas;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LikeModel.class} , version = 1)
public abstract class LikeRoomDatabase extends RoomDatabase {

    public abstract LikeDao likeDao();
    private static LikeRoomDatabase INSTANCE;

    static LikeRoomDatabase getDatabase(final Context mContext){
        if(INSTANCE == null){
            synchronized (LikeRoomDatabase.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(mContext.getApplicationContext() , LikeRoomDatabase.class , "StudentDataBase").build();
                }
            }
        }
        return INSTANCE;
    }

}
