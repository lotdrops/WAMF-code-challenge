package com.example.jsaumell.wamfcodechallenge.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bind:imageUrl")
fun ImageView.bindUrl(url: String) {
    Glide.with(context).load(url).into(this)
}