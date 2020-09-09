package com.studentsbazaar.studentsbazaarapp.helper;


/**
 * Created by Admin on 10/2/2017.
 */

public class PersistanceUtil {
    private static String userID;

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        PersistanceUtil.userID = userID;
    }
}
