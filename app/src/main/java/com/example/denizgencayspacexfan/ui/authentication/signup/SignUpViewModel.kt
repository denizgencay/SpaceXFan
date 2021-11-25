package com.example.denizgencayspacexfan.ui.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denizgencayspacexfan.data.Resource
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

    private val userData = MutableLiveData<Resource<UserModel>>()

    fun signUpUser(email: String, password: String): MutableLiveData<Resource<UserModel>> {

        firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
            if (it.result?.signInMethods?.size == 0) {
                firebaseRepository.signUpUser(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        userData.postValue(Resource.success(UserModel( email = email,userId = firebaseAuth.currentUser!!.uid)))
                    } else {
                        userData.postValue(Resource.error(null,task.exception.toString()))
                    }
                }
            }else{
                userData.postValue(Resource.error(null,it.exception.toString()))
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