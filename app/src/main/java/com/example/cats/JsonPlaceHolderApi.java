package com.example.cats;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface JsonPlaceHolderApi {


    @GET("v1/breeds")
    Call<List<Cat>> getCats();

    @GET("v1/breeds/search")
    Call<List<Cat>> getCats(@Query("q") String catName);





}
