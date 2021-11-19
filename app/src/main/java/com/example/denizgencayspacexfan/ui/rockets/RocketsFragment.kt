package com.example.denizgencayspacexfan.ui.rockets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.ui.rockets.detail.RocketDetailFragment
import com.example.denizgencayspacexfan.ui.upcoming.detail.UpcomingDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_rockets.*

@AndroidEntryPoint
class RocketsFragment : Fragment() {

    lateinit var rocketsRecyclerViewAdapter: RocketsRecyclerAdapter
    lateinit var rocketsRecyclerView: RecyclerView

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
            if(it != null){
                rocketsRecyclerViewAdapter.setRocketListData(it)
                rocketsRecyclerViewAdapter.notifyDataSetChanged()
                rocketsRecyclerViewAdapter.setOnCardClickedListener(object :RocketsRecyclerAdapter.OnCardListener{
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
        viewModel.loadRocketListOfData()
    }

}