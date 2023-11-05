package com.shuangning.safeconstruction.ui.dialog

import android.content.Context
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.lxj.xpopup.core.BottomPopupView
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.widget.DrawableTextView
import com.shuangning.safeconstruction.data.mmkv.MMKVResp
import com.shuangning.safeconstruction.databinding.DialogGroupEducationTipBinding
import com.shuangning.safeconstruction.extension.onClick
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/11/5.
 */
class GroupEducationTipDialog(ctx: Context): BottomPopupView(ctx) {
    private var isSelected = false
    override fun onCreate() {
        super.onCreate()
        findViewById<TextView>(R.id.tv_content).movementMethod = ScrollingMovementMethod.getInstance();
        R.id.btn_know.onClick(this){
            if(isSelected){
                MMKVResp.resp.putGroupEducationTipStatus(true)
            }
            dismiss()
        }
        val tvSelect = findViewById<DrawableTextView>(R.id.tv_select)
        tvSelect.setOnDrawableClickListener(object: DrawableTextView.OnDrawableClickListener{
            override fun onDrawableLeftClick() {
                isSelected = !isSelected
                if(isSelected){
                    UIUtils.setTextLeftDrawable(tvSelect, R.drawable.selected)
                }else{
                    UIUtils.setTextLeftDrawable(tvSelect, R.drawable.not_select)
                }
            }

            override fun onDrawableTopClick() {
            }

            override fun onDrawableRightClick() {
            }

            override fun onDrawableBottomClick() {
            }

        })
    }

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_group_education_tip
    }

}