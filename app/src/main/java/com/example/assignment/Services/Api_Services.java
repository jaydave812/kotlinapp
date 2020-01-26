package com.example.assignment.Services;

import com.example.assignment.Model.ModelClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Api_Services {

    /*/api/User/info*/
    @GET("/api/users")
    Call<ModelClass> getUsersList(@Query("page") int page);

}
