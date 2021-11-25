package com.example.denizgencayspacexfan.ui.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.RocketModel
import kotlinx.android.synthetic.main.rocket_card.view.*

class FavoritesRecyclerAdapter : RecyclerView.Adapter<FavoritesRecyclerAdapter.FavoritesViewHolder>() {

    private var favoriteRocketsListData: List<RocketModel>? = null
    private var listener: OnCardListener? = null

    interface OnCardListener{
        fun onCardClicked(position: Int)
        fun onLikeClicked(position: Int)
    }

    fun setRocketListData(favoriteRocketsListData: List<RocketModel>?){
        this.favoriteRocketsListData = favoriteRocketsListData
    }

    fun setOnCardClickedListener(listener: OnCardListener){

        this.listener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rocket_card,parent,false)

        return FavoritesViewHolder(itemView ,listener!!)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(favoriteRocketsListData?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if(favoriteRocketsListData == null) return 0
        return favoriteRocketsListData?.size!!
    }

    class FavoritesViewHolder(itemView: View, listener: OnCardListener): RecyclerView.ViewHolder(itemView){
        private val rocketName = itemView.rocket_card_rocket_name
        private val likeButton = itemView.rocket_card_like_button

        init {
            itemView.setOnClickListener {
                listener.onCardClicked(adapterPosition)
            }

            likeButton.setOnClickListener{
                listener.onLikeClicked(adapterPosition)
            }
        }

        fun bind(data: RocketModel){
            rocketName.text = data.name
        }
    }

}