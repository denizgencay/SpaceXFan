package com.example.denizgencayspacexfan.ui.rockets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.UserCollectionModel
import com.example.denizgencayspacexfan.ui.rockets.detail.RocketDetailFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RocketsFragment : Fragment() {

    lateinit var rocketsRecyclerViewAdapter: RocketsRecyclerAdapter
    lateinit var rocketsRecyclerView: RecyclerView
    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_rockets, container, false)

        rocketsRecyclerView = view.findViewById(R.id.rockets_recycler_view)
        initRecyclerView()
        initViewModel()

        return view
    }

    private fun initRecyclerView(){
        rocketsRecyclerView.layoutManager = LinearLayoutManager(context)
        rocketsRecyclerViewAdapter = RocketsRecyclerAdapter()
        rocketsRecyclerView.adapter = rocketsRecyclerViewAdapter
    }
    private fun initViewModel(){
        val viewModel:RocketsViewModel = ViewModelProvider(this).get(RocketsViewModel::class.java)
        viewModel.getRocketLiveDataObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (firebaseAuth.currentUser != null) {
                    viewModel.getLikeStatus().addOnSuccessListener { ls ->
                        val list: ArrayList<String> = ls.data?.get("rocketIds") as ArrayList<String>
                        for (rocket in it) {
                            if (list.contains(rocket.id)) {
                                rocket.isLiked = true
                            }
                        }
                        rocketsRecyclerViewAdapter.setRocketListData(it)
                        rocketsRecyclerViewAdapter.notifyDataSetChanged()
                        rocketsRecyclerViewAdapter.setOnCardClickedListener(object : RocketsRecyclerAdapter.OnCardListener {
                            override fun onCardClicked(position: Int) {
                                val currentFragment = RocketDetailFragment(it[position])
                                activity?.supportFragmentManager!!.beginTransaction()
                                        .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                        .commit();
                            }

                            override fun onLikeClicked(position: Int) {
                                println("like")
                                if (!it[position].isLiked) {
                                    it[position].setLikeStatus(true)
                                    rocketsRecyclerViewAdapter.notifyItemChanged(position)
                                    viewModel.appendLike(it[position].id)
                                    println("hello")
                                }
                            }

                            override fun onDislikeClicked(position: Int) {
                                println("dislike")
                                if (it[position].isLiked) {
                                    it[position].setLikeStatus(false)
                                    rocketsRecyclerViewAdapter.notifyItemChanged(position)
                                    viewModel.removeLike(it[position].id)
                                }
                            }
                        })
                    }
                } else {
                    rocketsRecyclerViewAdapter.setRocketListData(it)
                    rocketsRecyclerViewAdapter.notifyDataSetChanged()
                    rocketsRecyclerViewAdapter.setOnCardClickedListener(object : RocketsRecyclerAdapter.OnCardListener {
                        override fun onCardClicked(position: Int) {
                            println("asd")
                        }

                        override fun onLikeClicked(position: Int) {
                            println("asd")
                        }

                        override fun onDislikeClicked(position: Int) {
                            println("asd")
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