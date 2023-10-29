package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.base.AddPhoto
import com.shuangning.safeconstruction.databinding.ActivityAddContentBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.utils.GlideEngine
import com.shuangning.safeconstruction.utils.ScreenUtil
import java.util.ArrayList

/**
 * Created by Chenwei on 2023/10/16.
 */
class AddContentActivity: BaseActivity<ActivityAddContentBinding>() {
    private val data: MutableList<ItemViewType> = mutableListOf()
    private var addShowPhotoAdapter: AddShowPhotoMultiAdapter?= null
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAddContentBinding? {
        return ActivityAddContentBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("添加内容")
        addShowPhotoAdapter = AddShowPhotoMultiAdapter(data)
        binding?.rvPic?.apply {
            layoutManager = GridLayoutManager(this@AddContentActivity, 4)
            addItemDecoration(GridSpaceItemDecoration(4, ScreenUtil.dp2px(16f),
                ScreenUtil.dp2px(8f), false))
            adapter = addShowPhotoAdapter
        }

    }

    override fun initData() {
        data.add(AddPhoto())
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.viewInspectionClassification?.setOnClickListener {
            val data = arrayOf("安全检查","质量检查")
            XPopCreateUtils.showListCenterDialog(this@AddContentActivity, data){
                    index, text->
            }
        }
        binding?.viewPartOfTender?.setOnClickListener {
            val data = arrayOf("GX-1标","GX-2标", "GX-21标")
            XPopCreateUtils.showListCenterDialog(this@AddContentActivity, data){
                    index, text->
            }
        }
        binding?.viewCheckList?.setOnClickListener {
            StartActivityManager.startSelectCheckList(this@AddContentActivity)
        }
        binding?.viewTermsOfReference?.setOnClickListener {
            StartActivityManager.startTermsOfReference(this@AddContentActivity)
        }
        addShowPhotoAdapter?.setListener(object: AddShowPhotoMultiAdapter.OnPhotoActionClickListener{
            override fun onAdd() {
                PictureSelector.create(this@AddContentActivity)
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
                addShowPhotoAdapter?.notifyItemRemoved(position)
            }

            override fun onImageClick() {
            }

        })
    }

    override fun observeViewModel() {
    }
}