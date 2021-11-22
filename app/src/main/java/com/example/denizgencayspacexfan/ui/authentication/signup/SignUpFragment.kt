package com.example.denizgencayspacexfan.ui.authentication.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.ui.rockets.RocketsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view:View =  inflater.inflate(R.layout.fragment_sign_up, container, false)

        initViewModel()
        return view
    }

    private fun initViewModel() {

        val viewModel:SignUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.signUpUser("denizgencay@gmail.com","123456").observe(viewLifecycleOwner,{
            if(it != null){
                viewModel.saveUser("denizgencay@gmail.com")
            }
        })
    }

}