package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.bean.response.UserBaseInfoResp
import com.shuangning.safeconstruction.databinding.ActivityQrcodeResultBinding
import com.shuangning.safeconstruction.ui.viewmodel.QrcodeResultViewModel
import com.shuangning.safeconstruction.utils2.ActivityUtils
import com.shuangning.safeconstruction.utils2.ImageLoader
import org.json.JSONObject

/**
 * Created by Chenwei on 2023/12/2.
 */
class QrcodeResultActivity: BaseActivity<ActivityQrcodeResultBinding>() {
    private val userId: Int by lazy {
        intent?.getIntExtra(USER_ID, -1)?:-1
    }
    private val viewModel by viewModels<QrcodeResultViewModel>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityQrcodeResultBinding? {
        return ActivityQrcodeResultBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("人员信息")

    }

    override fun initData() {
        LoadingManager.startLoading(this)
        viewModel.getData(userId)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
    }

    override fun observeViewModel() {
        viewModel.result.observe(this){
            LoadingManager.stopLoading()
            it?.let {
                showData(it)
            }
        }
    }
    private fun showData(data: UserBaseInfoResp){
        binding?.tvName?.text = data.userName
        binding?.tvSex?.text = data.sex
        binding?.tvNum?.text = data.idCardNo
        binding?.tvPerson?.text = getNativePlace(data.nativePlace)
        binding?.tvCompanyName?.text = data.companyName
        binding?.tvPersonNum?.text = data.userNo
        binding?.tvDepartmentName?.text = data.departmentName
        binding?.tvWorkName?.text = data.jobName
        binding?.tvTeamsGroup?.text = data.teamGroup
        binding?.tvPersonType?.text = data.manType
        binding?.tvStatus?.text = data.enterStatus
        binding?.tvTime?.text = data.enterTime
        if (data.imageUrl.isNullOrEmpty()){
            binding?.ivHeader?.visibility = View.GONE
        }else{
            binding?.ivHeader?.visibility = View.VISIBLE
            binding?.ivHeader?.let { ImageLoader.loadUrl(this, data.imageUrl, it) }
        }
    }

    private fun getNativePlace(json: String): String{
        if (json.isEmpty()){
            return ""
        }
        return try {
            val jsonObject = JSONObject(json)
            val province = jsonObject.optString("province")
            val city = jsonObject.optString("city")
            val country = jsonObject.optString("country")
            province + city + country
        }catch (e: Exception){
            ""
        }

    }
    companion object{
        const val USER_ID = "userId"
        fun startTo(ctx: Context, userId: Int){
            ActivityUtils.start(ctx, QrcodeResultActivity::class.java){
                putExtra(USER_ID, userId)
            }
        }

    }
}