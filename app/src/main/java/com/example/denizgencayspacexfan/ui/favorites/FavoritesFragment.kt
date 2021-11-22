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
import com.example.denizgencayspacexfan.ui.rockets.detail.RocketDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    lateinit var favoritesRecyclerViewAdapter: FavoritesRecyclerAdapter
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

    private fun initViewModel() {
       val viewModel: FavoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        viewModel.getRocketByIdLiveDataObserver().observe(viewLifecycleOwner,{
            if(it != null){
                favoritesRecyclerViewAdapter.setRocketListData(it)
                favoritesRecyclerViewAdapter.notifyDataSetChanged()
                favoritesRecyclerViewAdapter.setOnCardClickedListener(object :FavoritesRecyclerAdapter.OnCardListener{
                    override fun onCardClicked(position: Int) {
                        val currentFragment = RocketDetailFragment(it[position])
                        activity?.supportFragmentManager!!.beginTransaction()
                                .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                .commit();
                    }
                })
            }else{
                println("err")
            }
        })
        val stringArray:ArrayList<String> = ArrayList()
        stringArray.add("5e9d0d96eda699382d09d1ee")
        stringArray.add("5e9d0d95eda69955f709d1eb")
        stringArray.add("5e9d0d95eda69974db09d1ed")
        viewModel.loadRocketByIdListOfData(stringArray)

    }

    private fun initRecyclerView() {
        favoritesRecyclerView.layoutManager = LinearLayoutManager(context)
        favoritesRecyclerViewAdapter = FavoritesRecyclerAdapter()
        favoritesRecyclerView.adapter = favoritesRecyclerViewAdapter
    }

}