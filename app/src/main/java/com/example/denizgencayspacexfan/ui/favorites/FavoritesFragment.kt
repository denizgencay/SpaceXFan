package com.example.denizgencayspacexfan.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.transition.Transition
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInFragment
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInViewModel
import com.example.denizgencayspacexfan.ui.authentication.signup.SignUpFragment
import com.example.denizgencayspacexfan.ui.rockets.RocketsRecyclerAdapter
import com.example.denizgencayspacexfan.ui.rockets.RocketsViewModel
import com.example.denizgencayspacexfan.ui.rockets.detail.RocketDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    lateinit var favoritesRecyclerViewAdapter: RocketsRecyclerAdapter
    lateinit var favoritesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorites, container, false)

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
                            val currentFragment = RocketDetailFragment(it[position])
                            activity?.supportFragmentManager!!.beginTransaction()
                                    .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                    .commit();
                        }

                        override fun onLikeClicked(position: Int) {
                            if (it[position].isLiked) {
                                it[position].isLiked = false
                                viewModel.removeLike(it[position].id)
                                likedRockets.remove(it[position])
                                favoritesRecyclerViewAdapter.notifyDataSetChanged()
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