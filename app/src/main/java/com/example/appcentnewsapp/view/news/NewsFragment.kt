package com.example.appcentnewsapp.view.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcentnewsapp.R
import com.example.appcentnewsapp.databinding.FragmentNewsBinding
import com.example.appcentnewsapp.model.News
import com.example.appcentnewsapp.utils.EventObserver
import com.example.appcentnewsapp.view.NewsClickListener
import com.example.appcentnewsapp.view.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment() : Fragment(), NewsClickListener {

    companion object{
        const val NEWSITEM = "NEWS_ITEM"
    }

    private lateinit var newsAdapter: CommonAdapter
    private val viewModel by viewModel<NewsViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val toolbar = binding.mainToolBar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setRecyclerView()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.newsList.observe(viewLifecycleOwner, {
            if (it.isNotEmpty() && it!= null) {
                newsAdapter.setList(it)
            }
        })


        viewModel.navigateToDetails.observe(viewLifecycleOwner, EventObserver { newsItem ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(NEWSITEM, newsItem)
            startActivity(intent)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRecyclerView() {
        newsAdapter = CommonAdapter(requireContext(), this)
        binding.newsRecyclerView.run {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = newsAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun onItemClick(newsItem: News) {
        viewModel.itemClicked(newsItem)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.let {
            it.queryHint = getString(R.string.type_text)
            it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    Log.d("onQueryTextSubmit", query)
                    binding.hintText.visibility = View.GONE
                    viewModel.getQuery(query, 1)
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    //viewModel.getQuery(newText, 1)  --> server'ın request sayısı sınırlı ondan kapalı
                    return true
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        newsAdapter.setList(null)
        (requireActivity() as AppCompatActivity).setSupportActionBar(null)
    }
}