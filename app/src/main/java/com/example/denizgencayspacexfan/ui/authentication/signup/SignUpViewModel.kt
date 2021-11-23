package com.example.denizgencayspacexfan.ui.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denizgencayspacexfan.data.models.UserCollectionModel
import com.example.denizgencayspacexfan.data.models.UserModel
import com.example.denizgencayspacexfan.network.respositories.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val firebaseAuth: FirebaseAuth,
) :ViewModel() {

    private val userData = MutableLiveData<UserModel>()

    fun signUpUser(email: String, password: String): LiveData<UserModel> {

        firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
            if (it.result?.signInMethods?.size == 0) {
                firebaseRepository.signUpUser(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        userData.postValue(UserModel( email = email,userId = firebaseAuth.currentUser!!.uid))
                    } else {
                        println("error")
                    }
                }
            }
        }

    return userData
    }

    fun saveCollection(userCollection: UserCollectionModel) {
        firebaseRepository.saveCollection(firebaseAuth.currentUser!!.uid,userCollection).addOnCompleteListener {
            if (it.isSuccessful) {
                println("suc")
            }else{
                println("error")
            }
        }
    }


}