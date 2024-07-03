package com.example.newsapp

/**
 * Click Listener for list item and bookmark icon on list item
 */
interface NewsItemClickListener {
    fun onItemClick(position: Int)
    fun onBookmarkClick(position: Int)
}