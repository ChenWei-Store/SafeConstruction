package com.shuangning.safeconstruction.extension

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes

/**
 * Created by Chenwei on 2023/9/30.
 */

fun Int.onClick(containerView: View, click:()->Unit){
    containerView.findViewById<View>(this).setOnClickListener {
        click()
    }
}

fun Int.setText(containerView: View, text: String){
    containerView.findViewById<TextView>(this).text = text
}

fun Int.setVisible(containerView: View, visibility: Int){
    containerView.findViewById<View>(this).visibility = visibility
}