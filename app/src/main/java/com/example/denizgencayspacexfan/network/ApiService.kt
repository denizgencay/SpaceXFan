package com.example.denizgencayspacexfan.network

import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.data.models.UpcomingLaunchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("rockets")
    fun getRockets(): Call<List<RocketModel>>

    @GET("launches/upcoming")
    fun getUpcomingLaunches(): Call<List<UpcomingLaunchModel>>

    @GET("rockets/{rocketId}")
    fun getRocketsById(@Path("rocketId") rocketId:String): Call<RocketModel>

}