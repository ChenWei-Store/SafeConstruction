package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.base.ShowPhoto
import com.shuangning.safeconstruction.bean.response.QuestionOperatorResp
import com.shuangning.safeconstruction.databinding.ActivityQuestionOperatorDetailBinding
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/10/11.
 */
class QuestionOperatorDetailActivity: BaseActivity<ActivityQuestionOperatorDetailBinding>() {
    private var photoData = mutableListOf<ItemViewType>()
    private var photoAdapter: AddShowPhotoMultiAdapter?= null
    private var data: QuestionOperatorResp?= null

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityQuestionOperatorDetailBinding? {
        return ActivityQuestionOperatorDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.detail))
        data?.let {
            binding?.apply {
                tvProblemContent.text = it.xianchangmiaoshu
                tvContentNumber.text = "${it.biaoduan}_221228001"
                tvType.text = it.danweileixing
                tvCheckSort.text = it.jianchafenlei
                tvCheckItem.text = it.jianchaxiang
                tvPartOfTender.text = it.biaoduan
                tvConstructionTeamDate.text = "路基施工2队"
                tvRectificationRequirementRight.text = it.zhenggaishijian
                tvRectificationPeriod.text = it.zhenggaiqixian
                tvPerson.text = it.xingming
                photoAdapter = AddShowPhotoMultiAdapter(photoData)
                rv.apply {
                    isNestedScrollingEnabled = false
                    layoutManager = GridLayoutManager(this@QuestionOperatorDetailActivity, 4)
                    addItemDecoration(
                        GridSpaceItemDecoration(4,
                            ScreenUtil.dp2px(24f), ScreenUtil.dp2px(12f),false)
                    )
                    adapter = photoAdapter
                }
            }
        }


    }

    override fun initData() {
        data = intent?.getParcelableExtra(DATA)
        data?.let {
            val urls = parsePhotoUrl(it.xunchatupian)
            photoData.clear()
            urls.forEach {
                photoData.add(ShowPhoto(it))
            }
        }
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
    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {


    }

    override fun observeViewModel() {
    }

    companion object{
        const val DATA = "data"
        fun startTo(ctx: Context, data: QuestionOperatorResp){
            ActivityUtils.start(ctx, QuestionOperatorDetailActivity::class.java){
                putExtra(DATA, data)
            }
        }
    }
}