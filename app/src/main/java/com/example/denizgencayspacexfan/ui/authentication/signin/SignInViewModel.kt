package com.example.denizgencayspacexfan.ui.authentication.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denizgencayspacexfan.data.Resource
import com.example.denizgencayspacexfan.data.models.UserModel
import com.example.denizgencayspacexfan.network.respositories.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(

    private var firebaseRepository: FirebaseRepository,
    private var firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val userData = MutableLiveData<Resource<UserModel>>()

    fun signInUser(email: String, password: String): MutableLiveData<Resource<UserModel>> {

        firebaseRepository.signInUser(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseRepository.fetchUser().addOnCompleteListener { userTask ->
                    if (userTask.isSuccessful) {
                        println("suc")
                            userData.postValue(
                                Resource.success(UserModel(
                                        firebaseAuth.currentUser?.email!!, firebaseAuth.currentUser?.uid!!))
                            )
                    } else {
                        userData.postValue(Resource.error(null,userTask.exception?.message.toString()))
                    }
                }
            } else {
              userData.postValue(Resource.error(null,task.exception?.message.toString()))
            } }
        return userData
    }
}