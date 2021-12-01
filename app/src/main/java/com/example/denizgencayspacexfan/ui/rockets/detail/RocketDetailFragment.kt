package com.example.denizgencayspacexfan.ui.rockets.detail

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInFragment
import com.example.denizgencayspacexfan.ui.favorites.FavoritesFragment
import com.example.denizgencayspacexfan.ui.rockets.RocketsFragment
import com.example.denizgencayspacexfan.ui.rockets.RocketsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_rocket_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class RocketDetailFragment @Inject constructor(private val rocketModel: RocketModel, private val isComingFromFavorites:Boolean) : Fragment() {
    @Inject
    lateinit var firebaseAuth:FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_rocket_detail, container, false)
        val rocketDescription: TextView = view.findViewById(R.id.rocket_detail_fragment_description_text)
        val rocketHeight: TextView = view.findViewById(R.id.rocket_detail_fragment_height_text)
        val rocketDiameter: TextView = view.findViewById(R.id.rocket_detail_fragment_diameter_text)
        val rocketMass: TextView = view.findViewById(R.id.rocket_detail_fragment_mass_text)
        val imageView: CircleImageView = view.findViewById(R.id.rocket_detail_fragment_image)
        val backButton: ImageView = view.findViewById(R.id.fragment_detail_back_button)
        val likeButton: ImageView = view.findViewById(R.id.fragment_detail_like_button)
        val dislikeButton: ImageView = view.findViewById(R.id.fragment_detail_dislike_button)
        val viewModel: RocketsViewModel = ViewModelProvider(this).get(RocketsViewModel::class.java)
        val imageRecyclerView: RecyclerView = view.findViewById(R.id.image_recycler_view)
        imageRecyclerView.isNestedScrollingEnabled = false
        val imageAdapter = ImageRecyclerAdapter()
        imageRecyclerView.layoutManager = GridLayoutManager(context,2)
        imageRecyclerView.adapter = imageAdapter

        if (rocketModel.flickrImages != null){
            imageAdapter.setImageListData(rocketModel.flickrImages)
            imageAdapter.notifyDataSetChanged()
        }

        //Setting data to view fields
        if(rocketModel.description != null) rocketDescription.text = rocketModel.description
        if(rocketModel.height != null) rocketHeight.text = "${rocketModel.height.meters}m/${rocketModel.height.feet}ft"
        if(rocketModel.diameter != null) rocketDiameter.text = "${rocketModel.diameter.meters}m/${rocketModel.diameter.feet}ft"
        if(rocketModel.mass != null) rocketMass.text = "${rocketModel.mass.kg}kg/${rocketModel.mass.lb}lb"

        //Setting image to image view with the help of picasso library
        if(rocketModel.flickrImages.isNotEmpty()){
            Picasso.get().load(rocketModel.flickrImages[0]).into(imageView)
        }

        //Setting like icon status, there are two icons with filled and empty
        //If the rocket is liked empty icon is invisible and filled one is visible
        //If the rocket is not liked filled icon is invisible and empty one is visible
        if (rocketModel.isLiked){
            likeButton.isVisible = false
            dislikeButton.isVisible = true
        }else{
            dislikeButton.isVisible = false
            likeButton.isVisible = true
        }


        //Setting rockets like status to true
            likeButton.setOnClickListener {
                if(firebaseAuth.currentUser != null){
                    if (!rocketModel.isLiked){
                        rocketModel.isLiked = true
                        likeButton.isVisible = false
                        dislikeButton.isVisible = true
                        viewModel.appendLike(rocketModel.id)
                    }
                }else{
                    val alertDialog = AlertDialog.Builder(context)
                    alertDialog.setTitle("You have to login for like a rocket.")
                        .setCancelable(false)
                        .setPositiveButton("LOGIN"){ dialog, _ ->
                            val currentFragment = SignInFragment()
                            activity?.supportFragmentManager!!.beginTransaction()
                                .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                .commit();
                            dialog.dismiss()
                        }
                        .setNegativeButton("CANCEL"){dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }

        //Setting rockets like status to false
        dislikeButton.setOnClickListener {
            if (rocketModel.isLiked){
                rocketModel.isLiked = false
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