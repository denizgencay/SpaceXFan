package com.example.denizgencayspacexfan.ui.authentication.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val userData = MutableLiveData<UserModel>()

    fun signInUser(email: String, password: String): LiveData<UserModel> {

        firebaseRepository.signInUser(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseRepository.fetchUser().addOnCompleteListener { userTask ->
                    if (userTask.isSuccessful) {
                        userTask.result?.documents?.forEach {
                            if (it.data!!["email"] == email) {
                                val userId = it.data?.getValue("userId")
                                userData.postValue(
                                    UserModel(
                                        firebaseAuth.currentUser?.email!!, userId.toString())
                                ) } }
                    } else {
                        println(userTask.exception)
                    }
                }
            } else {
              println(task.exception)
            } }
        return userData
    }

}