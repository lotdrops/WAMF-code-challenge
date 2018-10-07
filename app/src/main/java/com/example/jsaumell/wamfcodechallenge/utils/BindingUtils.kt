package com.example.jsaumell.wamfcodechallenge.utils

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jsaumell.wamfcodechallenge.R

@BindingAdapter("bind:imageUrl")
fun ImageView.bindUrl(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = resources.getDimension(R.dimen.image_placeholder_stroke)
    circularProgressDrawable.centerRadius = resources.getDimension(R.dimen.image_placeholder_radius)
    circularProgressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent),
            PorterDuff.Mode.ADD)
    circularProgressDrawable.start()

    Glide.with(context)
            .load(url)
            .apply(RequestOptions().placeholder(circularProgressDrawable))
            .into(this)
}