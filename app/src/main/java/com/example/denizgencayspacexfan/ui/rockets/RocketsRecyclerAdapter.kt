package com.example.denizgencayspacexfan.ui.rockets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rocket_card.view.*

class RocketsRecyclerAdapter : RecyclerView.Adapter<RocketsRecyclerAdapter.RocketsViewHolder>() {

    private var rocketListData: List<RocketModel>? = null
    private var listener: OnCardListener? = null

    interface OnCardListener{
        fun onCardClicked(position: Int)
        fun onLikeClicked(position: Int)
        fun onDislikeClicked(position: Int)
    }

    fun setRocketListData(rocketListData: List<RocketModel>?){
        this.rocketListData = rocketListData
    }

    fun setOnCardClickedListener(listener: OnCardListener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rocket_card,parent,false)
        return RocketsViewHolder(itemView ,listener!!)
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
        private val likeButton: ImageView = itemView.rocket_card_like_button
        private val dislikeButton: ImageView = itemView.rocket_card_dislike_button
        private val rocketCardImage: ImageView = itemView.rocket_card_rocket_image

        init {
            itemView.setOnClickListener {
                listener.onCardClicked(adapterPosition)
            }
            likeButton.setOnClickListener{
                listener.onLikeClicked(adapterPosition)
            }

            dislikeButton.setOnClickListener{
                listener.onDislikeClicked(adapterPosition)
            }

        }

        fun bind(data: RocketModel){
            rocketName.text = data.name
            if (data.flickrImages.isNotEmpty()){
                Picasso
                    .get()
                    .load(data.flickrImages[0])
                    .resize(350,350)
                    .centerCrop()
                    .into(rocketCardImage)
            }
            likeButton.isVisible = !data.isLiked
            dislikeButton.isVisible = data.isLiked
        }
    }

}