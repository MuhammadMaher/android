package com.example.graduationproject.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface INewsApi {

    //https://newsapi.org/
    // endpoint= v2/top-headlines?country=us&category=business&apiKey=b096c75fdf394e5ea57d0580cdc73e6a

    @GET("v2/top-headlines?apiKey=b096c75fdf394e5ea57d0580cdc73e6a")
    Call<News>getNews(
            @Query("country") String country,
            @Query("category") String category
            );


}
