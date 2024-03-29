package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.base.ShowPhoto
import com.shuangning.safeconstruction.bean.request.CommitApprovalRejectReq
import com.shuangning.safeconstruction.bean.response.RectificationAndReplyDetailResp
import com.shuangning.safeconstruction.databinding.ActivityCompletedDetailBinding
import com.shuangning.safeconstruction.databinding.ActivityToBeExamineDetailBinding
import com.shuangning.safeconstruction.databinding.ActivityToBeRectifedDetailBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.CommitAuditViewModel
import com.shuangning.safeconstruction.ui.viewmodel.RectifucationAndReplyDetailViewModel
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/10/11.
 */
class RectifucationAndReplyDetailActivity : BaseActivity<ViewBinding>() {
    private var status: String = ""
    private var photoData = mutableListOf<ItemViewType>()
    private var photoData2 = mutableListOf<ItemViewType>()
    private var photoAdapter: AddShowPhotoMultiAdapter? = null
    private var photoAdapter2: AddShowPhotoMultiAdapter? = null
    private val viewModel by viewModels<RectifucationAndReplyDetailViewModel>()
    private val commitViewModel by viewModels<CommitAuditViewModel>()

    private var id = ""
    private var flowInstanceId = 0
    private var taskInstanceId = 0
    private var checkoutNum = ""
    override fun getViewBinding(layoutInflater: LayoutInflater): ViewBinding? {
        return null
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    private fun showDataToUi(resp: RectificationAndReplyDetailResp) {
        when (status) {
            "DAILY_CHECK_CORRECT" -> {
                initToBeRectifed(resp)
            }

            "DAILY_CHECK_AUDIT","DAILY_CHECK_REJECT","DAILY_CHECK_APPROVAL" -> {
                initToBeExamine(resp)
            }
            else ->{
//                initCompleted(resp)
            }
        }
        initListener()
    }

    private fun initToBeRectifed(questionOperatorResp: RectificationAndReplyDetailResp) {
        val urls = parsePhotoUrl(questionOperatorResp.xunchatupian)
        photoData.clear()
        urls.forEach {
            photoData.add(ShowPhoto(it))
        }
        binding = ActivityToBeRectifedDetailBinding.inflate(LayoutInflater.from(this))
        (binding as? ActivityToBeRectifedDetailBinding)?.apply {
            setContentView(root)
            viewTitle.setTitle(UIUtils.getString(R.string.question_operator))
            tvProblemContent.text = questionOperatorResp.xianchangmiaoshu
            tvCheckSort.text = questionOperatorResp.jianchafenlei
            tvCheckItem.text = questionOperatorResp.jianchaxiang
            tvPartOfTender.text = questionOperatorResp.zhenggaiqixian
            tvCommitPerson.text = questionOperatorResp.zhenggaiyaoqiu
            if (urls.size > 0) {
                photoAdapter = AddShowPhotoMultiAdapter(photoData)
                rv.apply {
                    isNestedScrollingEnabled = false
                    layoutManager = GridLayoutManager(this@RectifucationAndReplyDetailActivity, 4)
                    addItemDecoration(
                        GridSpaceItemDecoration(
                            4,
                            ScreenUtil.dp2px(24f), ScreenUtil.dp2px(12f), false
                        )
                    )
                    adapter = photoAdapter
                }
            }
            floatingbutton.visibility = View.GONE
            commit.visibility = View.VISIBLE
        }
        supportActionBar?.hide()
    }

    private fun parsePhotoUrl(json: String): MutableList<String> {
        if(json.isEmpty()){
            return mutableListOf()
        }
        val jsonObj = JSONObject(json)
        val jsonArray = jsonObj.optJSONArray("attach")
        val urls = mutableListOf<String>()
        for (idx in 0 until jsonArray.length()) {
            val result = jsonArray.optJSONObject(idx)
            val url = result.optString("url")
            urls.add(url)
        }
        return urls
    }

    private fun initToBeExamine(questionOperatorResp: RectificationAndReplyDetailResp) {
        val urls = parsePhotoUrl(questionOperatorResp.xunchatupian)
        photoData.clear()
        urls.forEach {
            photoData.add(ShowPhoto(it))
        }
        binding = ActivityToBeExamineDetailBinding.inflate(LayoutInflater.from(this))
        (binding as? ActivityToBeExamineDetailBinding)?.apply {
            setContentView(root)
            viewTitle.setTitle(UIUtils.getString(R.string.question_operator))
            tvProblemContent.text = questionOperatorResp.xianchangmiaoshu
            tvNumber.text = questionOperatorResp.richangxunchabianhao
            tvCheckSort.text = questionOperatorResp.jianchafenlei
            tvCheckItem.text = questionOperatorResp.jianchaxiang
            commit.text = if (status == "DAILY_CHECK_REJECT"|| status == "DAILY_CHECK_APPROVAL"){
                "提交审批结果"
            }else{
                "提交审批"
            }
//            tvPartOfTender.text = questionOperatorResp.biaoduan
//            tvCommitPerson.text = questionOperatorResp.xingming
            if (urls.size > 0){
                photoAdapter = AddShowPhotoMultiAdapter(photoData)
                rv.apply {
                    isNestedScrollingEnabled = false
                    layoutManager = GridLayoutManager(this@RectifucationAndReplyDetailActivity, 4)
                    addItemDecoration(
                        GridSpaceItemDecoration(
                            4,
                            ScreenUtil.dp2px(24f), ScreenUtil.dp2px(12f), false
                        )
                    )
                    adapter = photoAdapter
                }
            }
        }

        supportActionBar?.hide()
    }

    private fun initCompleted(questionOperatorResp: RectificationAndReplyDetailResp) {
        val urls = parsePhotoUrl(questionOperatorResp.xunchatupian)
        photoData.clear()
        urls.forEach {
            photoData.add(ShowPhoto(it))
        }
        val urls2 = parsePhotoUrl(questionOperatorResp.zhenggaitupian)
        photoData2.clear()
        urls2.forEach {
            photoData2.add(ShowPhoto(it))
        }
        binding = ActivityCompletedDetailBinding.inflate(LayoutInflater.from(this))
        (binding as? ActivityCompletedDetailBinding)?.apply {
            setContentView(root)
            viewTitle.setTitle(UIUtils.getString(R.string.question_operator))
//            tvProblemRight.text = getTime(questionOperatorResp.createTime)
            tvProblemContent.text = questionOperatorResp.xianchangmiaoshu
            tvCheckSort.text = questionOperatorResp.jianchafenlei
            tvCheckItem.text = questionOperatorResp.jianchaxiang
            tvPartOfTender.text = questionOperatorResp.zhenggaiqixian
            tvRectificationPerson.text = getPersonName(questionOperatorResp.zhenggaichuliren)
            tvCommitPerson.text = questionOperatorResp.zhenggaiyaoqiu
            tvRectificationConditionDate.text = questionOperatorResp.zhenggaishijian
            tvRectificationCondition.text = questionOperatorResp.zhenggaiyaoqiu
            tvSubsequentMeasurese.text = questionOperatorResp.houqicuoshi
//            tvAuditRight.text = "2022-12-28 14:38 安全员"
            tvAuditRight.text = ""
            tvAuditResult.text = questionOperatorResp.shenpijieguo
            if (urls.size > 0){
                photoAdapter = AddShowPhotoMultiAdapter(photoData)
                rv.apply {
                    isNestedScrollingEnabled = false
                    layoutManager = GridLayoutManager(this@RectifucationAndReplyDetailActivity, 4)
                    addItemDecoration(
                        GridSpaceItemDecoration(
                            4,
                            ScreenUtil.dp2px(24f), ScreenUtil.dp2px(12f), false
                        )
                    )
                    adapter = photoAdapter
                }
            }
            if (photoData2.size > 0){
                photoAdapter2 = AddShowPhotoMultiAdapter(photoData2)
                rv2.apply {
                    isNestedScrollingEnabled = false
                    layoutManager = GridLayoutManager(this@RectifucationAndReplyDetailActivity, 4)
                    addItemDecoration(
                        GridSpaceItemDecoration(
                            4,
                            ScreenUtil.dp2px(24f), ScreenUtil.dp2px(12f), false
                        )
                    )
                    adapter = photoAdapter2
                }
            }
            floatingbutton.visibility = View.GONE
        }

        supportActionBar?.hide()
    }

    private fun getTime(str: String): String{
        if (str.isEmpty() || !str.contains("T") || !str.contains(".")){
            return ""
        }
        val array1 = str.split("T")
        val array2 = array1[1].split(".")
        val time = array1[0] + array2[0]
        return time
    }

    private fun getPersonName(str: String): String{
        if (str.isEmpty()){
            return ""
        }
        val jsonobject = JSONObject(str)
        val jsonArray = jsonobject.optJSONArray("user")
        return jsonArray?.optJSONObject(0)?.optString("fullName")?:""
    }

    override fun initData() {
        status = intent?.getStringExtra(STATUS) ?: ""
        id = intent?.getStringExtra(ID) ?: ""
        flowInstanceId = intent?.getIntExtra(FLOW_INSTANCE_ID, 0)?:0
        taskInstanceId = intent?.getIntExtra(TASK_INSTANCE_ID, 0)?:0
        LoadingManager.startLoading(this)
        viewModel.getData(id, flowInstanceId, taskInstanceId)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.let {
            when (it) {
                is ActivityToBeRectifedDetailBinding -> {
                    it.floatingbutton.setOnClickListener {
                        FinesListActivity.startTo(this@RectifucationAndReplyDetailActivity,checkoutNum)
                    }
                    it.viewProblemTitle.setOnClickListener {
                        StartActivityManager.startToQuestionOperatorDetail(this@RectifucationAndReplyDetailActivity)
                    }
                    it.commit.setOnClickListener{
                        CommitRectifiedActivity.startTo(this@RectifucationAndReplyDetailActivity, flowInstanceId, taskInstanceId, 1)
                    }
                }

                is ActivityCompletedDetailBinding -> {
                    it.floatingbutton.setOnClickListener {
                        FinesListActivity.startTo(this@RectifucationAndReplyDetailActivity, checkoutNum)
                    }
                    it.viewProblemTitle.setOnClickListener {
                        StartActivityManager.startToQuestionOperatorDetail(this@RectifucationAndReplyDetailActivity)
                    }
                }
                is ActivityToBeExamineDetailBinding ->{
                    it.commit.setOnClickListener {
                        if (status == "DAILY_CHECK_AUDIT"){
                            CommitAduitActivity.startTo(this@RectifucationAndReplyDetailActivity, flowInstanceId, taskInstanceId, 2)
                        }else{
                            LoadingManager.startLoading(this)
                            val req = CommitApprovalRejectReq(flowInstanceId, taskInstanceId)
                            if (status == "DAILY_CHECK_APPROVAL") {
                                commitViewModel.commitApproval(req)
                            } else {
                                commitViewModel.commitReject(req)
                            }
                        }
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun observeViewModel() {
        viewModel.data.observe(this) {
            it?.let {
                showDataToUi(it)
            }
            LoadingManager.stopLoading()
        }
        commitViewModel.uploadResult.observe(this){
            LoadingManager.stopLoading()
            if (it){
                finish()
            }
        }

    }

    companion object {
        const val STATUS = "status"
        const val ID = "id"
        const val FLOW_INSTANCE_ID = "flowInstanceId"
        const val TASK_INSTANCE_ID = "taskInstanceId"
        fun startTo(ctx: Context, status: String, id: String, flowInstanceId: Int, taskInstanceId: Int) {
            ActivityUtils.start(ctx, RectifucationAndReplyDetailActivity::class.java) {
                putExtra(STATUS, status)
                putExtra(ID, id)
                putExtra(CommitRectifiedActivity.FLOW_INSTANCE_ID, flowInstanceId)
                putExtra(CommitRectifiedActivity.TASK_INSTANCE_ID, taskInstanceId)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 || requestCode == 2){
            if (resultCode == RESULT_OK){
                finish()
            }
        }
    }
}