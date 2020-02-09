package com.studentsbazaar.studentsbazaarapp.retrofit;

import java.util.ArrayList;

public class ApiUtil {

    public static final String BASE_URL = "http://uniqsolutions.co.in/";
    public static final String LOAD_EVENTS = BASE_URL + "getevents.php";
    public static final String UPDATE_STATUS = BASE_URL + "updateEvent.php";
    public static final String LOAD_PLACEMENT = BASE_URL + "getplacementrecords.php";
    public static final String GET_PENDING_EVENTS = BASE_URL + "getpendingEvents.php";
    public static final String LOAD_STUDENTEVENT = BASE_URL + "getStudentEvents.php";
    public static final String GET_QUIZ_QUESTIONS = BASE_URL + "get_quiz.php";
    public static final String GET_COLLEGES = BASE_URL + "getcollegelist.php";
    public static final String ADD_QUIZ_RESULTS=BASE_URL +"updatequizresults.php";
    public  static final String GET_POSTERS=BASE_URL+"getposter.php";
    public static final String GET_MEMES=BASE_URL+"getmems.php";
    public static  final String GET_PENDING_MEMES=BASE_URL+"getpendingpost.php";
    public static final String GET_URL=BASE_URL+"getresultsurl.php";
    public static final String GET_NEWS=BASE_URL+"gettechnews.php";
    public static final String GET_RECENT_EVENTS=BASE_URL+"getrecentevents.php";
    public static  final ArrayList<String> COLLEGEARRAY = new ArrayList<>();
    public static  int QUIZ_RESULT=0;


    public static RetrofitInterface getServiceClass() {
        return RetrofitAPI.getRetrofit(BASE_URL).create(RetrofitInterface.class);
    }
}
