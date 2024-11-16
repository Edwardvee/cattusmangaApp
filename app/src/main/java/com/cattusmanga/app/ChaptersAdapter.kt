package com.cattusmanga.app

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChaptersAdapter(
    private val chapters: ArrayList<Chapters>,
    private val onChapterClick: (Int, Int) -> Unit // Parámetro necesario
) : RecyclerView.Adapter<ChaptersAdapter.myViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.manga_chapters_display, parent, false)
        return myViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val currentItem = chapters[position]
        holder.chapterTitle.text = "Capítulo ${currentItem.Number}"
        holder.chapterTitle.setOnClickListener {
            onChapterClick(currentItem.id, currentItem.Number)
        }

    }
    override fun getItemCount(): Int {
        return chapters.size
    }
    class myViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val chapterTitle : TextView = itemView.findViewById(R.id.chapterButton)
    }
}