package com.shuangning.safeconstruction.ui.activity

import android.content.Context
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
import com.shuangning.safeconstruction.bean.response.QuestionOperatorResp
import com.shuangning.safeconstruction.databinding.ActivityCompletedDetailBinding
import com.shuangning.safeconstruction.databinding.ActivityToBeExamineDetailBinding
import com.shuangning.safeconstruction.databinding.ActivityToBeRectifedDetailBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.QuestionOperatorViewModel
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/10/11.
 */
class QuestionOperatorActivity : BaseActivity<ViewBinding>() {
    private var status: Int = ERROR
    private var photoData = mutableListOf<ItemViewType>()
    private var photoData2 = mutableListOf<ItemViewType>()
    private var photoAdapter: AddShowPhotoMultiAdapter? = null
    private var photoAdapter2: AddShowPhotoMultiAdapter? = null
    private val viewModel by viewModels<QuestionOperatorViewModel>()
    private var id = ""
    private var fromWhere = FROM_ROUTINE_INSPECTION
    private var checkoutNum = ""
    override fun getViewBinding(layoutInflater: LayoutInflater): ViewBinding? {
        return null
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    private fun showDataToUi(questionOperatorResp: QuestionOperatorResp) {
        when (status) {
            TO_BE_RECTIFIED -> {
                initToBeRectifed(questionOperatorResp)
            }

            TO_BE_EXAMINE -> {
                initToBeExamine(questionOperatorResp)
            }

            COMPLETED -> {
                initCompleted(questionOperatorResp)
            }
        }
        initListener()
    }

    private fun initToBeRectifed(questionOperatorResp: QuestionOperatorResp) {
        val urls = parsePhotoUrl(questionOperatorResp.xunchatupian)
        photoData.clear()
        urls.forEach {
            photoData.add(ShowPhoto(it))
        }
        binding = ActivityToBeRectifedDetailBinding.inflate(LayoutInflater.from(this))
        (binding as? ActivityToBeRectifedDetailBinding)?.apply {
            setContentView(root)
            viewTitle.setTitle(UIUtils.getString(R.string.question_operator))
            tvProblemRight.text = getTime(questionOperatorResp.createTime)
            tvProblemContent.text = questionOperatorResp.xianchangmiaoshu
            tvCheckSort.text = questionOperatorResp.jianchafenlei
            tvCheckItem.text = questionOperatorResp.jianchaxiang
            tvPartOfTender.text = questionOperatorResp.zhenggaiqixian
            tvCommitPerson.text = questionOperatorResp.zhenggaiyaoqiu
            if (urls.size > 0) {
                photoAdapter = AddShowPhotoMultiAdapter(photoData)
                rv.apply {
                    isNestedScrollingEnabled = false
                    layoutManager = GridLayoutManager(this@QuestionOperatorActivity, 4)
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

    private fun initToBeExamine(questionOperatorResp: QuestionOperatorResp) {
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
            tvType.text = questionOperatorResp.danweileixing
            tvCheckSort.text = questionOperatorResp.jianchafenlei
            tvCheckItem.text = questionOperatorResp.jianchaxiang
            tvPartOfTender.text = questionOperatorResp.biaoduan
            tvCommitPerson.text = questionOperatorResp.xingming
            if (urls.size > 0){
                photoAdapter = AddShowPhotoMultiAdapter(photoData)
                rv.apply {
                    isNestedScrollingEnabled = false
                    layoutManager = GridLayoutManager(this@QuestionOperatorActivity, 4)
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

    private fun initCompleted(questionOperatorResp: QuestionOperatorResp) {
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
            tvProblemRight.text = getTime(questionOperatorResp.createTime)
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
                    layoutManager = GridLayoutManager(this@QuestionOperatorActivity, 4)
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
                    layoutManager = GridLayoutManager(this@QuestionOperatorActivity, 4)
                    addItemDecoration(
                        GridSpaceItemDecoration(
                            4,
                            ScreenUtil.dp2px(24f), ScreenUtil.dp2px(12f), false
                        )
                    )
                    adapter = photoAdapter2
                }
            }
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
        fromWhere = intent?.getIntExtra(FROM_WHERE, FROM_ROUTINE_INSPECTION)?: FROM_ROUTINE_INSPECTION
        status = intent?.getIntExtra(STATUS, ERROR) ?: ERROR
        id = intent?.getStringExtra(ID) ?: ""
        if (fromWhere == FROM_ROUTINE_INSPECTION){
            LoadingManager.startLoading(this)
            viewModel.getData(id)
        }
//        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=2048195462,703560066&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333", false))
//        photoData.add(ShowPhoto("https://img0.baidu.com/it/u=666517787,2620707380&fm=253&fmt=auto&app=120&f=JPEG?w=1140&h=641", false))
//        photoData.add(ShowPhoto("https://img1.baidu.com/it/u=2559867097,3726275945&fm=253&fmt=auto&app=138&f=JPEG?w=1333&h=500", false))
//        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=638285213,1746517464&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800", false))
//        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=2048195462,703560066&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333", false))
//        photoData.add(ShowPhoto("https://img0.baidu.com/it/u=666517787,2620707380&fm=253&fmt=auto&app=120&f=JPEG?w=1140&h=641", false))
//        photoData.add(ShowPhoto("https://img1.baidu.com/it/u=2559867097,3726275945&fm=253&fmt=auto&app=138&f=JPEG?w=1333&h=500", false))
//        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=638285213,1746517464&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800", false))
//        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=2048195462,703560066&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333", false))
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
                        FinesListActivity.startTo(this@QuestionOperatorActivity,checkoutNum)
                    }
                    it.viewProblemTitle.setOnClickListener {
                        StartActivityManager.startToQuestionOperatorDetail(this@QuestionOperatorActivity)
                    }
                }

                is ActivityCompletedDetailBinding -> {
                    it.floatingbutton.setOnClickListener {
                        FinesListActivity.startTo(this@QuestionOperatorActivity, checkoutNum)
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
        viewModel.result.observe(this) {
            it?.let {
                checkoutNum = it.richangxunchabianhao
                showDataToUi(it)
            }
            LoadingManager.stopLoading()
        }

    }

    companion object {
        const val STATUS = "status"
        const val ID = "id"
        const val FROM_WHERE = "fromWhere"
        const val FROM_ROUTINE_INSPECTION = 0
        const val FROM_RECTIFICATION_PEPLY = 1
        fun startTo(ctx: Context, status: Int, id: String, fromWhere: Int) {
            ActivityUtils.start(ctx, QuestionOperatorActivity::class.java) {
                putExtra(STATUS, status)
                putExtra(ID, id)
                putExtra(FROM_WHERE, fromWhere)
            }
        }
    }
}