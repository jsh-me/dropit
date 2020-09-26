package com.ddd4.dropit.presentation.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber

fun ImageView.loadUriCenterCrop(uri : Uri) {
    val options = RequestOptions()
    GlideApp.with(this).load(uri).apply(options.centerCrop()).into(this)
    Timber.d("glide loaded %s", uri.toString())
}

fun ImageView.loadUrlCenterCrop(url : String) {
    val options = RequestOptions()
    Timber.d("loaded %s", url)
    GlideApp.with(this).load(url).apply(options.centerCrop()).into(this)
}

fun ImageView.loadUri(uri : Uri) {
    GlideApp.with(this).load(uri).into(this)
}

fun ImageView.loadUrl(url : String?) {
    url?.let {
        GlideApp.with(this).load(it).into(this)
    }
}

fun ImageView.loadDrawable(drawable: Drawable) {
    GlideApp.with(this).load(drawable).into(this)
}

fun ImageView.loadBitmap(bitmap: Bitmap) {
    GlideApp.with(this).load(drawable).into(this)
}

fun ImageView.loadResource(res: Int) {
    GlideApp.with(this).load(res).into(this)
}