package com.cattusmanga.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MangaAdapter (private val mangaList : ArrayList<Manga>) :
    RecyclerView.Adapter<MangaAdapter.myViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.manga_display, parent, false)
        return myViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mangaList.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = mangaList[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.imageSrc)
            .into(holder.image)
        holder.title.text = currentItem.title
    }
    private fun goToMangaInfo(){

    }
    class myViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.manga_img)
        val title : TextView = itemView.findViewById(R.id.manga_title)


    }


}