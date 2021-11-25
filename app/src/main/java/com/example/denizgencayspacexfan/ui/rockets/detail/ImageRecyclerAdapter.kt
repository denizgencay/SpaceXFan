package com.example.denizgencayspacexfan.ui.rockets.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.denizgencayspacexfan.R
import com.example.denizgencayspacexfan.data.models.RocketModel
import com.squareup.picasso.Picasso

class ImageRecyclerAdapter : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>(){

    private var imageListData: List<String>? = null

    fun setImageListData(imageListData: List<String>?){
        this.imageListData = imageListData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageRecyclerAdapter.ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.image_card,parent,false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageRecyclerAdapter.ImageViewHolder, position: Int) {
        holder.bind(imageListData?.get(position)!!)
    }

    override fun getItemCount(): Int {
        if (imageListData == null) return 0
        return imageListData!!.size
    }

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val rocketImageView: ImageView = itemView.findViewById(R.id.image_card_rocket_image)

        fun bind(data: String){
            Picasso
                .get()
                .load(data)
                .into(rocketImageView)
        }

    }
}