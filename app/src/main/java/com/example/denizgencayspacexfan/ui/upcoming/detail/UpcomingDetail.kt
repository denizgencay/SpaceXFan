package com.example.denizgencayspacexfan.ui.upcoming.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.UpcomingLaunchModel
import com.example.denizgencayspacexfan.ui.upcoming.UpcomingFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class UpcomingDetail @Inject constructor(private val upcomingLaunchModel: UpcomingLaunchModel): Fragment()   {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_upcoming_detail, container, false)
        val upcomingImage: CircleImageView = view.findViewById(R.id.upcoming_detail_fragment_image)
        val backButton: ImageView = view.findViewById(R.id.fragment_upcoming_back_button)
        val detailText: TextView = view.findViewById(R.id.upcoming_detail_fragment_description_text)
        val nameText: TextView = view.findViewById(R.id.upcoming_detail_fragment_name_text)
        val dateText: TextView = view.findViewById(R.id.upcoming_detail_fragment_date_text)
        val flightNumberText: TextView = view.findViewById(R.id.upcoming_detail_fragment_flight_number_text)

        //Setting upcoming data to to view
        if (upcomingLaunchModel.links.patch.large != null){
            Picasso.get().load(upcomingLaunchModel.links.patch.large).into(upcomingImage)
        }
        if(upcomingLaunchModel.details != null){
            detailText.text = upcomingLaunchModel.details
        }
        if (upcomingLaunchModel.name != null){
            nameText.text = upcomingLaunchModel.name
        }
        if(upcomingLaunchModel.dateUtc != null){
            dateText.text = modifyDateLayout(upcomingLaunchModel.dateUtc)
        }
        if(upcomingLaunchModel.flightNumber != null){
            flightNumberText.text = upcomingLaunchModel.flightNumber.toString()
        }

        backButton.setOnClickListener {
            val currentFragment = UpcomingFragment()
            activity?.supportFragmentManager!!.beginTransaction()
                .replace(R.id.fragment_container, currentFragment, "fragmentId")
                .commit()
        }
        return view
    }
    //Date formatting
    @Throws(ParseException::class)
    private fun modifyDateLayout(inputDate: String): String? {
        val date: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssssss").parse(inputDate)
        return SimpleDateFormat("dd.MM.yyyy").format(date)
    }

}