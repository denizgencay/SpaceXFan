package com.example.denizgencayspacexfan.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.network.respositories.RocketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(

        private val rocketRepository: RocketRepository

): ViewModel() {

    var rocketsData: MutableLiveData<List<RocketModel>> = MutableLiveData()

    fun getRocketByIdLiveDataObserver(): MutableLiveData<List<RocketModel>> {
        return rocketsData
    }

    fun loadRocketByIdListOfData(rocketIds:ArrayList<String>){
        rocketRepository.getRocketById(rocketsData,rocketIds)
    }

}