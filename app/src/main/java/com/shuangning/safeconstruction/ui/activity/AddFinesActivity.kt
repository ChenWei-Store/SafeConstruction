package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.databinding.ActivityAddFinesBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils

/**
 * Created by Chenwei on 2023/10/14.
 */
class AddFinesActivity: BaseActivity<ActivityAddFinesBinding>() {
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAddFinesBinding? {
        return ActivityAddFinesBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("新增罚款")
    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewAddFineItem?.setOnClickListener {
            StartActivityManager.startToAddFineItem(this@AddFinesActivity)
        }

        binding?.viewFinesUnit?.setOnClickListener {
            XPopCreateUtils.showListCenterDialog(this@AddFinesActivity, arrayOf("GX-2标", "GX-1标")){
                    position, text->
            }
        }
        binding?.viewVettingPerson?.setOnClickListener {
            XPopCreateUtils.showListCenterDialog(this@AddFinesActivity, arrayOf("指挥部", "xxx部")){
                    position, text->
            }
        }
    }

    override fun observeViewModel() {
    }
}