package com.example.appmarvels.framework.imageLoader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.example.appmarvels.R

interface ImageLoader {

    fun load(
        imageView: ImageView,
        imageUrl: String,
        @DrawableRes placeholder: Int =  R.drawable.ic_img_placeholder,
        @DrawableRes fallback: Int = R.drawable.ic_img_loading_error)
}