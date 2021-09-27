package com.example.appcentnewsapp.view.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.viewModels
import com.example.appcentnewsapp.NewsApp
import com.example.appcentnewsapp.R
import com.example.appcentnewsapp.base.BaseActivity
import com.example.appcentnewsapp.databinding.ActivityDetailBinding
import com.example.appcentnewsapp.model.News
import com.example.appcentnewsapp.utils.EventObserver
import com.example.appcentnewsapp.view.news.NewsFragment.Companion.NEWSITEM


class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory((application as NewsApp).localRepository)
    }

    private var newsItem: News? = null

    override fun getViewBinding(): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras.let {
            newsItem = it?.getParcelable(NEWSITEM)
            binding.news = newsItem
            if (newsItem!=null) {
                viewModel.checkFavorites(newsItem!!)
            }
        }

        binding.newsLinkButton.setOnClickListener {
            viewModel.sourceClick()
        }

        binding.shareImageView.setOnClickListener {
            viewModel.shareClick()
        }

        binding.favoritesImageView.setOnClickListener {
            newsItem?.let { news -> viewModel.addToFavorites(news) }
        }


        viewModel.openWebEvent.observe(this, EventObserver {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(newsItem?.newsUrl)
            startActivity(openURL)
        })


        viewModel.shareEvent.observe(this, EventObserver {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, newsItem?.newsUrl)
            startActivity(Intent.createChooser(shareIntent, "Share link using"))
        })

        viewModel.favoriteIconVisibility.observe(this, { ok ->
            if(ok) {
                Toast.makeText(this, "Eklendi", LENGTH_SHORT).show()
            } else
                Toast.makeText(this, "Eklenmedi yada çıktı", LENGTH_SHORT).show()
        })
    }

}