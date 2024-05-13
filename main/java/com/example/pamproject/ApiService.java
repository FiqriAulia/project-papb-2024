package com.example.pamproject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("friendlist/show_data.php")
    Call<List<friendlist>> getFriendList();
}
