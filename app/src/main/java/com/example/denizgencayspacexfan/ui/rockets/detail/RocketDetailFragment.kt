package com.example.denizgencayspacexfan.ui.rockets.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInFragment
import com.example.denizgencayspacexfan.ui.rockets.RocketsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_rocket_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class RocketDetailFragment @Inject constructor(private val rocketModel: RocketModel) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_rocket_detail, container, false)
        val rocketDescription: TextView = view.findViewById(R.id.rocket_detail_fragment_description_text)
        val rocketHeight: TextView = view.findViewById(R.id.rocket_detail_fragment_height_text)
        val rocketDiameter: TextView = view.findViewById(R.id.rocket_detail_fragment_diameter_text)
        val rocketMass: TextView = view.findViewById(R.id.rocket_detail_fragment_mass_text)
//        val rocketToLeo: TextView = view.findViewById(R.id.rocket_detail_fragment_payload_to_leo_text)
//        val rocketToGto: TextView = view.findViewById(R.id.rocket_detail_fragment_payload_to_gto_text)
//        val rocketToMars: TextView = view.findViewById(R.id.rocket_detail_fragment_payload_to_mars_text)
        val backButton: ImageView = view.findViewById(R.id.fragment_detail_back_button)
        val likeButton: ImageView = view.findViewById(R.id.fragment_detail_like_button)
        val dislikeButton: ImageView = view.findViewById(R.id.fragment_detail_dislike_button)

        rocketDescription.text = rocketModel.description
        rocketHeight.text = "${rocketModel.height.meters}m/${rocketModel.height.feet}ft"
        rocketDiameter.text = "${rocketModel.diameter.meters}m/${rocketModel.diameter.feet}ft"
        rocketMass.text = "${rocketModel.mass.kg}kg/${rocketModel.mass.lb}lb"

        if (rocketModel.isLiked){
            likeButton.isVisible = false
            dislikeButton.isVisible = true
        }else{
            dislikeButton.isVisible = false
            likeButton.isVisible = true
        }

        backButton.setOnClickListener {
            val currentFragment = RocketsFragment()
            activity?.supportFragmentManager!!.beginTransaction()
                    .replace(R.id.fragment_container, currentFragment, "fragmentId")
                    .commit()
        }
        return view
    }


}