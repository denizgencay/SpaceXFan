package com.example.denizgencayspacexfan.ui.rockets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.models.RocketModel
import kotlinx.android.synthetic.main.rocket_card.view.*

class RocketsRecyclerAdapter : RecyclerView.Adapter<RocketsRecyclerAdapter.RocketsViewHolder>() {

    private var rocketListData: List<RocketModel>? = null
    private lateinit var listener: OnCardListener

    interface OnCardListener{
        fun onCardClicked(position: Int)
    }

    fun setRocketListData(rocketListData: List<RocketModel>?){
        this.rocketListData = rocketListData
    }

    fun setOnCardClickedListener(listener: OnCardListener){

        this.listener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rocket_card,parent,false)

        return RocketsViewHolder(itemView ,listener)
    }

    override fun onBindViewHolder(holder: RocketsViewHolder, position: Int) {
        holder.bind(rocketListData?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if(rocketListData == null) return 0
        return rocketListData?.size!!
    }

    class RocketsViewHolder(itemView: View,listener: OnCardListener): RecyclerView.ViewHolder(itemView){
        private val rocketName = itemView.rocket_card_rocket_name

        init {
            itemView.setOnClickListener {
                listener.onCardClicked(adapterPosition)
            }
        }

        fun bind(data: RocketModel){
            rocketName.text = data.name
        }

    }


}