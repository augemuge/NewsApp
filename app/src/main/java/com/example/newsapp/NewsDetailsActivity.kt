package com.example.newsapp

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.model.Article
import com.squareup.picasso.Picasso

/**
 * News Details Activity
 */
class NewsDetailsActivity : AppCompatActivity() {

    private val newsTitle: TextView
        get() = findViewById(R.id.newsTitle)
    private val newsAuthor: TextView
        get() = findViewById(R.id.newsAuthor)
    private val newsDate: TextView
        get() = findViewById(R.id.newsDate)
    private val newsContent: TextView
        get() = findViewById(R.id.newsContent)
    private val newsImage: ImageView
        get() = findViewById(R.id.newsImage)
    private lateinit var article : Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            article  = intent.getParcelableExtra("news", Article::class.java)!!
        } else {
            @Suppress("DEPRECATION")
            article = ((intent.getParcelableExtra("news") as? Article)!!)
        }

        article?.let {
           newsTitle.text = it.title
            newsContent.text = it.description
            newsAuthor.text = it.author
            newsDate.text = it.publishedAt
            Picasso.get().load(it.urlToImage).placeholder(R.drawable.ic_launcher_background)
           .into(newsImage)
       }
    }
}