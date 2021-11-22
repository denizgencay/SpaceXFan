package com.example.denizgencayspacexfan.ui.rockets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.network.respositories.RocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(

    private val rocketRepository: RocketRepository

): ViewModel() {

    var rocketsDataList: MutableLiveData<List<RocketModel>> = MutableLiveData()

    fun getRocketLiveDataObserver(): MutableLiveData<List<RocketModel>> {
        return rocketsDataList
    }

    fun loadRocketListOfData(){
        rocketRepository.getAllRocketsFromApi(rocketsDataList)
    }


}