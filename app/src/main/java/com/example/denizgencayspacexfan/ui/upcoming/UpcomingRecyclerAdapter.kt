package com.example.denizgencayspacexfan.ui.upcoming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.models.UpcomingLaunchModel
import kotlinx.android.synthetic.main.upcoming_launch_card.view.*

class UpcomingRecyclerAdapter: RecyclerView.Adapter<UpcomingRecyclerAdapter.UpcomingViewHolder>(){

    private var upcomingListData: List<UpcomingLaunchModel>? = null
    private lateinit var listener: OnUpcomingCardListener

    interface OnUpcomingCardListener{
        fun onUpcomingCardClicked(position: Int)
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

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bind(upcomingListData?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if(upcomingListData == null) return 0
        return upcomingListData?.size!!
    }

    class UpcomingViewHolder(itemView: View,listener: OnUpcomingCardListener): RecyclerView.ViewHolder(itemView){
        private val upcomingLaunchName = itemView.upcoming_launches_name_text

        init {
            itemView.setOnClickListener {
                listener.onUpcomingCardClicked(adapterPosition)
            }
        }

        fun bind(data: UpcomingLaunchModel){
            upcomingLaunchName.text = data.name
        }



    }
}