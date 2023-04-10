package com.example.retrofit.server

import com.example.retrofit.model.ResponseMoviesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("movies")
    fun moviesList(@Query("page") page:Int):Call<ResponseMoviesList>
}