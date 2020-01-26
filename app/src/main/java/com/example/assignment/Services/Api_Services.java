package com.example.assignment.Services;

import com.example.assignment.model.ModelClass;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api_Services {

    /*/api/User/info*/
    @GET("/api/users")
    Call<ModelClass> getUsersList();

}
