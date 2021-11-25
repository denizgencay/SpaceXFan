package com.example.denizgencayspacexfan.ui.upcoming

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
import com.example.denizgencayspacexfan.ui.upcoming.detail.UpcomingDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    lateinit var upcomingRecyclerAdapter: UpcomingRecyclerAdapter
    lateinit var upcomingRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_upcoming, container, false)

        upcomingRecyclerView = view.findViewById(R.id.upcoming_launches_recycler_view)
        initRecyclerView()
        initViewModel()

        return view
    }

    private fun initRecyclerView(){
        // Initializing rockets recycler view for displaying the data to user
        upcomingRecyclerView.layoutManager = LinearLayoutManager(context)
        upcomingRecyclerAdapter = UpcomingRecyclerAdapter()
        upcomingRecyclerView.adapter = upcomingRecyclerAdapter
    }

    private fun initViewModel(){
        val viewModel: UpcomingViewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)
        viewModel.getUpcomingLiveDataObserver().observe(viewLifecycleOwner, Observer {
            if(it != null){
                upcomingRecyclerAdapter.setUpcomingListData(it)
                upcomingRecyclerAdapter.notifyDataSetChanged()
                upcomingRecyclerAdapter.setOnUpcomingCardClickedListener(object: UpcomingRecyclerAdapter.OnUpcomingCardListener{
                    override fun onCardButtonClicked(position: Int) {
                        //Navigating user to upcoming detail page and sending upcoming model
                        //To its constructor for detail information
                        println(position)
                        val currentFragment = UpcomingDetail(it[position])
                        activity?.supportFragmentManager!!.beginTransaction()
                                .replace(R.id.fragment_container, currentFragment, "fragmentId")
                                .commit()
                    }
                })
            }else{
                println("err")
            }
        })
        viewModel.loadUpcomingListOfData()
    }

}