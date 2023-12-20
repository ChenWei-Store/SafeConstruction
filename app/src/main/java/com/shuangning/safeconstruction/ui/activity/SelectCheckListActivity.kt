package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.IExpandable
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_ONE
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.other.SelectCheckHeader
import com.shuangning.safeconstruction.bean.other.SelectCheckLevelOne
import com.shuangning.safeconstruction.bean.other.SelectCheckLevelTwo
import com.shuangning.safeconstruction.databinding.ActivitySelectCheckListBinding
import com.shuangning.safeconstruction.ui.adapter.SelectCheckListMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.SelectCheckListViewModel
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/10/18.
 */
class SelectCheckListActivity : BaseActivity<ActivitySelectCheckListBinding>() {
    private var selectCheckListAdapter: SelectCheckListMultiAdapter? = null
    private var data: MutableList<IItemViewType> = mutableListOf()
    private val viewModel by viewModels<SelectCheckListViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivitySelectCheckListBinding? {
        return ActivitySelectCheckListBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("选择常见隐患及条款")
        selectCheckListAdapter = SelectCheckListMultiAdapter(data)
        binding?.rv?.apply {
            layoutManager = LinearLayoutManager(this@SelectCheckListActivity)
            adapter = selectCheckListAdapter
        }
    }

    override fun initData() {
        LoadingManager.startLoading(this)
        viewModel.getCheckList()
        data.add(SelectCheckHeader("检查项"))
//        val level2List = mutableListOf<SelectCheckLevelTwo>()
//        for(i in 1..5){
//            val level2 = SelectCheckLevelTwo("第一节2区三场建设")
//            level2List.add(level2)
//        }
//
//        val level1 = SelectCheckLevelOne("第一篇通用篇", level2List)
//        data.add(level1)
//        val level2List2 = mutableListOf<SelectCheckLevelTwo>()
//        for(i in 1..5){
//            val level2 = SelectCheckLevelTwo("第一节路基工程")
//            level2List2.add(level2)
//        }
//        val level11 = SelectCheckLevelOne("第二篇路基篇", level2List2)
//        data.add(level11)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        selectCheckListAdapter?.setOnItemClickListener(object : OnItemClickListener<IItemViewType> {
            override fun onItemClick(data: IItemViewType, position: Int) {
                if (data.getItemType() == HEADER) {
                    return
                }
                if (data is SelectCheckLevelTwo) {
                    val intent = Intent()
                    intent.putExtra("title", data.title)
                    intent.putExtra("id", data.id)
                    setResult(RESULT_OK, intent)
                    finish()
                    return
                }
                if (data.getItemType() == LEVEL_ONE) {
                    val expandable = data as? IExpandable<*>
                    expandable ?: return
                    if (expandable.isExpand()) {
                        selectCheckListAdapter?.collapse(position)
                    } else {
                        selectCheckListAdapter?.expand(position)
                    }
                }
            }
        })
    }

    override fun observeViewModel() {
        viewModel.result.observe(this) {
            it?.let {
                it.forEach {
                    val level1 = SelectCheckLevelOne(it.name, it.id)
                    it.childCheckList.forEach { it2 ->
                        val level2 = SelectCheckLevelTwo(it2.name, it2.id)
                        level1.subData.add(level2)
                    }
                    data.add(level1)
                }
            }
            selectCheckListAdapter?.notifyDataSetChanged()
            LoadingManager.stopLoading()
        }
    }

    companion object {
        fun startForResult(ctx: Context, requestCode: Int) {
            ActivityUtils.startForResult(ctx, SelectCheckListActivity::class.java, requestCode)
        }
    }
}