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
        //Alerting the user that the user is trying to log out
        signOut.setOnClickListener {
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
        // Initializing rockets recycler view for displaying the data to user
        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        favoritesRecyclerViewAdapter = RocketsRecyclerAdapter()
        favoritesRecyclerView.adapter = favoritesRecyclerViewAdapter
    }

    private fun initViewModel() {
        //Data and view binding using hilt view model
        val viewModel: RocketsViewModel = ViewModelProvider(this).get(RocketsViewModel::class.java)
        viewModel.getRocketLiveDataObserver().observe(viewLifecycleOwner, {
            if (it != null) {
                viewModel.getLikeStatus().addOnSuccessListener { ls ->
                    //Creating an array list for users liked rockets
                    //The liked rocket data stored in firebase
                    //We collect the data under users document and under that the rocketIds collection
                    val list: ArrayList<String> = ls.data?.get("rocketIds") as ArrayList<String>
                    val likedRockets: ArrayList<RocketModel> = arrayListOf()
                    for (rocket in it) {
                        if (list.contains(rocket.id)) {
                            rocket.isLiked = true
                            likedRockets.add(rocket)
                        }
                    }
                    favoritesRecyclerViewAdapter.setRocketListData(likedRockets)
                    favoritesRecyclerViewAdapter.notifyDataSetChanged()
                    favoritesRecyclerViewAdapter.setOnCardClickedListener(object :
                        RocketsRecyclerAdapter.OnCardListener {
                        override fun onCardClicked(position: Int) {
                            println(position)
                            val currentFragment = RocketDetailFragment(it[position], true)
                            activity?.supportFragmentManager!!.beginTransaction()
                                .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                .commit();
                        }

                        override fun onLikeClicked(position: Int) {
                            //Like button cannot clicked here because it's always invisible on this fragment.
                            //Calling this method is a must because this view uses the rockets adapter
                            //And it has a interface for liked rockets
                        }

                        override fun onDislikeClicked(position: Int) {
                            //Removing liked rockets
                            println(position)
                            println(it[position].name)
                            println(it[position].isLiked)
                            //Liked rockets and it(List of rocket models) has different size of data
                            //The data has to removed from the correct item from liked rockets array for updating view
                            //And also has to removed from it(List of rocket model)  for updating the database
                            //Correcting indexes for both array lists
                            var index = 0
                            for(rocket in it){
                                if (rocket.id == likedRockets[position].id){
                                    index = it.indexOf(rocket)
                                    break
                                }
                            }
                            println(index)
                            println(it[index].name)
                            println(it[index].isLiked)
                            if (it[index].isLiked) {
                                it[index].isLiked = false
                                println("clicked")
                                favoritesRecyclerViewAdapter.notifyItemRemoved(index)
                                likedRockets.remove(it[index])
                                viewModel.removeLike(it[index].id)
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