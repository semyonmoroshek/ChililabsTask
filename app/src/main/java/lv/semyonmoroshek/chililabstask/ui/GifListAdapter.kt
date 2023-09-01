package lv.semyonmoroshek.chililabstask.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lv.semyonmoroshek.chililabstask.R
import lv.semyonmoroshek.chililabstask.data.model.Data

class GifListAdapter() : RecyclerView.Adapter<GifListAdapter.ViewHolder>() {

    private var imgList = mutableListOf<Data>()

    fun setUpdatedData(items: List<Data>) {
        this.imgList = items.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.gif_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gifItem = imgList.getOrNull(position) ?: return
        val gifUrl = gifItem.images?.original?.url
        val context = holder.itemView.context


        gifUrl?.let {
            Glide.with(context)
                .asGif()
                .load(gifUrl)
                .placeholder(R.drawable.loader)
                .into(holder.imgGif)
        }
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGif: ImageView = itemView.findViewById(R.id.img_gif)

    }
}