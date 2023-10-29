package com.shuangning.safeconstruction.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Chenwei on 2023/10/16.
 */
class NoScrollRecyclerview @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0): RecyclerView(ctx, attrs, defStyleAttr) {


    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

}