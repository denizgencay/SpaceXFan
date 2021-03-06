package com.example.denizgencayspacexfan.ui.upcoming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.UpcomingLaunchModel
import kotlinx.android.synthetic.main.upcoming_launch_card.view.*

class UpcomingRecyclerAdapter: RecyclerView.Adapter<UpcomingRecyclerAdapter.UpcomingViewHolder>(){

    private var upcomingListData: List<UpcomingLaunchModel>? = null
    private lateinit var listener: OnUpcomingCardListener

    //Creating the interface for handling clicks inside fragment
    interface OnUpcomingCardListener{
        fun onCardButtonClicked(position: Int)
    }

    fun setOnUpcomingCardClickedListener(listener: OnUpcomingCardListener){
        this.listener = listener
    }

    fun setUpcomingListData(upcomingListData: List<UpcomingLaunchModel>?){
        this.upcomingListData = upcomingListData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.upcoming_launch_card,parent,false)
        return UpcomingViewHolder(itemView ,listener)
    }

    //Binding data to view
    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bind(upcomingListData?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if(upcomingListData == null) return 0
        return upcomingListData?.size!!
    }

    class UpcomingViewHolder(itemView: View,listener: OnUpcomingCardListener): RecyclerView.ViewHolder(itemView){
        private val upcomingLaunchName = itemView.upcoming_launches_name_text
        private val exploreButton = itemView.upcoming_launches_button

        init {
            //Binding interface to click actions
            exploreButton.setOnClickListener {
                listener.onCardButtonClicked(adapterPosition)
            }
        }
        //Binding data to view
        fun bind(data: UpcomingLaunchModel){
            upcomingLaunchName.text = data.name
        }



    }
}