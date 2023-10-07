package com.shuangning.safeconstruction.extension

import android.view.View
import androidx.annotation.IdRes

/**
 * Created by Chenwei on 2023/9/30.
 */

fun Int.onClick(containerView: View, click:()->Unit){
    containerView.findViewById<View>(this).setOnClickListener {
        click()
    }
}