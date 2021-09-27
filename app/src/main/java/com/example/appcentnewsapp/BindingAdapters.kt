package com.example.appcentnewsapp

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

import com.bumptech.glide.Glide

@Suppress("UNCHECKED_CAST")
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url:String? ) {
    if (url != null) {
        if(url.isNotEmpty()) {
            Glide.with(imageView.context)
                .load(url)
                .into(imageView)
        }
    }
}

/*
    "2021-09-16T13:26:42Z"
 */
@Suppress("UNCHECKED_CAST")
@BindingAdapter("date")
fun convertToDate(textView: TextView, longerText:String?) {
    if (longerText != null) {
        textView.text= longerText.substring(0,10).replace("-",".")
    }
}
