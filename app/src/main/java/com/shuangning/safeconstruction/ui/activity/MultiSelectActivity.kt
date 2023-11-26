package com.shuangning.safeconstruction.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.bean.other.MultiSelectBean
import com.shuangning.safeconstruction.bean.response.ParticipantItem
import com.shuangning.safeconstruction.databinding.ActivitySelectCauseBinding
import com.shuangning.safeconstruction.manager.FROM_GROUP_EDUCATION
import com.shuangning.safeconstruction.manager.FROM_PROBLEM_REPORT
import com.shuangning.safeconstruction.manager.FROM_WHERE
import com.shuangning.safeconstruction.ui.adapter.MultiSelectMultiAdapter

/**
 * Created by Chenwei on 2023/10/18.
 */
class MultiSelectActivity: BaseActivity<ActivitySelectCauseBinding>() {
    private var selectCauseAdapter: MultiSelectMultiAdapter?= null
    private var data: MutableList<ItemViewType> = mutableListOf()
    private var fromWhere: Int = NONE
    private val groupEducationData = mutableListOf<ParticipantItem>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivitySelectCauseBinding? {
        return ActivitySelectCauseBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        if (fromWhere == FROM_PROBLEM_REPORT) {
            binding?.viewTitle?.setTitle("原因选择")
        }else if (fromWhere == FROM_GROUP_EDUCATION){
            binding?.viewTitle?.setTitle("班组教育")
        }
        selectCauseAdapter = MultiSelectMultiAdapter(data)
        binding?.rv?.apply {
            layoutManager = LinearLayoutManager(this@MultiSelectActivity, LinearLayoutManager.VERTICAL, false)
            adapter = selectCauseAdapter
        }
    }

    override fun initData() {
        fromWhere = intent?.getIntExtra(FROM_WHERE, FROM_PROBLEM_REPORT)?:FROM_PROBLEM_REPORT
        data.add(ItemViewType(HEADER))
        if (fromWhere == FROM_GROUP_EDUCATION){
            val data1 = intent?.getParcelableArrayListExtra<ParticipantItem>(DATA)
            if (data1 != null){
                groupEducationData.addAll(data1)
                data1.forEach {
                    data.add(MultiSelectBean(it.id, it.fullName))
                }
            }
        }
//
//        data.add(MultiSelectBean("管理制度"))
//        data.add(MultiSelectBean("岗位职责"))
//        data.add(MultiSelectBean("专项方案"))
//        data.add(MultiSelectBean("安全措施"))
//        data.add(MultiSelectBean("思想行为"))
//        data.add(MultiSelectBean("经费保障"))
//        data.add(MultiSelectBean("其他"))
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.llConfirm?.setOnClickListener {
            val intent = Intent()
            val data = transformData()
            intent.putExtra("data", data)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun transformData(): ArrayList<MultiSelectBean>{
        val list = ArrayList<MultiSelectBean>()
        data.forEach {
            if (it is MultiSelectBean){
                list.add(it)
            }
        }
        return list
    }

    override fun observeViewModel() {
    }

    companion object{
        const val DATA = "data"
        fun getIntent(activity: Activity, fromWhere: Int, items1: MutableList<ParticipantItem>?, ): Intent{
            val intent = Intent(activity, MultiSelectActivity::class.java)
            intent.putExtra(FROM_WHERE, fromWhere)
            if (items1 != null){
                val list = ArrayList<ParticipantItem>()
                items1.forEach {
                    list.add(it)
                }
                intent.putParcelableArrayListExtra(DATA, list)
            }
            return intent
        }
    }

}