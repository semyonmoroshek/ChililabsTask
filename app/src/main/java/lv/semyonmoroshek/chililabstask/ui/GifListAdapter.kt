package lv.semyonmoroshek.chililabstask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lv.semyonmoroshek.chililabstask.R
import lv.semyonmoroshek.chililabstask.data.model.Data

class GifListAdapter() : PagingDataAdapter<Data, GifListAdapter.ViewHolder>(GifComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.gif_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gifItem = getItem(position)
        val gifUrl = gifItem?.images?.original?.url
        val context = holder.itemView.context

        gifUrl?.let {
            Glide.with(context)
                .asGif()
                .load(gifUrl)
                .placeholder(R.drawable.loader)
                .into(holder.imgGif)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGif: ImageView = itemView.findViewById(R.id.img_gif)

    }

    object GifComparator: DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}