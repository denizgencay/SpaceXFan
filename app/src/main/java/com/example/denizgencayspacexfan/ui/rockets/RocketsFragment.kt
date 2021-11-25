package com.example.denizgencayspacexfan.ui.rockets

import android.app.AlertDialog
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
import com.example.denizgencayspacexfan.ui.authentication.signin.SignInFragment
import com.example.denizgencayspacexfan.ui.rockets.detail.RocketDetailFragment
import com.example.denizgencayspacexfan.ui.upcoming.detail.UpcomingDetail
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
        // Initializing rockets recycler view for displaying the data to user
        rocketsRecyclerView.layoutManager = LinearLayoutManager(context)
        rocketsRecyclerViewAdapter = RocketsRecyclerAdapter()
        rocketsRecyclerView.adapter = rocketsRecyclerViewAdapter
    }
    private fun initViewModel(){
        //Data and view binding using hilt view model
        val viewModel:RocketsViewModel = ViewModelProvider(this).get(RocketsViewModel::class.java)
        viewModel.getRocketLiveDataObserver().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (firebaseAuth.currentUser != null) {
                    viewModel.getLikeStatus().addOnSuccessListener { ls ->
                        //Creating an array list for users liked rockets
                        //The liked rocket data stored in firebase
                        //We collect the data under users document and under that the rocketIds collection
                        val list: ArrayList<String> = ls.data?.get("rocketIds") as ArrayList<String>
                        for (rocket in it) {
                            if (list.contains(rocket.id)) {
                                rocket.isLiked = true
                            }
                        }
                        rocketsRecyclerViewAdapter.setRocketListData(it)
                        rocketsRecyclerViewAdapter.notifyDataSetChanged()
                        rocketsRecyclerViewAdapter.setOnCardClickedListener(object : RocketsRecyclerAdapter.OnCardListener {
                            //Navigating user to selected rockets detail
                            //Rocket detail has a constructor type of RocketModel
                            //That rocket model passes the information to the detail fragment from this(RocketFragment) fragment
                            override fun onCardClicked(position: Int) {
                                val currentFragment = RocketDetailFragment(it[position],false)
                                activity?.supportFragmentManager!!.beginTransaction()
                                        .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                        .commit();
                            }

                            override fun onLikeClicked(position: Int) {
                                println("like")
                                if (!it[position].isLiked) {
                                    it[position].isLiked = true
                                    rocketsRecyclerViewAdapter.notifyItemChanged(position)
                                    viewModel.appendLike(it[position].id)
                                }
                            }

                            override fun onDislikeClicked(position: Int) {
                                println("dislike")
                                if (it[position].isLiked) {
                                    it[position].isLiked = false
                                    rocketsRecyclerViewAdapter.notifyItemChanged(position)
                                    viewModel.removeLike(it[position].id)
                                }
                            }
                        })
                    }
                } else {
                    //Handling data when user is not logged in
                    rocketsRecyclerViewAdapter.setRocketListData(it)
                    rocketsRecyclerViewAdapter.notifyDataSetChanged()
                    rocketsRecyclerViewAdapter.setOnCardClickedListener(object : RocketsRecyclerAdapter.OnCardListener {
                        override fun onCardClicked(position: Int) {
                            val currentFragment = RocketDetailFragment(it[position],false)
                            activity?.supportFragmentManager!!.beginTransaction()
                                .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                .commit()
                        }

                        override fun onLikeClicked(position: Int) {
                            //Alerting user that the user is not logged in
                            //If the user wants to like a rocket the user
                            //Has to login or create an account
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

                        override fun onDislikeClicked(position: Int) {
                            //User cannot dislike a rocket if user is not logged in
                            //Rocket fragment adapter uses this interface for dislike operation
                            //Because of that, this method has to called here
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