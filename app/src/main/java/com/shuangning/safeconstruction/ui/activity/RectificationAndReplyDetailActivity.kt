package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.databinding.ActivityRectificationAndReplyBinding
import com.shuangning.safeconstruction.databinding.ActivityRectificationAndReplyDetailBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.RectificationAndReplyAdapter
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
class RectificationAndReplyDetailActivity: BaseActivity<ActivityRectificationAndReplyDetailBinding>() {

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityRectificationAndReplyDetailBinding? {
        return ActivityRectificationAndReplyDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.question_operator))

    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.floatingbutton?.setOnClickListener {
            StartActivityManager.startToFinesList(this@RectificationAndReplyDetailActivity)
        }
    }

    override fun observeViewModel() {
    }
}