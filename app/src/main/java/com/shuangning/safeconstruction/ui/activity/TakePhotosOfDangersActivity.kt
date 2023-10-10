package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.TakePhotosOfDangersTitle
import com.shuangning.safeconstruction.databinding.ActivityTakePhotosOfDangersBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.TakePhotosOfDangersAdapter
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/10.
 */
class TakePhotosOfDangersActivity: BaseActivity<ActivityTakePhotosOfDangersBinding>() {
    private var takePhotoOfDangerAdapter:TakePhotosOfDangersAdapter? = null
    private val data: MutableList<TakePhotosOfDangersTitle> = mutableListOf()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityTakePhotosOfDangersBinding? {
        return ActivityTakePhotosOfDangersBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle(UIUtils.getString(R.string.take_photos_of_dangers))
        takePhotoOfDangerAdapter = TakePhotosOfDangersAdapter(data)
        binding?.rv?.apply {
            adapter = takePhotoOfDangerAdapter
            layoutManager = LinearLayoutManager(this@TakePhotosOfDangersActivity)
            addItemDecoration(DividerItemDecoration(this@TakePhotosOfDangersActivity, DividerItemDecoration.VERTICAL))
        }
        takePhotoOfDangerAdapter?.notifyDataSetChanged()
    }

    override fun initData() {
        data.add(TakePhotosOfDangersTitle("GX-1标"))
        data.add(TakePhotosOfDangersTitle("GX-2标"))
        data.add(TakePhotosOfDangersTitle("GX-21标"))
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        takePhotoOfDangerAdapter?.setOnItemClickListener(object: OnItemClickListener<TakePhotosOfDangersTitle>{
            override fun onItemClick(data: TakePhotosOfDangersTitle, position: Int) {
                StartActivityManager.startToTakePhotosOfDangersStatus(this@TakePhotosOfDangersActivity)
            }
        })

    }

    override fun observeViewModel() {
    }
}