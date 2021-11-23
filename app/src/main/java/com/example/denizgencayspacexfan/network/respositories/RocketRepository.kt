package com.example.denizgencayspacexfan.network.respositories

import androidx.lifecycle.MutableLiveData
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.network.ApiService
import com.google.protobuf.Empty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RocketRepository @Inject constructor(private val apiService: ApiService) {

    fun getAllRocketsFromApi(rocketDataList: MutableLiveData<List<RocketModel>>){
        val call: Call<List<RocketModel>> = apiService.getRockets()
        call.enqueue(object : Callback<List<RocketModel>> {
            override fun onResponse(call: Call<List<RocketModel>>, response: Response<List<RocketModel>>
            ) {
                if (response.isSuccessful){
                    rocketDataList.postValue(response.body())
                }
            }
            override fun onFailure(call: Call<List<RocketModel>>, t: Throwable) {
                println(t)
            }
        })
    }

    fun getRocketById(rocketData: MutableLiveData<List<RocketModel>>, rocketIds:ArrayList<String>){
        val listOfReturn: ArrayList<RocketModel> = arrayListOf()
        for (rocketId:String in rocketIds){
            val call: Call<RocketModel> = apiService.getRocketsById(rocketId)
            call.enqueue(object: Callback<RocketModel>{
                override fun onResponse(call: Call<RocketModel>, response: Response<RocketModel>) {
                    listOfReturn.add(response.body()!!)
                    if(listOfReturn.size == rocketIds.size){
                        rocketData.postValue(listOfReturn)
                    }
                }
                override fun onFailure(call: Call<RocketModel>, t: Throwable) {
                    println(t)
                }
            })
        }
    }
}