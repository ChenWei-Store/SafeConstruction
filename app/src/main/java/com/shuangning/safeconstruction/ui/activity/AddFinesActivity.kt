package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.other.FineBottom
import com.shuangning.safeconstruction.bean.other.FineItem
import com.shuangning.safeconstruction.bean.request.AddFineReq
import com.shuangning.safeconstruction.databinding.ActivityAddFinesBinding
import com.shuangning.safeconstruction.ui.adapter.AddFinesAdapter
import com.shuangning.safeconstruction.ui.viewmodel.AddFineViewModel
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils2.ActivityUtils

/**
 * Created by Chenwei on 2023/10/14.
 */
class AddFinesActivity : BaseActivity<ActivityAddFinesBinding>() {
    private val data: MutableList<ItemViewType> = mutableListOf()
    private var addFinesAdapter: AddFinesAdapter? = null
    private val viewModel by viewModels<AddFineViewModel>()
    private var section: Array<String>? = null
    private var partOfTender: String = "" //标段

    private val id: String by lazy {
        intent?.getStringExtra(ID) ?: ""
    }
    private val fineBottom = FineBottom()
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
        LoadingManager.startLoading(this)
        viewModel.getData()
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewBottom?.setOnClickListener {
            addFinesAdapter?.let {
                if (it.itemCount <= 1){
                    ToastUtil.showCustomToast("请添加罚款项")
                    return@setOnClickListener
                }
                LoadingManager.startLoading(this)
                val req = AddFineReq(fineBottom.beifakuandanwei, fineBottom.jianchadanwei, id, fineBottom.totalMoney)
                viewModel.commit(req)
            }

        }

    }

    override fun observeViewModel() {
        viewModel.sectionResult.observe(this) {
            section = it?.toTypedArray()
            section?.let {
                partOfTender = it[0]
                fineBottom.beifakuandanwei = partOfTender
                fineBottom.jianchadanwei = partOfTender
                data.add(fineBottom)
                addFinesAdapter?.setSelection(it)
                addFinesAdapter?.notifyDataSetChanged()
                LoadingManager.stopLoading()
            }
        }
        viewModel.result.observe(this){
            if (it){
                finish()
            }
            LoadingManager.stopLoading()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val money = intent?.getFloatExtra("money", 0.0f) ?: 0.0f
                val dealType = intent?.getStringExtra("dealType") ?: ""
                val desc = intent?.getStringExtra("desc") ?: ""
                val fineItem = FineItem(dealType, money, desc)
                this.data.add(0, fineItem)
                fineBottom.totalMoney += money
                addFinesAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun receiveEvent(code: Int, obj: Any?) {
        super.receiveEvent(code, obj)
    }

    companion object {
        const val ID = "id"
        fun startTo(ctx: Context, id: String) {
            ActivityUtils.start(ctx, AddFinesActivity::class.java) {
                putExtra(ID, id)
            }
        }
    }
}