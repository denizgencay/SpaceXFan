package com.example.denizgencayspacexfan.ui.favorites

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInFragment
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInViewModel
import com.example.denizgencayspacexfan.ui.authentication.signup.SignUpFragment
import com.example.denizgencayspacexfan.ui.rockets.RocketsRecyclerAdapter
import com.example.denizgencayspacexfan.ui.rockets.RocketsViewModel
import com.example.denizgencayspacexfan.ui.rockets.detail.RocketDetailFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    lateinit var favoritesRecyclerViewAdapter: RocketsRecyclerAdapter
    lateinit var favoritesRecyclerView: RecyclerView
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorites, container, false)
        val signOut: ImageView = view.findViewById(R.id.fragment_log_out_button)

        signOut.setOnClickListener {
            println("hello")
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("LOG OUT"){ dialog, _ ->
                        firebaseAuth.signOut()
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
        favoritesRecyclerView = view.findViewById(R.id.favorites_recycler_view)
        initRecyclerView()
        initViewModel()

        return view
    }

    private fun initRecyclerView() {
        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        favoritesRecyclerViewAdapter = RocketsRecyclerAdapter()
        favoritesRecyclerView.adapter = favoritesRecyclerViewAdapter
    }

    private fun initViewModel() {
        val viewModel: RocketsViewModel = ViewModelProvider(this).get(RocketsViewModel::class.java)
        viewModel.getRocketLiveDataObserver().observe(viewLifecycleOwner, {
            if (it != null) {
                viewModel.getLikeStatus().addOnSuccessListener { ls ->
                    val list: ArrayList<String> = ls.data?.get("rocketIds") as ArrayList<String>
                    var likedRockets:  ArrayList<RocketModel> = arrayListOf()
                    for (rocket in it) {
                        if (list.contains(rocket.id)) {
                            rocket.isLiked = true
                            likedRockets.add(rocket)
                        }
                    }
                    println(list.size)
                    favoritesRecyclerViewAdapter.setRocketListData(likedRockets)
                    favoritesRecyclerViewAdapter.notifyDataSetChanged()
                    favoritesRecyclerViewAdapter.setOnCardClickedListener(object : RocketsRecyclerAdapter.OnCardListener {
                        override fun onCardClicked(position: Int) {
                            val currentFragment = RocketDetailFragment(it[position],true)
                            activity?.supportFragmentManager!!.beginTransaction()
                                    .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                    .commit();
                        }

                        override fun onLikeClicked(position: Int) {
                            //Like button cannot clicked here because it's always invisible on this fragment.
                            //Calling this method is a must because this view uses the rockets adapter
                            //And it has a interface for like rockets
                        }

                        override fun onDislikeClicked(position: Int) {
                            if (it[position].isLiked) {
                                it[position].isLiked = false
                                favoritesRecyclerViewAdapter.notifyItemRemoved(position)
                                likedRockets.remove(it[position])
                                viewModel.removeLike(it[position].id)
                            }
                        }

                    })
                }
            } else {
                println("err")
            }
        })
        viewModel.loadRocketListOfData()
    }

}