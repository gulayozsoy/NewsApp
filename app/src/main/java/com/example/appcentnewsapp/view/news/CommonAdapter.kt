package com.example.appcentnewsapp.view.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentnewsapp.databinding.ItemNewsBinding
import com.example.appcentnewsapp.model.News
import com.example.appcentnewsapp.view.NewsClickListener

class CommonAdapter(val context: Context, val clickListener: NewsClickListener):
    RecyclerView.Adapter<CommonAdapter.NewsViewHolder>(){

    private var list: MutableList<News> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(ItemNewsBinding.inflate(layoutInflater, parent, false))
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int = list.size

    fun setList(newsList: List<News>?) {
        list.clear()
        if (newsList != null) {
            list.addAll(newsList)
        }
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(position: Int) {
            val item = list[position]
            binding.news = item
            binding.newsListClickInterface = clickListener
        }
    }
}