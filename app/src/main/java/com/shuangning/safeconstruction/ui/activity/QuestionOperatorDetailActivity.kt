package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.base.ShowPhoto
import com.shuangning.safeconstruction.databinding.ActivityQuestionOperatorDetailBinding
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
class QuestionOperatorDetailActivity: BaseActivity<ActivityQuestionOperatorDetailBinding>() {
    private var photoData = mutableListOf<ItemViewType>()
    private var photoAdapter: AddShowPhotoMultiAdapter?= null

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

    override fun initData() {
        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=2048195462,703560066&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333", false))
        photoData.add(ShowPhoto("https://img0.baidu.com/it/u=666517787,2620707380&fm=253&fmt=auto&app=120&f=JPEG?w=1140&h=641", false))
        photoData.add(ShowPhoto("https://img1.baidu.com/it/u=2559867097,3726275945&fm=253&fmt=auto&app=138&f=JPEG?w=1333&h=500", false))
        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=638285213,1746517464&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800", false))
        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=2048195462,703560066&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333", false))
        photoData.add(ShowPhoto("https://img0.baidu.com/it/u=666517787,2620707380&fm=253&fmt=auto&app=120&f=JPEG?w=1140&h=641", false))
        photoData.add(ShowPhoto("https://img1.baidu.com/it/u=2559867097,3726275945&fm=253&fmt=auto&app=138&f=JPEG?w=1333&h=500", false))
        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=638285213,1746517464&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800", false))
        photoData.add(ShowPhoto("https://img2.baidu.com/it/u=2048195462,703560066&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333", false))
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