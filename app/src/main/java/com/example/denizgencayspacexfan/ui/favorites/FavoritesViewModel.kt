package com.example.denizgencayspacexfan.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.network.respositories.FirebaseRepository
import com.example.denizgencayspacexfan.network.respositories.RocketRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(

        private val rocketRepository: RocketRepository,
        private val firebaseRepository: FirebaseRepository,
        private var firebaseAuth: FirebaseAuth

): ViewModel() {

    var rocketsData: MutableLiveData<List<RocketModel>> = MutableLiveData()

    fun getRocketByIdLiveDataObserver(): MutableLiveData<List<RocketModel>> {
        return rocketsData
    }

    fun loadRocketByIdListOfData(rocketIds:ArrayList<String>){
        rocketRepository.getRocketById(rocketsData,rocketIds)
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