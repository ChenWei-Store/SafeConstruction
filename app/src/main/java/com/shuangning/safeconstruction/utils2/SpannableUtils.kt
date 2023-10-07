package com.shuangning.safeconstruction.utils2

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/9/30.
 */
class SpannableUtils {
    lateinit var spanString: SpannableString
    fun create(@StringRes stringId: Int): SpannableUtils{
        spanString = SpannableString(UIUtils.getString(stringId))
        return this
    }
    fun create(string: String): SpannableUtils{
        spanString = SpannableString(string)
        return this
    }

    /**
     * 添加文字颜色
     */
    fun addTextColorSpan(@ColorRes colorId: Int, startIdx: Int, endIdx: Int): SpannableUtils{
        val foregroundColorSpan = ForegroundColorSpan(UIUtils.getColor(colorId))
        spanString.setSpan(
            foregroundColorSpan,
            startIdx,
            endIdx,
            SpannableString.SPAN_INCLUSIVE_INCLUSIVE
        )
        return this
    }

    /**
     * 添加粗斜体
     */
    fun addTextBoldItalic(startIdx: Int, endIdx: Int): SpannableUtils{
        val span = StyleSpan(Typeface.BOLD_ITALIC)
        spanString.setSpan(span, startIdx, endIdx, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        return this
    }
    /**
     * 添加斜体
     */
    fun addTextItalic(startIdx: Int, endIdx: Int): SpannableUtils{
        val span = StyleSpan(Typeface.ITALIC)
        spanString.setSpan(span, startIdx, endIdx, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        return this
    }
    /**
     * 添加粗体
     */
    fun addTextBold(startIdx: Int, endIdx: Int): SpannableUtils{
        val span = StyleSpan(Typeface.BOLD)
        spanString.setSpan(span, startIdx, endIdx, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        return this
    }

    /**
     * 添加下划线
     */
    fun addUnderLine(startIdx: Int, endIdx: Int): SpannableUtils{
        val span = UnderlineSpan()
        spanString.setSpan(span, startIdx, endIdx, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        return this
    }

    /**
     * 添加删除线
     */
    fun addDeleteLine(startIdx: Int, endIdx: Int): SpannableUtils{
        val span = StrikethroughSpan()
        spanString.setSpan(span, startIdx, endIdx, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        return this
    }

    /**
     * 添加超链接
     */
    fun addUrl(startIdx: Int, endIdx: Int, url: String): SpannableUtils{
        val span = URLSpan(url)
        spanString.setSpan(span, startIdx, endIdx, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        return this
    }

    /**
     * 添加字体大小
     */
    fun addFont(startIdx: Int, endIdx: Int, textSize: Int): SpannableUtils{
        val span = AbsoluteSizeSpan(textSize)
        spanString.setSpan(span, startIdx, endIdx, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        return this
    }

    /**
     * 添加点击事件和点击部分颜色
     */
    fun addClickable(startIdx: Int, endIdx: Int, tv: TextView, @ColorRes color: Int, block:()->Unit): SpannableUtils{
        //不设置点击不生效
        tv.movementMethod = LinkMovementMethod.getInstance()
        //取消选中后高亮效果
        tv.highlightColor = Color.TRANSPARENT
        val span = ClickSpan(block, color)
        spanString.setSpan(span, startIdx, endIdx, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
        return this
    }

    fun get(): SpannableString{
        return spanString
    }

    class ClickSpan(val block:()->Unit, @ColorRes val color: Int): ClickableSpan(){
        override fun onClick(widget: View) {
            block()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = UIUtils.getColor(color)
            //取消下划线
            ds.isUnderlineText = false
        }
    }
}