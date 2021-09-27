package com.example.appcentnewsapp.view


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import com.example.appcentnewsapp.R
import com.example.appcentnewsapp.base.BaseActivity
import com.example.appcentnewsapp.databinding.ActivityMainBinding
import com.example.appcentnewsapp.view.favourites.FavoritesFragment
import com.example.appcentnewsapp.view.news.NewsFragment
import com.example.appcentnewsapp.view.news.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setSupportActionBar(binding.mainToolBar)
        addFragment()
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        val newsFragment = NewsFragment()
        val favoritesFragment = FavoritesFragment()

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.action_news -> {
                    replaceFragment(newsFragment)
                    return@setOnItemSelectedListener true
                }

                else -> {
                    replaceFragment(favoritesFragment)
                    return@setOnItemSelectedListener true
                }

            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.container.id, fragment)
            .addToBackStack(null)
            .commit()

    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction()
            .add<NewsFragment>(binding.container.id)
            .commit()
    }

}