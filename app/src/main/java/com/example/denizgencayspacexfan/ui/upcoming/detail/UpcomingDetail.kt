package com.example.denizgencayspacexfan.ui.upcoming.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.UpcomingLaunchModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingDetail @Inject constructor(private val upcomingLaunchModel: UpcomingLaunchModel): Fragment()   {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_upcoming_detail, container, false)
        //setDataToView()
        return view
    }

    private fun setDataToView() {

    }

}