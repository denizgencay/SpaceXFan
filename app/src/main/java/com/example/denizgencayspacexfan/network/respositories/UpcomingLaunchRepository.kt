package com.example.denizgencayspacexfan.network.respositories

import androidx.lifecycle.MutableLiveData
import com.example.denizgencayspacexfan.models.UpcomingLaunchModel
import com.example.denizgencayspacexfan.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class UpcomingLaunchRepository  @Inject constructor(
    private val apiService: ApiService
){

    fun getAllUpcomingLaunchesFromApi(upcomingLaunchDataList: MutableLiveData<List<UpcomingLaunchModel>>){
        val call: Call<List<UpcomingLaunchModel>> = apiService.getUpcomingLaunches()
        call?.enqueue(object : Callback<List<UpcomingLaunchModel>> {
            override fun onResponse(
                call: Call<List<UpcomingLaunchModel>>,
                response: Response<List<UpcomingLaunchModel>>
            ) {
                if (response.isSuccessful){
                    upcomingLaunchDataList.postValue(response.body())
                    println("hoo: ${response.code()}")
                }
                println("hoo: ${response.code()}")
            }

            override fun onFailure(call: Call<List<UpcomingLaunchModel>>, t: Throwable) {
                println("hoo: $t")
            }
        })

    }

}