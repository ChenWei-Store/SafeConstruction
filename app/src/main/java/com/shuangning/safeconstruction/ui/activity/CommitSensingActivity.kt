package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.base.AddPhoto
import com.shuangning.safeconstruction.bean.base.ShowPhoto
import com.shuangning.safeconstruction.bean.request.CommitSensingReq
import com.shuangning.safeconstruction.databinding.ActivityCommitSensingBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.CommitSensingViewModel
import com.shuangning.safeconstruction.utils.GlideEngine
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils
import java.util.ArrayList
import java.util.Calendar
import java.util.Date

/**
 * Created by Chenwei on 2023/12/23.
 */
class CommitSensingActivity : BaseActivity<ActivityCommitSensingBinding>() {
    private var selectedCalendar: Calendar = Calendar.getInstance()
    private var selectedNextCalendar: Calendar = Calendar.getInstance()
    private var selectedTime = ""
    private var selectedNextTime = ""
    private var isQualified = true
    private val viewModel by viewModels<CommitSensingViewModel>()
    private val id: Int by lazy {
        intent?.getIntExtra(RepairListActivity.ID, -1)?:-1
    }
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityCommitSensingBinding? {
        return ActivityCommitSensingBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("检测登记")
    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.view1?.setOnClickListener {
            XPopCreateUtils.showYearMonthDialog(
                this@CommitSensingActivity,
                TimePickerPopup.Mode.YMD,
                selectedCalendar,
                object : TimePickerListener {
                    override fun onTimeChanged(date: Date?) {
                    }

                    override fun onTimeConfirm(date: Date?, view: View?) {
                        date?.let {
                            selectedTime = TimeUtils.parseTime(date, TimeUtils.yyyy_MM_dd)
                            selectedCalendar.time = date
                            binding?.tvTime?.text = selectedTime
                        }
                    }

                    override fun onCancel() {
                    }
                })
        }

        binding?.view3?.setOnClickListener {
            XPopCreateUtils.showYearMonthDialog(
                this@CommitSensingActivity,
                TimePickerPopup.Mode.YMD,
                selectedNextCalendar,
                object : TimePickerListener {
                    override fun onTimeChanged(date: Date?) {
                    }

                    override fun onTimeConfirm(date: Date?, view: View?) {
                        date?.let {
                            selectedNextTime = TimeUtils.parseTime(date, TimeUtils.yyyy_MM_dd)
                            selectedNextCalendar.time = date
                            binding?.tvTime2?.text = selectedNextTime
                        }
                    }

                    override fun onCancel() {
                    }
                })
        }

        binding?.tvSelected?.setOnClickListener {
            isQualified = true
            val tv = it as TextView
            UIUtils.setTextLeftDrawable(tv, R.drawable.selected)
            binding?.tvNotSelect?.apply {
                UIUtils.setTextLeftDrawable(this, R.drawable.not_select)
            }
        }

        binding?.tvNotSelect?.setOnClickListener {
            isQualified = false
            val tv = it as TextView
            UIUtils.setTextLeftDrawable(tv, R.drawable.selected)
            binding?.tvSelected?.apply {
                UIUtils.setTextLeftDrawable(this, R.drawable.not_select)
            }
        }

        binding?.viewBottom?.setOnClickListener {
            if (selectedTime.isEmpty()) {
                ToastUtil.showCustomToast("请先选择检测时间")
                return@setOnClickListener
            }
            if (selectedNextTime.isEmpty()) {
                ToastUtil.showCustomToast("请先选择下次检测时间")
                return@setOnClickListener
            }
            LoadingManager.startLoading(this)
            val status = if (isQualified){
                "合格"
            }else{
                "不合格"
            }
            val req = CommitSensingReq(selectedTime, status, selectedNextTime, id)
            viewModel.commit(req)
        }
    }

    override fun observeViewModel() {
        viewModel.result.observe(this){
            if (it){
                ToastUtil.showCustomToast("提交成功")
                finish()
            }else{
                ToastUtil.showCustomToast("提交失败")
            }
        }
    }

    companion object {
        const val ID = "id"
        fun startTo(ctx: Context, id: Int) {
            ActivityUtils.start(ctx, CommitSensingActivity::class.java) {
                putExtra(ID, id)
            }
        }
    }
}