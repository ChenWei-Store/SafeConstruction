package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.FinesDetailResp
import com.shuangning.safeconstruction.databinding.ActivityFinesDetailBinding
import com.shuangning.safeconstruction.ui.viewmodel.FinesDetailViewModel
import com.shuangning.safeconstruction.utils2.ActivityUtils
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/10/12.
 */
class FinesDetailActivity: BaseActivity<ActivityFinesDetailBinding>() {
    private var id = ""
    private val viewModel by viewModels<FinesDetailViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityFinesDetailBinding? {
        return ActivityFinesDetailBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("罚款详情")
    }

    private fun showDataToUi(finesDetailResp: FinesDetailResp) {

        binding?.apply {
            tvSafe.text = getfakuanleibie(finesDetailResp)
            tvPrice.text = finesDetailResp.leijijine.toString()
            tvCompany.text = finesDetailResp.beichufadanwei
            tvMoney.text = finesDetailResp.leijijine.toString()
            tvVetting.text = finesDetailResp.shifoushenpi
            tvCheckPerson.text = getJianChaRen(finesDetailResp.jiancharen)
            tvCheckUnit.text = finesDetailResp.jianchadanwei
        }
    }

    private fun getfakuanleibie(finesDetailResp: FinesDetailResp):String{
        val sb = StringBuilder()
        finesDetailResp.subVOS.forEach {
            sb.append(
                it.fakuanleibie
            ).append(",")
        }
        if (sb.length > 0){
            sb.delete(sb.length -1, sb.length)
        }
        return sb.toString()
    }
    private fun getJianChaRen(json: String?): String{
        if (json.isNullOrEmpty()){
            return ""
        }
        return try {
            val jsonObject = JSONObject(json)
            jsonObject.optJSONArray("user").optJSONObject(0).optString("fullName")
        }catch (e: Exception){
            ""
        }

    }
    override fun initData() {
        id = intent?.getStringExtra(ID)?:""
        LoadingManager.startLoading(this)
        viewModel.getData(id)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
    }

    override fun observeViewModel() {
        viewModel.data.observe(this){
            it?.let {
                showDataToUi(it)
                LoadingManager.stopLoading()
            }
        }
    }

    companion object{
        const val ID = "ID"
        fun startTo(ctx: Context, id: String){
            ActivityUtils.start(ctx, FinesDetailActivity::class.java){
                putExtra(ID, id)
            }
        }
    }

}