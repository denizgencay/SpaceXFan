package com.example.denizgencayspacexfan.network.respositories

import androidx.lifecycle.MutableLiveData
import com.example.denizgencayspacexfan.models.RocketModel
import com.example.denizgencayspacexfan.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RocketRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllRocketsFromApi(rocketDataList: MutableLiveData<List<RocketModel>>){
        val call: Call<List<RocketModel>> = apiService.getRockets()
        call.enqueue(object : Callback<List<RocketModel>> {
            override fun onResponse(
                call: Call<List<RocketModel>>,
                response: Response<List<RocketModel>>
            ) {
                if (response.isSuccessful){
                    rocketDataList.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<RocketModel>>, t: Throwable) {
                println("hoo: $t")
            }
        })
    }

}