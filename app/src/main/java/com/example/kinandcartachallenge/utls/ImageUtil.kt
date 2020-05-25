package com.example.kinandcartachallenge.utls

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object ImageUtil {

    fun setImageFromUrl(context: Context, url:String, target: ImageView, defaultDrawable: Int){
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(defaultDrawable)
            .error(defaultDrawable)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        if (url.isNotEmpty())
            Glide.with(context).load(url).apply(options).into(target)
        else
            Glide.with(context).load(defaultDrawable)
    }

}