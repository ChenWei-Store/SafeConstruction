package com.shuangning.safeconstruction.utils2

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/**
 * Created by Chenwei on 2023/9/8.
 * TODO:111
 */
object ImageLoader {
    fun loadUrl(ctx: Context, url: String, iv: ImageView){
         Glide.with(ctx)
            .load(url)
             .into(iv)
    }

    fun loadUrlWithCircle(ctx: Context, url: String, iv: ImageView){
        Glide.with(ctx)
            .load(url)
            .transform(CircleCrop())
            .into(iv)
    }

    fun loadUrlWithRound(ctx: Context, url: String, iv: ImageView, px: Int){
        Glide.with(ctx)
            .load(url)
            .transform(RoundedCorners(px))
            .into(iv)
    }

}