package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.lxj.xpopupext.popup.TimePickerPopup
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.other.Photo
import com.shuangning.safeconstruction.databinding.ActivityAddContentBinding
import com.shuangning.safeconstruction.databinding.ActivityProblemReportBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.ADD_PHOTO
import com.shuangning.safeconstruction.ui.adapter.AddPhotoAdapter
import com.shuangning.safeconstruction.utils.GlideEngine
import com.shuangning.safeconstruction.utils.ScreenUtil
import java.util.ArrayList

/**
 * Created by Chenwei on 2023/10/16.
 */
class ProblemReportActivity: BaseActivity<ActivityProblemReportBinding>() {
    private val data: MutableList<ItemViewType> = mutableListOf()
    private var addPhotoAdapter: AddPhotoAdapter?= null
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityProblemReportBinding? {
        return ActivityProblemReportBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("问题上报")
        addPhotoAdapter = AddPhotoAdapter(data)
        binding?.rvPic?.apply {
            layoutManager = GridLayoutManager(this@ProblemReportActivity, 4)
            addItemDecoration(GridSpaceItemDecoration(4, ScreenUtil.dp2px(16f),
                ScreenUtil.dp2px(8f), false))
            adapter = addPhotoAdapter
        }

    }

    override fun initData() {
        data.add(Photo(type = ADD_PHOTO))
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewInspectionClassification?.setOnClickListener {
            val data = arrayOf("安全检查","质量检查")
            XPopCreateUtils.showListCenterDialog(this@ProblemReportActivity, data){
                    index, text->
            }
        }
        binding?.viewPartOfTender?.setOnClickListener {
            val data = arrayOf("GX-1标","GX-2标", "GX-21标")
            XPopCreateUtils.showListCenterDialog(this@ProblemReportActivity, data){
                    index, text->
            }
        }
        binding?.viewConstructionTeam?.setOnClickListener {
            val data = arrayOf("三场基建队","三场钢筋大棚安拆队", "三场砌墙队","三场临建队",
                "施工便道队","施工便桥队","桩基一队","桩基二队","路基一队","路基二队","下沟/小构1队",
                "下沟/小构2队", "钢筋加工场", "混凝土拌和站", "预制梁厂", "标准化建设")
            XPopCreateUtils.showListCenterDialog(this@ProblemReportActivity, data){
                    index, text->
            }
        }

        binding?.viewRectificationPeriod?.setOnClickListener {
            XPopCreateUtils.showYearMonthDialog(this@ProblemReportActivity, TimePickerPopup.Mode.YMDHM)
        }
        addPhotoAdapter?.setListener(object: AddPhotoAdapter.OnPhotoActionClickListener{
            override fun onAdd() {
                PictureSelector.create(this@ProblemReportActivity)
                    .openGallery(SelectMimeType.ofImage())
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .forResult(object: OnResultCallbackListener<LocalMedia>{
                        override fun onResult(result: ArrayList<LocalMedia>?) {
                        }

                        override fun onCancel() {
                        }

                    })
            }

            override fun onDelete(position: Int) {
                data.removeAt(position)
                addPhotoAdapter?.notifyItemRemoved(position)
            }
        })

        binding?.viewCauseAnalysis?.setOnClickListener {
            StartActivityManager.startToSelectCause(this@ProblemReportActivity)
        }
    }

    override fun observeViewModel() {
    }
}