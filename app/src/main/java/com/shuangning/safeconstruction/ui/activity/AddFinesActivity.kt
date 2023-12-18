package com.shuangning.safeconstruction.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.ConstructionTeamResp
import com.shuangning.safeconstruction.bean.response.PersonResp
import com.shuangning.safeconstruction.databinding.ActivityAddFinesBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.ADD_FINE
import com.shuangning.safeconstruction.ui.adapter.AddFinesAdapter
import com.shuangning.safeconstruction.ui.adapter.FINE_BOTTOM
import com.shuangning.safeconstruction.ui.viewmodel.AddFineItemViewModel
import com.shuangning.safeconstruction.ui.viewmodel.AddFineViewModel
import com.shuangning.safeconstruction.utils.ToastUtil

/**
 * Created by Chenwei on 2023/10/14.
 */
class AddFinesActivity: BaseActivity<ActivityAddFinesBinding>() {
    private val data: MutableList<ItemViewType> = mutableListOf()
    private var addFinesAdapter: AddFinesAdapter?= null
    private val viewModel by viewModels<AddFineViewModel>()
    private var section: Array<String>? = null
    private val personConstructionTeams = mutableListOf<ConstructionTeamResp>()
    private var personResp: PersonResp?= null
    private var person = ""
    private var partOfTender: String = "" //标段

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAddFinesBinding? {
        return ActivityAddFinesBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("新增罚款")
        addFinesAdapter = AddFinesAdapter(data)
        binding?.rv?.apply {
            adapter = addFinesAdapter
            layoutManager = LinearLayoutManager(this@AddFinesActivity)
        }

    }

    override fun initData() {
        data.add(ItemViewType(FINE_BOTTOM))
        LoadingManager.startLoading(this)
        viewModel.getData()
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewBottom?.setOnClickListener {

        }

    }

    override fun observeViewModel() {
        viewModel.sectionResult.observe(this) {
            section = it?.toTypedArray()
            section?.let {
                partOfTender = it[0]
//                binding??.text = partOfTender
            }
        }
        viewModel.constructionTeamResult.observe(this) {
            LoadingManager.stopLoading()
            it?.let {
                personConstructionTeams.addAll(it)
            }
        }

        viewModel.personResult.observe(this) {
            LoadingManager.stopLoading()
            if (it == null || it.children.isNullOrEmpty()){
                ToastUtil.showCustomToast("当前施工队下没有整改处理人数据")
                return@observe
            }
            personResp = it
            personResp?.children?.let {
//                val data = arrayOfNulls<String>(it.size)
//                it.forEachIndexed { index, personItem ->
//                    data[index] = personItem.name
//                }
//                XPopCreateUtils.showListCenterDialog(this@ProblemReportActivity, data as Array<String>) { index, text ->
//                    person = text
//                    binding?.tvCauseAnalysis?.text = person
//                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1){
            if (resultCode == RESULT_OK){

            }
        }
    }


    override fun receiveEvent(code: Int, obj: Any?) {
        super.receiveEvent(code, obj)

    }
}