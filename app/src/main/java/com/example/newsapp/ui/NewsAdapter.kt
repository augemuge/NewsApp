package com.example.newsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.NewsItemClickListener
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso

class NewsAdapter(private val itemList: List<Article> , private val itemClickListener:
    NewsItemClickListener) :
    RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    lateinit var bookmarkedItemList: String
    class MyViewHolder(view: View, private val itemClickListener: NewsItemClickListener,
    ) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val descTextView: TextView = view.findViewById(R.id.descTextView)
        val bookmarkIcon: ImageView = view.findViewById(R.id.bookmark)
        init {
            view.setOnClickListener(this)
            bookmarkIcon.setOnClickListener(this)

        }
        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                when (v?.id) {
                    R.id.bookmark -> {
                        itemClickListener.onBookmarkClick(position)
                    }
                    else -> itemClickListener.onItemClick(position)  // Handle item click
                }
            }
        }
    }

    fun setBookmarkedArticles (articles : String) {
        bookmarkedItemList = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.headline_list_item, parent, false)
        return MyViewHolder(itemView, itemClickListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]
        holder.titleTextView.text = item.title
        holder.descTextView.text = item.description
        val bookmarkIconRes = if (bookmarkedItemList.contains(item.title)) {
            R.drawable.bookmarked
        } else {
            R.drawable.unbookmarked
        }
        holder.bookmarkIcon.setImageResource(bookmarkIconRes)
        Picasso.get().load(item.urlToImage).placeholder(R.drawable.ic_launcher_background).
        into(holder.imageView)
    }

    override fun getItemCount() = itemList.size
}
