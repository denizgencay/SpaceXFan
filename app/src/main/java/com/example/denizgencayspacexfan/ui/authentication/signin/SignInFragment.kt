package com.example.denizgencayspacexfan.ui.authentication.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.UserModel
import com.example.denizgencayspacexfan.ui.authentication.signup.SignUpFragment
import com.example.denizgencayspacexfan.ui.authentication.signup.SignUpViewModel
import com.example.denizgencayspacexfan.ui.favorites.FavoritesFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(firebaseAuth.currentUser != null){
            val currentFragment = FavoritesFragment()
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, currentFragment, "fragmentId")
                .commit();
        }else{
            initViewModel()
        }


        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    private fun initViewModel() {

        val viewModel: SignInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        viewModel.signInUser("denizgencay@gmail.com","123456").observe(viewLifecycleOwner,{

        })
    }

}