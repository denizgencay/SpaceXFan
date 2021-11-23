package com.example.denizgencayspacexfan.network.respositories

import com.example.denizgencayspacexfan.data.models.UserCollectionModel
import com.example.denizgencayspacexfan.network.firebase.FirebaseSource
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    fun signUpUser(email: String, password: String) = firebaseSource.signUpUser(email, password)

    fun signInUser(email: String, password: String) = firebaseSource.signInUser(email, password)

    fun saveCollection(userId:String, userCollection: UserCollectionModel) = firebaseSource.saveCollection(userId, userCollection)

    fun appendLike(userId:String, rocketId: String) = firebaseSource.appendLike(userId, rocketId)

    fun removeLike(userId:String, rocketId: String) = firebaseSource.removeLike(userId, rocketId)

    fun getLikeStatus(userId:String) = firebaseSource.getLikeStatus(userId)

    fun fetchUser() = firebaseSource.fetchUser()

}