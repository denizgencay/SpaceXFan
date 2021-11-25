package com.example.denizgencayspacexfan.ui.authentication.signin

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.Status
import com.example.denizgencayspacexfan.data.models.UserModel
import com.example.denizgencayspacexfan.ui.authentication.signup.SignUpFragment
import com.example.denizgencayspacexfan.ui.authentication.signup.SignUpViewModel
import com.example.denizgencayspacexfan.ui.favorites.FavoritesFragment
import com.example.denizgencayspacexfan.ui.rockets.detail.RocketDetailFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sign_in.*
import java.security.Key
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class SignInFragment : Fragment() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_sign_in, container, false)
        val emailText: EditText = view.findViewById(R.id.fragment_sign_in_email_text)
        val passwordText: EditText = view.findViewById(R.id.fragment_sign_in_password_text)
        val loginButton: Button = view.findViewById(R.id.sign_in_button)
        val signUpButton: TextView = view.findViewById(R.id.sign_in_fragment_sign_up_button)
        if(firebaseAuth.currentUser != null){
            val currentFragment = FavoritesFragment()
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, currentFragment, "fragmentId")
                .commit();
        }
        fun Context.toast(message: CharSequence) =
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        loginButton.setOnClickListener{
            val email: String = emailText.text.toString()
            val password: String = passwordText.text.toString()
            if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(context, "Please fill enter your credentials", Toast.LENGTH_SHORT).show()
            }else if (password.isEmpty()){
                Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }else if(email.isEmpty()){
                Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            }else{
                initViewModel(email,password)
            }
        }

        signUpButton.setOnClickListener {
            val currentFragment = SignUpFragment()
            activity?.supportFragmentManager!!.beginTransaction()
                    .replace(R.id.fragment_container, currentFragment, "fragmentId")
                    .commit()
        }

        return view
    }

    private fun initViewModel(email: String, password: String) {

        val viewModel: SignInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        viewModel.signInUser(email,password).observe(viewLifecycleOwner,{
            when(it.status){

                Status.LOADING -> {
                    println("loading")
                }

                Status.SUCCESS ->{
                    activity?.runOnUiThread {
                        val currentFragment = FavoritesFragment()
                        activity?.supportFragmentManager!!.beginTransaction()
                                .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                .commit()
                    }
                }
                Status.ERROR ->{
                    println("no")
                }
            }

        })
    }

}