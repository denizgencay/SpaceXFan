package com.example.denizgencayspacexfan.ui.rockets.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.RocketModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RocketDetailFragment @Inject constructor(private val rocketModel: RocketModel) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_rocket_detail, container, false)
        setDataToView(view)
        return view
    }

    private fun setDataToView(view:View) {
        val rocketDescription: TextView = view.findViewById(R.id.rocket_detail_fragment_description_text)
        val rocketHeight: TextView = view.findViewById(R.id.rocket_detail_fragment_height_text)
        val rocketDiameter: TextView = view.findViewById(R.id.rocket_detail_fragment_diameter_text)
        val rocketMass: TextView = view.findViewById(R.id.rocket_detail_fragment_mass_text)
//        val rocketToLeo: TextView = view.findViewById(R.id.rocket_detail_fragment_payload_to_leo_text)
//        val rocketToGto: TextView = view.findViewById(R.id.rocket_detail_fragment_payload_to_gto_text)
//        val rocketToMars: TextView = view.findViewById(R.id.rocket_detail_fragment_payload_to_mars_text)
        rocketDescription.text = rocketModel.description
        rocketHeight.text = "${rocketModel.height.meters}m/${rocketModel.height.feet}ft"
        rocketDiameter.text = "${rocketModel.diameter.meters}m/${rocketModel.diameter.feet}ft"
        rocketMass.text = "${rocketModel.mass.kg}kg/${rocketModel.mass.lb}lb"
    }


}