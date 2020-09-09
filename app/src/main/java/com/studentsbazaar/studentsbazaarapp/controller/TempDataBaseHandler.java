package com.studentsbazaar.studentsbazaarapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.studentsbazaar.studentsbazaarapp.model.Likes_Details;

import java.util.ArrayList;
import java.util.List;

public class TempDataBaseHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StudentBazaarTemp";
    public static final String TABLE_PERSON = "likes";
    public static final String LIKE_ID = "Like_ID";
    public static final String OBJECT_ID = "Object_ID";
    public static final String USER_ID = "User_ID";
    public static final String TYPE = "Type";
    public static final String LIKE = "Like";
    public static final String LOG_NAME = "Temp SQLite";

    public TempDataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String DATABASE_TABLE = "CREATE TABLE IF NOT EXISTS  " + TABLE_PERSON + "(" + LIKE_ID + " INTEGER PRIMARY KEY," + OBJECT_ID + " INTEGER," + USER_ID + " INTEGER," + TYPE + " TEXT," + LIKE+ "INTEGER DEFAULT 0 )";
        db.execSQL(DATABASE_TABLE);
        Log.d(LOG_NAME , "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PERSON);
        onCreate(db);
    }

    //DropTable
    public void DropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PERSON);
        Log.d(LOG_NAME , "Table cleared");
    }

    //Store details in db
    public void StoreDetails(Likes_Details details){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(OBJECT_ID , details.getObject_ID());
        values.put(USER_ID , details.getUser_ID());
        values.put(TYPE , details.getType());
        values.put(LIKE , details.getLike());
        db.insert(TABLE_PERSON , null , values);
        db.close();
        Log.d(LOG_NAME , "Stored Details");
    }

    //find details through Object id
    public List<Likes_Details> findDetails(int objectID){
        List<Likes_Details> details = new ArrayList<Likes_Details>();
        String query = "SELECT * FROM "+TABLE_PERSON+" WHERE "+OBJECT_ID+" = "+objectID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        if(cursor.moveToFirst()){
            do{
                Likes_Details likes_details = new Likes_Details();
                likes_details.setLike_ID(cursor.getInt(0));
                likes_details.setObject_ID(cursor.getInt(1));
                likes_details.setUser_ID(cursor.getInt(2));
                likes_details.setType(cursor.getString(3));
                likes_details.setLike(cursor.getInt(4));
                details.add(likes_details);
            }while (cursor.moveToNext());
        }
        Log.d(LOG_NAME , "Details : "+details.toString());
        return details;
    }

    //check right or not
    public Boolean LikeorNot(int objectID){
        Boolean sts = false;
        String query = "SELECT * FROM "+TABLE_PERSON+" WHERE "+OBJECT_ID+" = "+objectID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query , null);
        if(cursor!=null){
            sts = true;
        }else{
            sts = false;
        }
       return sts;
    }

    //delete from table through object id
    public void deleteLike(int objectID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_PERSON+" WHERE "+OBJECT_ID+" = "+objectID;
        db.execSQL(query);
        Log.d(LOG_NAME , "Data Deleted");
    }

}
