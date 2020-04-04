package com.aura.grimage.features

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aura.grimage.R
import com.aura.grimage.models.Hit
import com.bumptech.glide.Glide

class GridImagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var hits = ArrayList<Hit>()
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return PropertiesViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int {
        return hits.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PropertiesViewHolder).bind(hits.get(position))
    }

    class PropertiesViewHolder(itemView: View, val listener: Listener?) :
        RecyclerView.ViewHolder(itemView) {
        private var ivImage: ImageView

        init {
            ivImage = itemView.findViewById(R.id.ivImage)
        }

        fun bind(hit: Hit) {
            Glide.with(itemView.context)
                .load(hit.previewURL)
                .into(ivImage)
            ivImage.setOnClickListener {
                listener?.onItemClicked(hit)
            }
        }
    }

    interface Listener {
        fun onItemClicked(hit: Hit)
    }
}