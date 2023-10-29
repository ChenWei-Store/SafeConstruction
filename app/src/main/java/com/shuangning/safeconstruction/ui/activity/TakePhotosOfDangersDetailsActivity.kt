package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.base.ShowPhoto
import com.shuangning.safeconstruction.databinding.ActivityTakePhotosOfDangersDetailBinding
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/11.
 */
class TakePhotosOfDangersDetailsActivity: BaseActivity<ActivityTakePhotosOfDangersDetailBinding>() {
    private var photoAdapter: AddShowPhotoMultiAdapter?= null
    private var photoData = mutableListOf<ItemViewType>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityTakePhotosOfDangersDetailBinding? {
        return ActivityTakePhotosOfDangersDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.take_photos_of_dangers))
        binding?.tvTimeName?.text  = "2021-12-07 17:52 炎月"
        binding?.tvDesc?.text = "封闭段还可以打开吗"
        binding?.tvDepartmentDetail?.text = "xxx部门"
        binding?.tvTeamDetail?.text = "xxx组"
        binding?.tvTelDetail?.text = "13631xxxxxx"
        binding?.tvCheckItemDetail?.text = "xxx项目"
        binding?.tvReferenceClauseDetail?.text = "xxx条款"
        binding?.tvTimeName2?.text  = "2021-12-07 17:52 炎月"
        binding?.tvReceiveOrNotDetail?.text = "不接收"
        binding?.tvReceiveRemarkDetail?.text = "出入口已封闭，不再打开"

        photoAdapter = AddShowPhotoMultiAdapter(photoData)
        binding?.rv?.apply {
            layoutManager = GridLayoutManager(this@TakePhotosOfDangersDetailsActivity, 4)
            addItemDecoration(GridSpaceItemDecoration(4,
                ScreenUtil.dp2px(24f), ScreenUtil.dp2px(12f),false))
            adapter = photoAdapter
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