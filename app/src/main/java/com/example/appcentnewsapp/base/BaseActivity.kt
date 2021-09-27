package com.example.appcentnewsapp.base

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.appcentnewsapp.R

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var binding: T
    abstract fun getViewBinding(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)

        findViewById<ImageView>(R.id.backImageView)?.setOnClickListener {
            onBackPressed()
        }
    }
}