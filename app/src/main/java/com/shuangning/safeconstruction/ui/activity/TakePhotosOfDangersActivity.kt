package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.TakePhotosOfDangers
import com.shuangning.safeconstruction.databinding.ActivityTakePhotosOfDangersBinding
import com.shuangning.safeconstruction.manager.FROM_TAKE_PHOTO_OF_DANAGE
import com.shuangning.safeconstruction.manager.FROM_WHERE
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.ui.adapter.TakePhotosOfDangersAdapter
import com.shuangning.safeconstruction.utils.UIUtils

/**
 * Created by Chenwei on 2023/10/10.
 */
class TakePhotosOfDangersActivity: BaseActivity<ActivityTakePhotosOfDangersBinding>() {
    private var takePhotoOfDangerAdapter:TakePhotosOfDangersAdapter? = null
    private val data: MutableList<TakePhotosOfDangers> = mutableListOf()
    private var fromWhere = NONE
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityTakePhotosOfDangersBinding? {
        return ActivityTakePhotosOfDangersBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (fromWhere == FROM_TAKE_PHOTO_OF_DANAGE){
            binding?.viewTitle?.setTitle(UIUtils.getString(R.string.take_photos_of_dangers))
        }else{
            binding?.viewTitle?.setTitle(UIUtils.getString(R.string.group_education))
        }
        takePhotoOfDangerAdapter = TakePhotosOfDangersAdapter(data)
        binding?.rv?.apply {
            adapter = takePhotoOfDangerAdapter
            layoutManager = LinearLayoutManager(this@TakePhotosOfDangersActivity)
            addItemDecoration(DividerItemDecoration(this@TakePhotosOfDangersActivity, DividerItemDecoration.VERTICAL))
        }
        takePhotoOfDangerAdapter?.notifyDataSetChanged()
    }

    override fun initData() {
        data.add(TakePhotosOfDangers("GX-1标"))
        data.add(TakePhotosOfDangers("GX-2标"))
        data.add(TakePhotosOfDangers("GX-21标"))
        fromWhere = intent?.getIntExtra(FROM_WHERE, FROM_TAKE_PHOTO_OF_DANAGE)?:FROM_TAKE_PHOTO_OF_DANAGE
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        takePhotoOfDangerAdapter?.setOnItemClickListener(object: OnItemClickListener<TakePhotosOfDangers>{
            override fun onItemClick(data: TakePhotosOfDangers, position: Int) {
                if (fromWhere == FROM_TAKE_PHOTO_OF_DANAGE){
                    StartActivityManager.startToTakePhotosOfDangersStatus(this@TakePhotosOfDangersActivity)
                }else{
                    StartActivityManager.startGroupEducationList(this@TakePhotosOfDangersActivity)
                }
            }
        })

    }

    override fun observeViewModel() {
    }
}