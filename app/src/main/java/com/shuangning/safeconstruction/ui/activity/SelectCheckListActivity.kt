package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.bin.david.form.data.form.IForm
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IExpandable
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_ONE
import com.shuangning.safeconstruction.base.adapter.LEVEL_TWO
import com.shuangning.safeconstruction.base.adapter.LevelType
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.SelectCheckHeader
import com.shuangning.safeconstruction.bean.other.SelectCheckLevelOne
import com.shuangning.safeconstruction.bean.other.SelectCheckLevelTwo
import com.shuangning.safeconstruction.databinding.ActivitySelectCheckListBinding
import com.shuangning.safeconstruction.ui.adapter.SelectCheckListAdapter

/**
 * Created by Chenwei on 2023/10/18.
 */
class SelectCheckListActivity: BaseActivity<ActivitySelectCheckListBinding>() {
    private var selectCheckListAdapter: SelectCheckListAdapter?= null
    private var data: MutableList<IItemViewType> = mutableListOf()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivitySelectCheckListBinding? {
        return ActivitySelectCheckListBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("选择常见隐患及条款")
        selectCheckListAdapter = SelectCheckListAdapter(data)
        binding?.rv?.apply {
            layoutManager = LinearLayoutManager(this@SelectCheckListActivity)
            adapter = selectCheckListAdapter
        }
    }

    override fun initData() {
        data.add(SelectCheckHeader("平安守护系统应用"))
        val level2List = mutableListOf<SelectCheckLevelTwo>()
        for(i in 1..5){
            val level2 = SelectCheckLevelTwo("第一节2区三场建设")
            level2List.add(level2)
        }

        val level1 = SelectCheckLevelOne("第一篇通用篇", level2List)
        data.add(level1)
        val level2List2 = mutableListOf<SelectCheckLevelTwo>()
        for(i in 1..5){
            val level2 = SelectCheckLevelTwo("第一节路基工程")
            level2List2.add(level2)
        }
        val level11 = SelectCheckLevelOne("第二篇路基篇", level2List2)
        data.add(level11)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        selectCheckListAdapter?.setOnItemClickListener(object: OnItemClickListener<IItemViewType>{
            override fun onItemClick(data: IItemViewType, position: Int) {
                if (data.getItemType() == HEADER || data.getItemType() == LEVEL_TWO){
                    finish()
                    return
                }
                if (data.getItemType() == LEVEL_ONE){
                    val expandable = data as? IExpandable<*>
                    expandable?: return
                    if (expandable.isExpand()){
                        selectCheckListAdapter?.collapse(position)
                    }else{
                        selectCheckListAdapter?.expand(position)
                    }
                }
            }

        })

    }

    override fun observeViewModel() {
    }
}