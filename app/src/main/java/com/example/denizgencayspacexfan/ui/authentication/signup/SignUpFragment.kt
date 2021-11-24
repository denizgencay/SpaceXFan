package com.example.denizgencayspacexfan.ui.authentication.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.UserCollectionModel
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View =  inflater.inflate(R.layout.fragment_sign_up, container, false)
        val emailText: EditText = view.findViewById(R.id.fragment_sign_up_email_text)
        val passwordText: EditText = view.findViewById(R.id.fragment_sign_up_password_text)
        val signUpButton: Button = view.findViewById(R.id.sign_up_button)
        val backButton: ImageView = view.findViewById(R.id.fragment_sign_up_back_button)

        signUpButton.setOnClickListener {
            val email: String = emailText.text.toString()
            val password: String = passwordText.text.toString()
            if (email.isEmpty() && password.isEmpty()){
                Toast.makeText(context, "Please fill all the blanks", Toast.LENGTH_SHORT).show()
            }else if (password.isEmpty()){
                Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }else if(email.isEmpty()){
                Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
            }else{
                initViewModel(email,password)
            }
        }

        backButton.setOnClickListener {
            val currentFragment = SignInFragment()
            activity?.supportFragmentManager!!.beginTransaction()
                    .replace(R.id.fragment_container, currentFragment, "fragmentId")
                    .commit()
        }

        return view
    }

    private fun initViewModel(email: String, password: String) {

        val viewModel: SignUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.signUpUser(email,password).observe(viewLifecycleOwner,{
            if(it != null){
                val userCollection = UserCollectionModel(arrayListOf())
                viewModel.saveCollection(userCollection)
            }
        })
    }

}