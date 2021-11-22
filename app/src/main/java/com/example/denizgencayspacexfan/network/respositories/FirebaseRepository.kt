package com.example.denizgencayspacexfan.network.respositories

import com.example.denizgencayspacexfan.network.firebase.FirebaseSource
import java.util.*
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    fun signUpUser(email: String, password: String) = firebaseSource.signUpUser(email, password)

    fun signInUser(email: String, password: String) = firebaseSource.signInUser(email, password)

    fun saveUser(email: String,userId:String) = firebaseSource.saveUser(email,userId)

    fun fetchUser() = firebaseSource.fetchUser()

}