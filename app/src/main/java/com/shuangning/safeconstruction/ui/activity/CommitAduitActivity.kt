package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.request.CommitApprovalRejectReq
import com.shuangning.safeconstruction.bean.request.CommitAuditReq
import com.shuangning.safeconstruction.databinding.ActivityCommitAuditBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.viewmodel.CommitAuditViewModel
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/12/16.
 */
class CommitAduitActivity : BaseActivity<ActivityCommitAuditBinding>() {
    private val maxLength = 500
    private val viewModel by viewModels<CommitAuditViewModel>()
    private val flowInstanceId by lazy {
        intent?.getIntExtra(FLOW_INSTANCE_ID, 0) ?: 0
    }
    private val taskInstanceId by lazy {
        intent?.getIntExtra(TASK_INSTANCE_ID, 0) ?: 0
    }
    private val result by lazy {
        intent?.getStringExtra(RESULT) ?: ""
    }
    private var selectStatus = "通过"
    private val shenpiyijian by lazy {
        intent?.getStringExtra(SHEN_PI_YI_JIAN) ?: ""
    }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityCommitAuditBinding? {
        return ActivityCommitAuditBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (result.isEmpty()) {
            binding?.viewTitle?.setTitle("提交审批")
            binding?.etContent?.isEnabled = true
        } else {
            binding?.viewTitle?.setTitle("提交审批结果")
            if (result == "通过") {
                binding?.tvApproval?.isSelected = true
                binding?.tvReject?.isSelected = false
            } else {
                binding?.tvApproval?.isSelected = false
                binding?.tvReject?.isSelected = true
            }
            selectStatus = result
            binding?.etContent?.setText(shenpiyijian)
            binding?.etContent?.isEnabled = false
        }
        binding?.tvCount?.text = maxLength.toString()
        binding?.etContent?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
    }

    override fun initData() {

    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.etContent?.doAfterTextChanged {
            val length = it.toString().length
            binding?.tvCount?.text = (maxLength - length).toString()
        }

        binding?.llCommit?.setOnClickListener {
            val desc = binding?.etContent?.text?.toString()
            if (desc.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请输入现场情况描述")
                return@setOnClickListener
            }

            if (selectStatus.isEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请选择审批结果")
                return@setOnClickListener
            }

            LoadingManager.startLoading(this)
            if (result.isEmpty()) {
                val req = CommitAuditReq(flowInstanceId, taskInstanceId, desc, selectStatus)
                viewModel.commitAduit(req)
            } else {
                val req = CommitApprovalRejectReq(flowInstanceId, taskInstanceId)
                if (selectStatus == "通过") {
                    viewModel.commitApproval(req)
                } else {
                    viewModel.commitReject(req)
                }
            }
        }

        binding?.tvApproval?.setOnClickListener {
            binding?.tvApproval?.isSelected = true
            binding?.tvReject?.isSelected = false
            selectStatus = "通过"
            UIUtils.setTextLeftDrawable(it as TextView, R.drawable.selected)
            binding?.tvReject?.let {
                UIUtils.setTextLeftDrawable(it as TextView, R.drawable.not_select)
            }
        }

        binding?.tvReject?.setOnClickListener {
            binding?.tvApproval?.isSelected = false
            binding?.tvReject?.isSelected = true
            UIUtils.setTextLeftDrawable(it as TextView, R.drawable.selected)
            binding?.tvApproval?.let {
                UIUtils.setTextLeftDrawable(it as TextView, R.drawable.not_select)

            }
            selectStatus = "不通过"
        }
    }

    override fun observeViewModel() {
        viewModel.uploadResult.observe(this) {
            LoadingManager.stopLoading()
            if (it) {
                ToastUtil.showCustomToast("提交成功")
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    companion object {
        const val FLOW_INSTANCE_ID = "flowInstanceId"
        const val TASK_INSTANCE_ID = "taskInstanceId"
        const val RESULT = "result"
        const val SHEN_PI_YI_JIAN = "shenpiyijian"
        fun startTo(
            ctx: Context,
            flowInstanceId: Int,
            taskInstanceId: Int,
            requestCode: Int,
            result: String = "",
            shenpiyijian: String = ""
        ) {
            ActivityUtils.startForResult(ctx, CommitAduitActivity::class.java, requestCode) {
                putExtra(FLOW_INSTANCE_ID, flowInstanceId)
                putExtra(TASK_INSTANCE_ID, taskInstanceId)
                putExtra(RESULT, result)
                putExtra(SHEN_PI_YI_JIAN, shenpiyijian)
            }
        }
    }
}