package com.example.denizgencayspacexfan.network.firebase

import com.example.denizgencayspacexfan.data.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import javax.inject.Inject

class FirebaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore) {

    fun signUpUser(email:String,password:String) = firebaseAuth.createUserWithEmailAndPassword(email,password)

    fun signInUser(email: String,password: String) = firebaseAuth.signInWithEmailAndPassword(email,password)

    fun saveUser(email: String,userId: String)=firestore.collection("users").document(email).set(UserModel(email = email,userId = userId))

    fun fetchUser()=firestore.collection("users").get()

}