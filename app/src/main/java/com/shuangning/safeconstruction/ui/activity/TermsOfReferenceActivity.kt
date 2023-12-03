package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.IExpandable
import com.shuangning.safeconstruction.base.adapter.IItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_ONE
import com.shuangning.safeconstruction.base.adapter.LEVEL_THREE
import com.shuangning.safeconstruction.base.adapter.LEVEL_TWO
import com.shuangning.safeconstruction.base.adapter.OnItemClickListener
import com.shuangning.safeconstruction.bean.other.TermsOfReferenceHeader
import com.shuangning.safeconstruction.bean.other.TermsOfReferenceLevelOne
import com.shuangning.safeconstruction.bean.other.TermsOfReferenceLevelThree
import com.shuangning.safeconstruction.bean.other.TermsOfReferenceLevelTwo
import com.shuangning.safeconstruction.databinding.ActivityTermsOfReferenceBinding
import com.shuangning.safeconstruction.ui.adapter.TermsOfReferencesMultiAdapter
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/10/19.
 */
class TermsOfReferenceActivity: BaseActivity<ActivityTermsOfReferenceBinding>() {
    private var termsOfReferencesAdapter: TermsOfReferencesMultiAdapter?= null
    private var data: MutableList<IItemViewType> = mutableListOf()

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityTermsOfReferenceBinding? {
        return ActivityTermsOfReferenceBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("选择常见隐患及条款")
        termsOfReferencesAdapter = TermsOfReferencesMultiAdapter(data)
        binding?.rv?.apply {
            layoutManager = LinearLayoutManager(this@TermsOfReferenceActivity)
            adapter = termsOfReferencesAdapter
        }
    }

    override fun initData() {
        data.add(TermsOfReferenceHeader("平安守护系统应用"))
        val level31 = mutableListOf<TermsOfReferenceLevelThree>()
        for(i in 1..5){
            val level3 = TermsOfReferenceLevelThree("施工现场生产区生活区办公区应分开放置")
            level31.add(level3)
        }
        val level21 = mutableListOf<TermsOfReferenceLevelTwo>()
//        for(i in 1..5){
            val level2 = TermsOfReferenceLevelTwo("一般规定", level31)
            level21.add(level2)
//        }
        val level1 = TermsOfReferenceLevelOne("表1.1临时用房现场安全检查表", level21)
        data.add(level1)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        termsOfReferencesAdapter?.setOnItemClickListener(object: OnItemClickListener<IItemViewType> {
            override fun onItemClick(data: IItemViewType, position: Int) {
                if (data is TermsOfReferenceLevelThree) {
                    val intent = Intent()
                    intent.putExtra("data", data.title)
                    setResult(RESULT_OK, intent)
                    finish()
                    return
                }
                if (data.getItemType() == LEVEL_ONE || data.getItemType() == LEVEL_TWO) {
                    val expandable = data as? IExpandable<*>
                    expandable ?: return
                    if (expandable.isExpand()) {
                        termsOfReferencesAdapter?.collapse(position)
                    } else {
                        termsOfReferencesAdapter?.expand(position)
                    }
                }
            }
        })
    }

    override fun observeViewModel() {
    }

    companion object{
        fun startForResult(ctx: Context, requestCode: Int){
            ActivityUtils.startForResult(ctx, TermsOfReferenceActivity::class.java, requestCode)
        }
    }
}