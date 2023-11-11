package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityQuestionOperatorDetailBinding
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
class QuestionOperatorDetailActivity: BaseActivity<ActivityQuestionOperatorDetailBinding>() {

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityQuestionOperatorDetailBinding? {
        return ActivityQuestionOperatorDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.detail))
        binding?.apply {
            tvProblemContent.text = "测试测试测试测试"
            tvContentNumber.text = "GX-2_221228001"
            tvType.text = "xxx类型"
            tvCheckSort.text = "质量检查"
            tvCheckItem.text = "工地形象"
            tvReferenceCause.text = "在建工程不得在外电架空线路正下方施工，搭设作业棚，建造生活设施或堆放构建，家具，材料及其他杂物等"
            tvPartOfTender.text = "gx-2标"
            tvConstructionTeamDate.text = "路基施工2队"
            tvRectificationRequirementRight.text = "测试测试测试测试"
            tvRectificationPeriod.text = "2022-12-31 00：00"
            tvReasonAnalyze.text = "其他，管理制度"
            tvPerson.text = "监理 2022-12-28 14：38"
        }

    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {


    }

    override fun observeViewModel() {
    }
}