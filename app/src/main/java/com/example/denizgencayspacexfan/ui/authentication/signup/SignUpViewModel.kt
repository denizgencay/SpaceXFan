package com.example.denizgencayspacexfan.ui.authentication.signup

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denizgencayspacexfan.data.models.UserModel
import com.example.denizgencayspacexfan.network.respositories.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val firebaseAuth: FirebaseAuth,
) :ViewModel() {

    private val userData = MutableLiveData<UserModel>()
    private val saveUserData = MutableLiveData<UserModel>()
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

    fun saveUser(email: String) {
        firebaseRepository.saveUser(email,firebaseAuth.currentUser!!.uid).addOnCompleteListener {
            if (it.isSuccessful) {
                saveUserData.postValue(UserModel(email, firebaseAuth.currentUser!!.uid))
            }else{
                println("error")
            }
        }
    }


}