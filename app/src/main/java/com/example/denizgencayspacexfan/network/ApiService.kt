package com.example.denizgencayspacexfan.network

import com.example.denizgencayspacexfan.models.RocketModel
import com.example.denizgencayspacexfan.models.UpcomingLaunchModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("rockets")
    fun getRockets(): Call<List<RocketModel>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(): Call<List<UpcomingLaunchModel>>

}