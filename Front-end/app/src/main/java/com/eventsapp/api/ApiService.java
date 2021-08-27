package com.eventsapp.api;


import com.eventsapp.model.CommentsPojo;
import com.eventsapp.model.EventsPojo;
import com.eventsapp.model.ResponseData;
import com.eventsapp.model.StudentPojo;
import com.eventsapp.model.TeacherPojo;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


public interface ApiService {

    @GET("/events/studentadd.php?")
    Call<ResponseData> studentadd(
            @Query("name") String name,
            @Query("email") String email,
            @Query("pass") String pass,
            @Query("phone") String phone);

    @GET("/events/studentlogin.php?")
    Call<ResponseData> studentlogin(
            @Query("email") String email,
            @Query("pass") String pass);

    @GET("/events/studentprofile.php?")
    Call<List<StudentPojo>> studentprofile(
            @Query("email") String email);


    @GET("/events/studentupdateprofile.php?")
    Call<ResponseData> studentupdateprofile(
            @Query("name") String name,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("pass") String pass);

    @GET("/events/teacheradd.php?")
    Call<ResponseData> teacheradd(
            @Query("name") String name,
            @Query("email") String email,
            @Query("pass") String pass,
            @Query("phone") String phone);

    @GET("/events/teacherlogin.php?")
    Call<ResponseData> teacherlogin(
            @Query("email") String email,
            @Query("pass") String pass);


    @GET("/events/teacherprofile.php?")
    Call<List<TeacherPojo>> teacherprofile(
            @Query("email") String email);

    @GET("/events/teacherupdateprofile.php?")
    Call<ResponseData> teacherupdateprofile(
            @Query("name") String name,
            @Query("email") String email,
            @Query("phone") String phone,
            @Query("pass") String pass);


    //http://paytracker.ca/events/adminlogin.php

    @GET("/events/adminlogin.php?")
    Call<ResponseData> adminlogin(
            @Query("email") String email,
            @Query("pass") String pass);



    @GET("/events/updateprofile.php?")
    Call<ResponseData> update_profile(
            @Query("name") String name,
            @Query("email") String email,
            @Query("pass") String pass,
            @Query("phone") String phone);

    @GET("events/getstudents.php?")
    Call<List<StudentPojo>> getstudents();

    @GET("events/getteachers.php?")
    Call<List<TeacherPojo>> getteachers();

    @GET("events/forgotPass.php")
    Call<ResponseData> getPassword(@Query("email") String email);


    @Multipart
    @POST("events/addevent.php")
    Call<ResponseData> addevent(
            @Part MultipartBody.Part file,
            @PartMap Map<String, String> partMap
    );

//id,category,name,dat,venue,description
    @GET("/events/updateevent.php?")
    Call<ResponseData> updateevent(
            @Query("id") String id,
            @Query("category") String category,
            @Query("name") String name,
            @Query("dat") String dat,
            @Query("venue") String venue,
            @Query("description") String description
            );

    @GET("/events/deleteevent.php")
    Call<ResponseData> deleteevent(@Query("id") String id);

    @GET("/events/deletestudent.php")
    Call<ResponseData> deletestudent(@Query("id") String id);

    @GET("/events/deleteteacher.php")
    Call<ResponseData> deleteteacher(@Query("id") String id);

    @GET("events/getadminevents.php")
    Call<List<EventsPojo>> getadminevents();

    @GET("events/upcoming.php")
    Call<List<EventsPojo>> upcoming();

    @GET("events/getmystudentevents.php?")
    Call<List<EventsPojo>> getmystudentevents(@Query("email") String email);

    @GET("/events/comment.php?")
    Call<ResponseData> comment(
            @Query("name") String name,
            @Query("msg") String msg,
            @Query("email") String email,
            @Query("eid") String eid);

    @GET("/events/updatecomment.php?")
    Call<ResponseData> updatecomment(
            @Query("name") String name,
            @Query("msg") String msg,
            @Query("email") String email,
            @Query("cid") String cid);



    @GET("/events/joinevent.php?")
    Call<ResponseData> joinevent(
            @Query("email") String email,
            @Query("eid") String eid);

    @GET("/events/getcount.php?")
    Call<ResponseData> getcount(
            @Query("eid") String eid);

    @GET("events/getpaticipants.php?")
    Call<List<StudentPojo>> getpaticipants(
            @Query("eid") String eid
    );


    @GET("events/getcomments.php?")
    Call<List<CommentsPojo>> getcomments(
            @Query("eid") String eid
    );

    @GET("events/getadmincomments.php?")
    Call<List<CommentsPojo>> getadmincomments();

    @GET("/events/deletecomment.php")
    Call<ResponseData> deletecomment(@Query("id") String id);


    @GET("/events/forgotPassword.php")
    Call<ResponseData> forgotPassword(@Query("email") String email);

    @GET("/events/tforgotPassword.php")
    Call<ResponseData> tforgotPassword(@Query("email") String email);

}
