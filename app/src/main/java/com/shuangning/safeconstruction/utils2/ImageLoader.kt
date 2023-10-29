package com.shuangning.safeconstruction.utils2

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

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

}