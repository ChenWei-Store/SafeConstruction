package com.shuangning.safeconstruction.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityCompletedDetailBinding
import com.shuangning.safeconstruction.databinding.ActivityToBeExamineDetailBinding
import com.shuangning.safeconstruction.databinding.ActivityToBeRectifedDetailBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
class QuestionOperatorActivity: BaseActivity<ViewBinding>() {
    private var status: Int = ERROR

    override fun getViewBinding(layoutInflater: LayoutInflater): ViewBinding? {
        return null
    }

    override fun initView(savedInstanceState: Bundle?) {
        when(status){
            TO_BE_RECTIFIED ->{
                initToBeRectifed()
            }
            TO_BE_EXAMINE->{
                initToBeExamine()
            }
            COMPLETED->{
                initCompleted()
            }
        }
    }

    private fun initToBeRectifed(){
        binding = ActivityToBeRectifedDetailBinding.inflate(LayoutInflater.from(this))
        (binding as? ActivityToBeRectifedDetailBinding)?.apply {
            setContentView(root)
            viewTitle.setTitle(UIUtils.getString(R.string.question_operator))
            tvProblemRight.text = "2022-12-28 14:38 监理"
            tvProblemTitle.text = "测试测试测试测试"
            tvCheckSort.text = "质量检查"
            tvCheckItem.text = "工地形象"
            tvReferenceClause.text = "在建工程不得在外电架空线路正下方施工，搭设作业棚，建造生活设施或堆放构建，家具，材料及其他杂物等"
            tvPartOfTender.text = "2022-12-31 00:00"
            tvCommitPerson.text = "xxxxxx要求"
            tvReasonAnalyze.text = "其他"
        }
        supportActionBar?.hide()

    }

    private fun initToBeExamine(){
        binding = ActivityToBeExamineDetailBinding.inflate(LayoutInflater.from(this))
        (binding as? ActivityToBeExamineDetailBinding)?.apply {
            setContentView(root)
            viewTitle.setTitle(UIUtils.getString(R.string.question_operator))
            tvProblemContent.text = "测试测试测试测试"
            tvNumber.text = "GX-21_220809001"
            tvType.text = "项目部自查"
            tvCheckSort.text = "质量检查"
            tvCheckItem.text = "工地形象"
            tvReferenceClause.text = "在建工程不得在外电架空线路正下方施工，搭设作业棚，建造生活设施或堆放构建，家具，材料及其他杂物等"
            tvPartOfTender.text = "GX-21标"
            tvCommitPerson.text = "顾晓宇 2022-08-09 11:42"

        }

        supportActionBar?.hide()
    }

    private fun initCompleted(){
        binding = ActivityCompletedDetailBinding.inflate(LayoutInflater.from(this))
        (binding as? ActivityCompletedDetailBinding)?.apply {
            setContentView(root)
            viewTitle.setTitle(UIUtils.getString(R.string.question_operator))
            tvProblemRight.text = "2022-12-28 14:38 监理"
            tvProblemTitle.text = "测试测试测试测试"
            tvCheckSort.text = "质量检查"
            tvCheckItem.text = "工地形象"
            tvReferenceClause.text = "在建工程不得在外电架空线路正下方施工，搭设作业棚，建造生活设施或堆放构建，家具，材料及其他杂物等"
            tvPartOfTender.text = "2022-12-31 00:00"
            tvRectificationPerson.text = "安全员"
            tvCommitPerson.text = "xxxxxx要求"
            tvReasonAnalyze.text = "其他"
            tvRectificationConditionDate.text = "2022-12-27 安全员"
            tvRectificationCondition.text = "测试测试测试测试"
            tvSubsequentMeasurese.text = "测试测试测试测试"
            tvAuditRight.text = "2022-12-28 14:38 安全员"
            tvAuditResult.text = "通过"
        }

        supportActionBar?.hide()
    }

    override fun initData() {
        status = intent?.getIntExtra(STATUS, ERROR)?: ERROR
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.let {
            when(it){
                is ActivityToBeRectifedDetailBinding->{
                    it.floatingbutton.setOnClickListener {
                        StartActivityManager.startToFinesList(this@QuestionOperatorActivity)
                    }
                    it.viewProblemTitle.setOnClickListener {
                        StartActivityManager.startToQuestionOperatorDetail(this@QuestionOperatorActivity)
                    }
                }
                is ActivityCompletedDetailBinding->{
                    it.floatingbutton.setOnClickListener {
                        StartActivityManager.startToFinesList(this@QuestionOperatorActivity)
                    }
                    it.viewProblemTitle.setOnClickListener {
                        StartActivityManager.startToQuestionOperatorDetail(this@QuestionOperatorActivity)
                    }
                }

                else -> {

                }
            }
        }





    }

    override fun observeViewModel() {
    }

    companion object{
        const val STATUS = "status"
        fun getIntent(activity: Activity, status: Int): Intent {
            val intent = Intent(activity, MultiSelectActivity::class.java)
            intent.putExtra(STATUS, status)
            return intent
        }
    }
}