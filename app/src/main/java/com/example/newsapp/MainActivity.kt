package com.example.newsapp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import com.example.newsapp.ui.NewsAdapter
import com.example.newsapp.utils.Constants
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.NewsViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * News List activity
 */
class MainActivity : AppCompatActivity(), NewsItemClickListener, OnClickListener {

    private lateinit var newsViewModel: NewsViewModel

    @Inject
    lateinit var newsViewModelFactory: NewsViewModelFactory

    private lateinit var newsArticles: List<Article>

    private lateinit var adapter: NewsAdapter

    private lateinit var recyclerView: RecyclerView

    private lateinit var progressBar: ProgressBar

    private var selectedCategory: String = ""

    private val hsv: HorizontalScrollView
        get() = findViewById(R.id.hScrollView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout: LinearLayout = hsv.findViewById(R.id.buttonLayout);
        layout.removeAllViews();

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        //Add buttons for categories
        for (category in Constants.categories) {
            val myButton = Button(this)
            layoutParams.setMargins(20, 0, 20, 0)
            myButton.layoutParams = layoutParams
            myButton.setTextColor(Color.WHITE)
            myButton.setBackgroundResource(R.drawable.rounded_bg)
            myButton.gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
            myButton.text = category
            myButton.setOnClickListener(this)
            layout.addView(myButton);
        }

        (application as NewsApplication).applicationComponent.inject(this)
        newsViewModel = ViewModelProvider(this, newsViewModelFactory)[NewsViewModel::class.java]

        recyclerView = findViewById(R.id.newsList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar = findViewById(R.id.pBar)
        progressBar.visibility = VISIBLE

        newsViewModel.newsLiveData.observe(this, Observer {
            newsArticles = (it as News).articles
            adapter = NewsAdapter(newsArticles, this)
            adapter.setBookmarkedArticles(getBookmarkedArticles())
            recyclerView.adapter = adapter
            progressBar.visibility = GONE
        })

    }

    // List Item click
    override fun onItemClick(position: Int) {
        val intent = Intent(this, NewsDetailsActivity::class.java)
        intent.putExtra("news", newsArticles[position])
        startActivity(intent)
    }

    //Bookmark Icon click
    override fun onBookmarkClick(position: Int) {
        val article = newsArticles[position]
        article.isBookmark = !article.isBookmark
        lifecycleScope.launch {
            if (article.isBookmark) {
                saveBookmarkedArticles(article.title)
                adapter.setBookmarkedArticles(getBookmarkedArticles())
            } else {
                deleteBookmarkedArticles(article.title)
                adapter.setBookmarkedArticles(getBookmarkedArticles())

            }
        }
        adapter.notifyItemChanged(position)
    }

    // Category button click
    override fun onClick(view: View?) {
        if (view is Button) {
            selectedCategory = view.text.toString()
            var title = getString(R.string.app_name)
            title = title + " -> " + selectedCategory.uppercase()
            (this as AppCompatActivity).supportActionBar!!.title = title
            progressBar.visibility = VISIBLE
            newsViewModel.category = view.text.toString()
            newsViewModel.getNewsFromAPi()
            progressBar.visibility = GONE
        }
    }

    //Save title of bookmarked article
    private fun saveBookmarkedArticles(title: String) {
        var pref: SharedPreferences =
            applicationContext.getSharedPreferences(Constants.PREF_NAME, 0) // 0 - for private mode
        val edit: SharedPreferences.Editor = pref.edit()
        var str = pref.getString(Constants.KEY_NAME, null);
        str = "$str,$title"
        edit.putString(Constants.KEY_NAME, str); // Storing string
        edit.commit(); // commit changes*/
    }

    //Delete title of un bookmarked article
    private fun deleteBookmarkedArticles(title: String) {
        var pref: SharedPreferences =
            applicationContext.getSharedPreferences(Constants.PREF_NAME, 0) // 0 - for private mode
        val edit: SharedPreferences.Editor = pref.edit()
        var str = pref.getString(Constants.KEY_NAME, null);
        str?.replace(",$title", "")
        edit.putString(Constants.KEY_NAME, str); // Storing string
        edit.commit(); // commit changes*/
    }

    //Retrieve titles of bookmarked articles
    private fun getBookmarkedArticles(): String {
        var pref: SharedPreferences =
            applicationContext.getSharedPreferences(Constants.PREF_NAME, 0) // 0 - for private mode
        return pref.getString(Constants.KEY_NAME, "") ?: ""
    }

}