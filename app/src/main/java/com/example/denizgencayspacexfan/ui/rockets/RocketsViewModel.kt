package com.example.denizgencayspacexfan.ui.rockets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.data.models.UserCollectionModel
import com.example.denizgencayspacexfan.network.respositories.FirebaseRepository
import com.example.denizgencayspacexfan.network.respositories.RocketRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RocketsViewModel @Inject constructor(

    private val rocketRepository: RocketRepository,
    private val firebaseRepository: FirebaseRepository,
    private var firebaseAuth: FirebaseAuth

): ViewModel() {

    private var rocketsDataList: MutableLiveData<List<RocketModel>> = MutableLiveData()

    fun getRocketLiveDataObserver(): MutableLiveData<List<RocketModel>> {
        return rocketsDataList
    }

    fun loadRocketListOfData(){
        rocketRepository.getAllRocketsFromApi(rocketsDataList)
    }

    fun appendLike(rocketId: String){
        firebaseRepository.appendLike(firebaseAuth.currentUser!!.uid, rocketId).addOnCompleteListener {
            println("suc")
        }
    }

    fun removeLike(rocketId: String){
        firebaseRepository.removeLike(firebaseAuth.currentUser!!.uid, rocketId).addOnCompleteListener {
            println("suc")
        }
    }

    fun getLikeStatus(): Task<DocumentSnapshot> {
        return firebaseRepository.getLikeStatus(firebaseAuth.currentUser!!.uid)
    }


}