package com.example.denizgencayspacexfan.network.firebase

import com.example.denizgencayspacexfan.data.models.RocketId
import com.example.denizgencayspacexfan.data.models.UserCollectionModel
import com.example.denizgencayspacexfan.data.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class FirebaseSource @Inject constructor(private val firebaseAuth: FirebaseAuth, private val firestore: FirebaseFirestore) {

    fun signUpUser(email:String,password:String) = firebaseAuth.createUserWithEmailAndPassword(email,password)

    fun signInUser(email: String,password: String) = firebaseAuth.signInWithEmailAndPassword(email,password)

    fun saveCollection(userId: String, userCollection: UserCollectionModel) = firestore.collection("users").document(userId).set(userCollection)

    fun appendLike(userId: String, rocketId: String) = firestore.collection("users").document(userId).update("rocketIds",FieldValue.arrayUnion(rocketId))

    fun removeLike(userId: String, rocketId: String) = firestore.collection("users").document(userId).update("rocketIds",FieldValue.arrayRemove(rocketId))

    fun getLikeStatus(userId:String) = firestore.collection("users").document(userId).get()

    fun fetchUser()=firestore.collection("users").get()

}