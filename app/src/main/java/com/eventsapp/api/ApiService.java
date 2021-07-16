package com.eventsapp.api;


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


    @GET("/events/adminlogin.php?")
    Call<ResponseData> adminlogin(
            @Query("email") String email,
            @Query("pass") String pass);

    @GET("/events/myprofile.php?")
    Call<List<StudentPojo>> getProfile(
            @Query("email") String email);

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

}
