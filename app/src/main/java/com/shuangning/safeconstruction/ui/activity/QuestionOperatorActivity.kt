package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityQuestionOperatorBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
class QuestionOperatorActivity: BaseActivity<ActivityQuestionOperatorBinding>() {

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityQuestionOperatorBinding? {
        return ActivityQuestionOperatorBinding.inflate(layoutInflater)
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
            StartActivityManager.startToFinesList(this@QuestionOperatorActivity)
        }

        binding?.viewProblemTitle?.setOnClickListener {
            StartActivityManager.startToQuestionOperatorDetail(this@QuestionOperatorActivity)
        }

    }

    override fun observeViewModel() {
    }
}