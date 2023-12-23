package com.shuangning.safeconstruction.base.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import com.shuangning.safeconstruction.databinding.ViewTitleBinding
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils
import com.shuangning.safeconstruction.utils2.MyLog

/**
 * Created by Chenwei on 2023/9/30.
 */
class TitleView @JvmOverloads constructor(
    ctx: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0): RelativeLayout(ctx, attrs, defStyleAttr) {
    private var binding: ViewTitleBinding? = null
    init {
        binding = ViewTitleBinding.inflate(LayoutInflater.from(ctx), this, true)
        val actionbarHeight = ScreenUtil.getActionBarHeight()
        MyLog.d("actionbarHeight:$actionbarHeight")
        binding?.rlTitle?.layoutParams?.apply {
            height = actionbarHeight
        }

        binding?.llBack?.setOnClickListener {
            ActivityUtils.removeActivity()
        }
    }

    fun setTitle(title: String){
        binding?.title?.text = title
    }

    fun setTitleColor(@ColorRes color: Int){
        binding?.title?.setTextColor(UIUtils.getColor(color))
    }

    fun setTitleBackgroundColor(@ColorRes color: Int){
        setBackgroundColor(UIUtils.getColor(color))
    }

    fun setRightText(text: String){
        binding?.tvRight?.text = text
        binding?.tvRight?.visibility = View.VISIBLE
    }
    fun setRightTextListener(block: (tv: View)->Unit){
       binding?.tvRight?.setOnClickListener {
           block(it)
       }
    }
}