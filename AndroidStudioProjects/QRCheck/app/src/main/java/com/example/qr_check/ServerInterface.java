package com.example.qr_check;

import org.json.JSONObject;

import retrofit2.Call;
import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface SeverInterface {
    @FormUrlEncoded
    @POST("GetCourse.php")
    Call<List<CourseSearch.Course>> getCourseInfo(
            @Field("std_id") String std_id
    );

    @FormUrlEncoded
    @POST("GetProCourse.php")
    Call<List<ProCourseActivity.ProCourse>> getProCourse(
            @Field("pro_name") String pro_name
    );

    @FormUrlEncoded
    @POST("DeleteProCourse.php")
    Call<List<ProCourseActivity.ProCourse>> deleteProCourse(
            @Field("pro_name") String pro_name,
            @Field("course_name") String course_name
    );

    @FormUrlEncoded
    @POST("GetStdCourse.php")
    Call<List<StdCourseActivity.StdCourse>> getStdCourse(
            @Field("std_id") String std_id
    );

    @FormUrlEncoded
    @POST("DeleteStdCourse.php")
    Call<List<StdCourseActivity.StdCourse>> deleteStdCourse(
            @Field("std_id") String std_id,
            @Field("course_name") String course_name
    );

    @FormUrlEncoded
    @POST("StdCourseRegister.php")
    Call<List<CourseSearch.Course>> putCourseInfo(
            @Field("std_id") String std_id,
            @Field("course_name") String course_name
    );

    @FormUrlEncoded
    @POST("getCode.php")
    Call<List<CheckCodeActivity.CodeCheck>> getCode(
            @Field("course_name") String course_name
    );

    @FormUrlEncoded
    @POST("QRCheck.php")
    Call<List<CheckCodeActivity.CodeCheck>> putCodeInfo(
            @Field("std_id") String std_id,
            @Field("course_name") String course_name,
            @Field("result") Integer result
    );

    @FormUrlEncoded
    @POST("UpdateCode.php")
    Call<List<ProCourseActivity.ProCourse>> updateCode(
            @Field("course_name") String course_name,
            @Field("code") String code
    );
}