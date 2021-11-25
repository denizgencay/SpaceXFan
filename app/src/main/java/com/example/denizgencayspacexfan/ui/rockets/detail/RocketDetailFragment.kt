package com.example.denizgencayspacexfan.ui.rockets.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInFragment
import com.example.denizgencayspacexfan.ui.favorites.FavoritesFragment
import com.example.denizgencayspacexfan.ui.rockets.RocketsFragment
import com.example.denizgencayspacexfan.ui.rockets.RocketsViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_rocket_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class RocketDetailFragment @Inject constructor(private val rocketModel: RocketModel, private val isComingFromFavorites:Boolean) : Fragment() {

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
        val imageView: CircleImageView = view.findViewById(R.id.rocket_detail_fragment_image)
        val backButton: ImageView = view.findViewById(R.id.fragment_detail_back_button)
        val likeButton: ImageView = view.findViewById(R.id.fragment_detail_like_button)
        val dislikeButton: ImageView = view.findViewById(R.id.fragment_detail_dislike_button)

        rocketDescription.text = rocketModel.description
        rocketHeight.text = "${rocketModel.height.meters}m/${rocketModel.height.feet}ft"
        rocketDiameter.text = "${rocketModel.diameter.meters}m/${rocketModel.diameter.feet}ft"
        rocketMass.text = "${rocketModel.mass.kg}kg/${rocketModel.mass.lb}lb"

        if(rocketModel.flickrImages.isNotEmpty()){
            Picasso.get().load(rocketModel.flickrImages[0]).into(imageView)
        }

        if (rocketModel.isLiked){
            likeButton.isVisible = false
            dislikeButton.isVisible = true
        }else{
            dislikeButton.isVisible = false
            likeButton.isVisible = true
        }

        val viewModel: RocketsViewModel = ViewModelProvider(this).get(RocketsViewModel::class.java)

        likeButton.setOnClickListener {
            if (!rocketModel.isLiked){
                rocketModel.setLikeStatus(true)
                likeButton.isVisible = false
                dislikeButton.isVisible = true
                viewModel.appendLike(rocketModel.id)
            }
        }

        dislikeButton.setOnClickListener {
            if (rocketModel.isLiked){
                rocketModel.setLikeStatus(false)
                dislikeButton.isVisible = false
                likeButton.isVisible = true
                viewModel.removeLike(rocketModel.id)
            }
        }

        backButton.setOnClickListener {
            if (isComingFromFavorites){
                val currentFragment = FavoritesFragment()
                activity?.supportFragmentManager!!.beginTransaction()
                        .replace(R.id.fragment_container, currentFragment, "fragmentId")
                        .commit()
            }else{
                val currentFragment = RocketsFragment()
                activity?.supportFragmentManager!!.beginTransaction()
                        .replace(R.id.fragment_container, currentFragment, "fragmentId")
                        .commit()
            }
        }
        return view
    }

}