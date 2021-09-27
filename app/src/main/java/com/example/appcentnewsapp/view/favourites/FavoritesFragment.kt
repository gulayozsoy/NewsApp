package com.example.appcentnewsapp.view.favourites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appcentnewsapp.NewsApp
import com.example.appcentnewsapp.databinding.FragmentFavoritesBinding
import com.example.appcentnewsapp.model.News
import com.example.appcentnewsapp.utils.EventObserver
import com.example.appcentnewsapp.view.NewsClickListener
import com.example.appcentnewsapp.view.detail.DetailActivity
import com.example.appcentnewsapp.view.news.CommonAdapter
import com.example.appcentnewsapp.view.news.NewsFragment.Companion.NEWSITEM


class FavoritesFragment : Fragment(), NewsClickListener {


    private lateinit var favAdapter: CommonAdapter
    private val viewModel: FavoritesViewModel by viewModels {
        FavoritesViewModelFactory((activity?.application as NewsApp).localRepository)
    }

    private var _binding: FragmentFavoritesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        viewModel.getLocalData()

        viewModel.allLocalNews?.observe(viewLifecycleOwner,  {
            if (it.isNotEmpty() && it!= null) {
                favAdapter.setList(it)
            }
        })

        viewModel.navigateToDetails.observe(viewLifecycleOwner, EventObserver { newsItem ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(NEWSITEM, newsItem)
            startActivity(intent)
        })
    }

    private fun setRecyclerView() {
        favAdapter = CommonAdapter(requireContext(), this)
        binding.favoritesRecyclerView.run {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = favAdapter
            isNestedScrollingEnabled = false
        }
    }

    override fun onItemClick(newsItem: News) {
        viewModel.itemClicked(newsItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}