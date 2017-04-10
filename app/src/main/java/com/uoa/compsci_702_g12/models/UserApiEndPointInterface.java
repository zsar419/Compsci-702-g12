package com.uoa.compsci_702_g12.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by z on 10/04/17.
 */

public interface UserApiEndPointInterface {

    // Get user asset based on id
    @GET("Users/Buddies")
    Call<List<User>> getVolunteers(@Query("newbieId") int newbieId);

    @GET("Users/Buddies")
    Call<List<User>> getNewbies(@Query("volunteerId") int volunteerId);

    @GET("Users/{category}/Volunteers")
    Call<List<User>> getVolunteersForCategory(@Path("category") int category);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("Users/")
    Call<User> createUser(@Body User user);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("Buddyrows/")
    Call<Buddy> registerBuddy(@Body Buddy buddy);

    @DELETE("Users/{id}")
    Call<User> removeUser(@Path("id") int id);

    @DELETE("Users")
    Call<Buddy> removeBuddy(@Query("volunteerId") int volunteerId, @Query("newbieId") int newbieId);

}
