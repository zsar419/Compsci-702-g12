package com.uoa.compsci_702_g12.utilities;

import com.uoa.compsci_702_g12.models.Buddy;
import com.uoa.compsci_702_g12.models.User;
import com.uoa.compsci_702_g12.models.UserApiEndPointInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by z on 10/04/17.
 */

public class RESTfulService {

    private static RESTfulService instance;
    private UserApiEndPointInterface userApi;

    public static synchronized RESTfulService getInstance() {
        if (instance == null)
            instance = new RESTfulService();
        return instance;
    }

    private RESTfulService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.DATABASE_API_URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        userApi = retrofit.create(UserApiEndPointInterface.class);
    }

    public List<User> getVolunteers(int userId) {
        Call<List<User>> callGetUsers= userApi.getVolunteers(userId);
        try {
            Response<List<User>> response = callGetUsers.execute();
            if (response.isSuccessful()) return response.body();
        } catch (IOException e) { e.printStackTrace(); }
        return new ArrayList<User>();
    }

    public List<User> getNewbies(int userId) {
        Call<List<User>> callGetUsers= userApi.getNewbies(userId);
        try {
            Response<List<User>> response = callGetUsers.execute();
            if (response.isSuccessful()) return response.body();
        } catch (IOException e) { e.printStackTrace(); }
        return new ArrayList<User>();
    }

    public List<User> getVolunteersForCategory(int category) {
        Call<List<User>> callGetUsers= userApi.getVolunteersForCategory(category);
        try {
            Response<List<User>> response = callGetUsers.execute();
            if (response.isSuccessful()) return response.body();
        } catch (IOException e) { e.printStackTrace(); }
        return new ArrayList<User>();
    }

    public int createUser(final User user) {
        Call<User> callPost = userApi.createUser(user);
        try {
            Response<User> response = callPost.execute();
            if (response.isSuccessful()) return response.body().getUserId();
        } catch (IOException e) { e.printStackTrace(); }
        return 0;
    }

    public boolean registerBuddy(final Buddy buddy) {
        Call<Buddy> callPost = userApi.registerBuddy(buddy);
        try {
            Response<Buddy> response = callPost.execute();
            if (response.isSuccessful()) return true;
        } catch (IOException e) { e.printStackTrace(); }
        return false;
    }

    public boolean removeUser(int id){
        Call<User> callDelete = userApi.removeUser(id);
        try {
            Response<User> response = callDelete.execute();
            if (response.isSuccessful()) return true;
        } catch (IOException e) { e.printStackTrace(); }
        return false;
    }

    public boolean removeBuddy(int volunteerId, int newbieId){
        Call<Buddy> callDelete = userApi.removeBuddy(volunteerId, newbieId);
        try {
            Response<Buddy> response = callDelete.execute();
            if (response.isSuccessful()) return true;
        } catch (IOException e) { e.printStackTrace(); }
        return false;
    }

}
