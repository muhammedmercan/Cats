package com.example.cats;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("v1/breeds")
    Call<List<Cat>> getCats();

}
