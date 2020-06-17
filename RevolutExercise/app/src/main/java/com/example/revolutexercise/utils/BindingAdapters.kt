package com.example.revolutexercise.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.revolutexercise.R

@BindingAdapter("imageSrc")
fun bindImage(imgView: ImageView, flagImageResId: Int?) {
    Glide.with(imgView.context)
        .load(flagImageResId)
        .placeholder(R.drawable.ic_flag_placeholder)
        .into(imgView)
}