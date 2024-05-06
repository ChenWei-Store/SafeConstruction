package com.shuangning.safeconstruction.ui.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.lxj.xpopupext.listener.TimePickerListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.base.AddPhoto
import com.shuangning.safeconstruction.bean.base.ShowPhoto
import com.shuangning.safeconstruction.bean.request.CommitRoutineInspectionReq
import com.shuangning.safeconstruction.bean.request.JianChaXiang
import com.shuangning.safeconstruction.bean.request.ZhengGaiChuLiRen
import com.shuangning.safeconstruction.bean.response.ConstructionTeamResp
import com.shuangning.safeconstruction.bean.response.PersonResp
import com.shuangning.safeconstruction.bean.response.UploadPhotoItem
import com.shuangning.safeconstruction.databinding.ActivityProblemReportBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.ProblemReportViewModel
import com.shuangning.safeconstruction.utils.GlideEngine
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.TimeUtils.yyyy_MM_dd_HH_mm_ss
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils2.BaiduLocation
import java.util.ArrayList
import java.util.Calendar
import java.util.Date

/**
 * Created by Chenwei on 2023/10/16.
 */
class ProblemReportActivity : BaseActivity<ActivityProblemReportBinding>(),
    ActivityResultCallback<ActivityResult>, BaiduLocation.OnLocationResultCallback {
    private val data: MutableList<ItemViewType> = mutableListOf()
    private var addShowPhotoAdapter: AddShowPhotoMultiAdapter? = null
    private var selectedCalendar: Calendar = Calendar.getInstance()
    private val maxLength = 500
    private var partOfTender: String = "" //标段
    private var inspectionClassification: String = "" //检查分类
    private var checkList: String = "" //检查项
    private var constructionTeam = "" //施工队
    private var selectedTime = "" //整改期限
    private var causeAnalysis = "" //原因分析
    private val maxPhotoNum = 9
    private val viewModel by viewModels<ProblemReportViewModel>()
    private var section: Array<String>? = null
    private val personConstructionTeams = mutableListOf<ConstructionTeamResp>()
    private var latitude: Double = 0.0 //纬度
    private var longitude: Double = 0.0 //精度
    private var personResp: PersonResp? = null
    private var person = ""
    private val jianchaxiang = JianChaXiang()
    private val persons = mutableListOf<String>()

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityProblemReportBinding? {
        return ActivityProblemReportBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("问题上报")
        addShowPhotoAdapter = AddShowPhotoMultiAdapter(data)
        binding?.rvPic?.apply {
            layoutManager = GridLayoutManager(this@ProblemReportActivity, 4)
            addItemDecoration(
                GridSpaceItemDecoration(
                    4, ScreenUtil.dp2px(16f),
                    ScreenUtil.dp2px(8f), false
                )
            )
            adapter = addShowPhotoAdapter
        }
        binding?.tvCount?.text = maxLength.toString()
        binding?.etContent?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        binding?.tvRectificationRequirementsCount?.text = maxLength.toString()
        binding?.etRectificationRequirements?.filters =
            arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        binding?.tvInspectionClassification?.text = inspectionClassification
    }

    override fun onStart() {
        super.onStart()
        BaiduLocation.start(this)
    }

    override fun onStop() {
        super.onStop()
        BaiduLocation.stop()
    }

    override fun initData() {
        data.add(AddPhoto())
//        partOfTender = "GX-1标"
        inspectionClassification = "安全检查"
        LoadingManager.startLoading(this)
        viewModel.getData()
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewInspectionClassification?.setOnClickListener {
            //检查分类
            val data = arrayOf("安全检查", "质量检查")
            XPopCreateUtils.showListCenterDialog(this@ProblemReportActivity, data) { index, text ->
                inspectionClassification = text
                binding?.tvInspectionClassification?.text = inspectionClassification
            }
        }
        binding?.viewPartOfTender?.setOnClickListener {
            //标段
            section?.let {
                XPopCreateUtils.showListCenterDialog(
                    this@ProblemReportActivity,
                    it
                ) { index, text ->
                    partOfTender = text
                    binding?.tvPartOfTender?.text = partOfTender
                }
            }
        }
        binding?.viewConstructionTeam?.setOnClickListener {
            //施工队
            if (partOfTender.isEmpty()) {
                ToastUtil.showCustomToast("请先选择标段")
                return@setOnClickListener
            }
            val data = getConstructionTeam()
            if (data.isEmpty()) {
                ToastUtil.showCustomToast("当前标段下没有施工队")
                return@setOnClickListener
            }
            XPopCreateUtils.showListCenterDialog(this@ProblemReportActivity, data) { index, text ->
                constructionTeam = text
                binding?.tvConstructionTeam?.text = constructionTeam
            }
        }

        binding?.viewRectificationPeriod?.setOnClickListener {
            //整改期限
            XPopCreateUtils.showYearMonthDialog(
                this@ProblemReportActivity,
                TimePickerPopup.Mode.YMDHM,
                selectedCalendar,
                object : TimePickerListener {
                    override fun onTimeChanged(date: Date?) {
                    }

                    override fun onTimeConfirm(date: Date?, view: View?) {
                        date?.let {
                            selectedTime = TimeUtils.parseTime(date, yyyy_MM_dd_HH_mm_ss)
                            selectedCalendar.time = date
                            binding?.tvRectificationPeriod?.text = selectedTime
                        }
                    }

                    override fun onCancel() {
                    }
                })
        }

        binding?.viewCheckList?.setOnClickListener {
            //检查项
            SelectCheckListActivity.startForResult(this@ProblemReportActivity, 1)
        }

        addShowPhotoAdapter?.setListener(object :
            AddShowPhotoMultiAdapter.OnPhotoActionClickListener {
            override fun onAdd() {
                PictureSelector.create(this@ProblemReportActivity)
                    .openGallery(SelectMimeType.ofImage())
                    .setMaxSelectNum(maxPhotoNum)
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .forResult(object : OnResultCallbackListener<LocalMedia> {
                        override fun onResult(result: ArrayList<LocalMedia>?) {
                            result.takeIf {
                                !it.isNullOrEmpty()
                            }?.let {
                                if (it.isNotEmpty()) {
                                    data.clear()
                                    it.forEach {
                                        val path = it.realPath
                                        val item = ShowPhoto(path, true)
                                        data.add(item)
                                    }
                                }
                                if (data.size < 9) {
                                    data.add(AddPhoto())
                                }
                                addShowPhotoAdapter?.notifyDataSetChanged()
                            }
                        }

                        override fun onCancel() {
                        }

                    })
            }

            override fun onDelete(position: Int) {
            }

            override fun onImageClick() {
            }
        })

        binding?.etContent?.doAfterTextChanged {
            val length = it.toString().length
            binding?.tvCount?.text = (maxLength - length).toString()
        }
        binding?.etRectificationRequirements?.doAfterTextChanged {
            val length = it.toString().length
            binding?.tvRectificationRequirementsCount?.text = (maxLength - length).toString()
        }
        binding?.viewCauseAnalysis?.setOnClickListener {
            //整改处理人
            if (partOfTender.isEmpty()) {
                ToastUtil.showCustomToast("请先选择标段")
                return@setOnClickListener
            }
            if (constructionTeam.isEmpty()) {
                ToastUtil.showCustomToast("请先选择施工队")
                return@setOnClickListener
            }
            val id = getConstructionTeamId()
            LoadingManager.startLoading(this)
            viewModel.getPerson(id)
        }
        binding?.llCommit?.setOnClickListener {
            //提交
            val desc = binding?.etContent?.text?.toString()
            if (desc.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请输入现场情况描述")
                return@setOnClickListener
            }
            if (inspectionClassification.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请选择检查分类")
                return@setOnClickListener
            }
            if (checkList.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请选择检查项")
                return@setOnClickListener
            }
            if (partOfTender.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请选择标段")
                return@setOnClickListener
            }
            if (constructionTeam.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请选择施工队")
                return@setOnClickListener
            }
            val rectificationRequirements = binding?.etRectificationRequirements?.text
            if (rectificationRequirements.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请输入整改要求")
                return@setOnClickListener
            }
            if (selectedTime.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请选择整改期限")
                return@setOnClickListener
            }
            if (data.isEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请上传图片")
                return@setOnClickListener
            }
            val paths = mutableListOf<String>()
            data.forEach {
                if (it is ShowPhoto) {
                    paths.add(it.url)
                }
            }
            LoadingManager.startLoading(this)
            viewModel.uploadPhotos(paths)
        }
    }

    private fun getConstructionTeamId(): Int {
        var item: ConstructionTeamResp? = null
        personConstructionTeams.forEach {
//            if (it.name.contains(partOfTender)) {
//                item = it
//                return@forEach
//            }
            val index = it.name.indexOf("（")
            if (index >= 0) {
                val data = it.name.substring(0, index)
                if (data == partOfTender) {
                    item = it
                    return@forEach
                }
            }
        }
        val id = item?.let { it ->
            var id = 0
            if (it.name == constructionTeam) {
                id = it.id
            } else {
                val result = it.children.find {
                    it.name == constructionTeam
                }
                id = result?.id ?: 0
            }
            id
        } ?: let {
            0
        }
        return id
    }

    fun getPersonId(personName: String): Int {
        var id = 0
        personResp?.children?.forEach {
            if (it.name == personName) {
                id = it.id
                return@forEach
            }
        }
        return id
    }

    private fun getConstructionTeam(): Array<String> {
        if (personConstructionTeams.isEmpty()) {
            return emptyArray()
        }
        var item: ConstructionTeamResp? = null
        personConstructionTeams.forEach {
            val index = it.name.indexOf("（")
            if (index >= 0) {
                val data = it.name.substring(0, index)
                if (data == partOfTender) {
                    item = it
                    return@forEach
                }
            }

        }
        val data = item?.let {
            val array = arrayOfNulls<String>(it.children.size + 1)
            array[0] = it.name
            it.children.forEachIndexed { index, constructionTeamItem ->
                array[index + 1] = constructionTeamItem.name
            }
            array as Array<String>
        } ?: let {
            emptyArray()
        }
        return data

    }

    override fun observeViewModel() {
        viewModel.photos.observe(this) {
            val data = mutableListOf<UploadPhotoItem>()
            it?.let {
                data.addAll(it)
            }
            val users = mutableListOf<ZhengGaiChuLiRen>()
            persons.forEach {
                val id = getPersonId(it)
                val user = ZhengGaiChuLiRen(id)
                users.add(user)
            }
            val desc = binding?.etContent?.text?.toString() ?: ""
            val rectificationRequirements =
                binding?.etRectificationRequirements?.text?.toString() ?: ""
            val req = CommitRoutineInspectionReq(
                latitude,
                longitude,
                inspectionClassification,
                data,
                jianchaxiang,
                partOfTender,
                constructionTeam,
                selectedTime,
                desc,
                rectificationRequirements,
                users
            )
            viewModel.commit(req)
        }
        viewModel.uploadResult.observe(this) {
            LoadingManager.stopLoading()
            if (it) {
                ToastUtil.showCustomToast("提交成功")
                finish()
            }
        }
        viewModel.sectionResult.observe(this) {
            section = it?.toTypedArray()
            section?.let {
                partOfTender = it[0]
                binding?.tvPartOfTender?.text = partOfTender
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
            if (it == null || it.children.isNullOrEmpty()) {
                ToastUtil.showCustomToast("当前施工队下没有整改处理人数据")
                return@observe
            }
            personResp = it
            personResp?.children?.let { it2 ->
                val data = mutableListOf<String>()
                it2.forEachIndexed { idx, personItem ->
                    data.add(personItem.name)
                }
                persons.clear()
                AlertDialog.Builder(this@ProblemReportActivity)
                    .setTitle("请选择")
                    .setMultiChoiceItems(data.toTypedArray(), null) { dialog, i, isChecked ->
                        val item = data[i]
                        if (isChecked) {
                            persons.add(item)
                        } else {
                            if (persons.contains(item)) {
                                persons.remove(item)
                            }
                        }
                    }.setPositiveButton("确认"){
                        dialog, which ->
                        dialog.dismiss()
                        var personsText = ""
                        persons.forEach { personName ->
                            personsText += personName
                            personsText += ","
                        }
                        personsText = personsText.substring(0, personsText.length -1)
                        binding?.tvCauseAnalysis?.text = personsText
                    }.create()
                    .show()
            }
        }
    }

    override fun onActivityResult(result: ActivityResult) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> {
                    onCheckListResult(data)
                }
            }
        }
    }

    private fun onCheckListResult(data: Intent?) {
        checkList = data?.getStringExtra("title") ?: ""
        val id = data?.getIntExtra("id", -1) ?: -1
        jianchaxiang.id = id
        jianchaxiang.referent = checkList
        binding?.tvCheckList?.text = checkList
    }

    override fun onLocationResult(isSuccess: Boolean, result: BaiduLocation.LocationResult?) {
        if (isSuccess && result != null) {
            latitude = result.latitude
            longitude = result.longitude
        } else {
            latitude = 0.0
            longitude = 0.0
        }
    }
}