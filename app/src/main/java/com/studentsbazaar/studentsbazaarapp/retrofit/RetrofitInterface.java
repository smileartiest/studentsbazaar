package com.studentsbazaar.studentsbazaarapp.retrofit;




import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @GET
    Call<DownloadResponse> getHomeComponentList(@Url String url);

    @POST
    Call<String> getLoginDetails(@Url String url);

    @GET
    Call<String> updateEventStatus(@Url String url);

    @FormUrlEncoded
    @POST("/addplacement.php")
    Call<String> addplacements(@Field("date") String date,
                               @Field("cname") String cname,
                               @Field("comname") String comname,
                               @Field("noofstu") String noofstu,
                               @Field("pack") String pack,
                               @Field("comments") String comments,
                               @Field("domain") String domain);

    @FormUrlEncoded
    @POST("/Register.php")
    Call<String> addaccount(@Field("name") String name,
                            @Field("password") String password,
                            @Field("cname") String collegename,
                            @Field("degree") String degree,
                            @Field("dept") String dept,
                            @Field("year") String year,
                            @Field("sem") String sem,
                            @Field("mobile") String mobile,
                            @Field("acyear") String acyear,
                            @Field("mail") String mail,
                            @Field("device") String device);

    @GET
    Call<DownloadResponse> getQuizQuestions(@Url String url);

    @FormUrlEncoded
    @POST("/addeventdetails.php")
    Call<String> insertUser(
            @Field("eid") String eid,
            @Field("title") String title,
            @Field("type") String type,
            @Field("name") String name,
            @Field("start")
                    String start,
            @Field("end")
                    String end,
            @Field("conducted")
                    String conducted,
            @Field("degree")
                    String degree,
            @Field("dept")
                    String dept,
            @Field("colladd")
                    String colladd,
            @Field("district")
                    String district,
            @Field("state")
                    String state,
            @Field("organiser")
                    String organiser,
            @Field("events")
                    String events,
            @Field("eventdis")
                    String eventdis,
            @Field("eventweb")
                    String eventweb,
            @Field("collegeweb")
                    String collegeweb,
            @Field("cpname1")
                    String cpname1,
            @Field("cpno1")
                    String cpno1,
            @Field("cpname2")
                    String cpname2,
            @Field("cpno2")
                    String cpno2,
            @Field("poster")
                    String poster,
            @Field("fees")
                    String fees,
            @Field("accept")
                    String accept,
            @Field("lat")
                    String lat,
            @Field("long")
                    String longt,
            @Field("guest")
                    String guest,
            @Field("pronites")
                    String pronites,
            @Field("accom")
                    String accom,
            @Field("reach")
                    String reach,
            @Field("sponser")
                    String sponser,
            @Field("lastdate")
                    String lastdate,
            @Field("status")
                    String status,
            @Field("comment")
                    String comment);

    @FormUrlEncoded
    @POST("/coordinatorEventUpdate.php")
    Call<String> updateevents(@Field("title") String title,
                              @Field("type") String type,
                              @Field("start") String start,
                              @Field("end") String end,
                              @Field("dept") String dept,
                              @Field("district") String district,
                              @Field("state") String state,
                              @Field("organiser") String organiser,
                              @Field("events") String events,
                              @Field("eventdis") String eventdis,
                              @Field("eventweb") String eventweb,
                              @Field("collegeweb") String collegeweb,
                              @Field("cpname1") String cpname1,
                              @Field("cpno1") String cpno1,
                              @Field("cpname2") String cpname2,
                              @Field("cpno2") String cpno2,
                              @Field("fees") String fees,
                              @Field("pronites") String pronites,
                              @Field("accom") String accom,
                              @Field("reach") String reach,
                              @Field("lastdate") String lastdate,
                              @Field("guest") String guest,
                              @Field("Eid") String Eid);

}


