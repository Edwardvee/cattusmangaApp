package com.cattusmanga.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cattusmanga.app.CategoriesAdapter.myViewHolder

class CategoriesAdapter (private val categoryList: ArrayList<Category>) :
    RecyclerView.Adapter<CategoriesAdapter.myViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_display, parent, false)
        return myViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = categoryList[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.imageSrc)
            .into(holder.image)
        holder.title.text = currentItem.title
    }

    class myViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val image : ImageView = itemView.findViewById(R.id.category_img)
        val title : TextView = itemView.findViewById(R.id.category_title)
    }
}