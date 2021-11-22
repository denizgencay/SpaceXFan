package com.example.denizgencayspacexfan.ui.upcoming

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denizgencayspacexfan.data.models.UpcomingLaunchModel
import com.example.denizgencayspacexfan.network.respositories.UpcomingLaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpcomingViewModel @Inject constructor(
    private val upcomingLaunchRepository: UpcomingLaunchRepository
): ViewModel() {

    var upcomingLaunchDataList: MutableLiveData<List<UpcomingLaunchModel>> = MutableLiveData()

    fun getUpcomingLiveDataObserver(): MutableLiveData<List<UpcomingLaunchModel>> {
        return upcomingLaunchDataList
    }

    fun loadUpcomingListOfData(){
        upcomingLaunchRepository.getAllUpcomingLaunchesFromApi(upcomingLaunchDataList)
    }

}