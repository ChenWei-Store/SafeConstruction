package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.base.widget.GridSpaceItemDecoration
import com.shuangning.safeconstruction.bean.base.AddPhoto
import com.shuangning.safeconstruction.bean.base.ShowPhoto
import com.shuangning.safeconstruction.bean.request.CommitRectifiedReq
import com.shuangning.safeconstruction.bean.response.UploadPhotoItem
import com.shuangning.safeconstruction.databinding.ActivityCommitRectifiedBinding
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.adapter.AddShowPhotoMultiAdapter
import com.shuangning.safeconstruction.ui.viewmodel.CommitRectifiedViewModel
import com.shuangning.safeconstruction.utils.GlideEngine
import com.shuangning.safeconstruction.utils.ScreenUtil
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils2.ActivityUtils
import java.util.ArrayList

/**
 * Created by Chenwei on 2023/12/16.
 */
class CommitRectifiedActivity: BaseActivity<ActivityCommitRectifiedBinding>() {
    private val maxLength = 500
    private var addShowPhotoAdapter: AddShowPhotoMultiAdapter? = null
    private val data: MutableList<ItemViewType> = mutableListOf()
    private val maxPhotoNum = 9
    private val viewModel by viewModels<CommitRectifiedViewModel>()
    private val flowInstanceId by lazy {
        intent?.getIntExtra(FLOW_INSTANCE_ID, 0)?:0
    }
    private val taskInstanceId by lazy {
        intent?.getIntExtra(TASK_INSTANCE_ID, 0)?:0
    }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityCommitRectifiedBinding? {
        return ActivityCommitRectifiedBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("提交整改")
        binding?.tvCount?.text = maxLength.toString()
        binding?.etContent?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        binding?.tvSubsequentMeasuresCount?.text = maxLength.toString()
        binding?.etSubsequentMeasures?.filters =
            arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        addShowPhotoAdapter = AddShowPhotoMultiAdapter(data)
        binding?.rvPic?.apply {
            layoutManager = GridLayoutManager(this@CommitRectifiedActivity, 4)
            addItemDecoration(
                GridSpaceItemDecoration(
                    4, ScreenUtil.dp2px(16f),
                    ScreenUtil.dp2px(8f), false
                )
            )
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
        addShowPhotoAdapter?.setListener(object :
            AddShowPhotoMultiAdapter.OnPhotoActionClickListener {
            override fun onAdd() {
                PictureSelector.create(this@CommitRectifiedActivity)
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
        binding?.etSubsequentMeasures?.doAfterTextChanged {
            val length = it.toString().length
            binding?.tvSubsequentMeasuresCount?.text = (maxLength - length).toString()
        }
        binding?.llCommit?.setOnClickListener {
            val desc = binding?.etContent?.text?.toString()
            if (desc.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请输入现场情况描述")
                return@setOnClickListener
            }
            val subsequentMeasures = binding?.etSubsequentMeasures?.text
            if (subsequentMeasures.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请输入后期措施")
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

    override fun observeViewModel() {
        viewModel.photos.observe(this) {
            val data = mutableListOf<UploadPhotoItem>()
            it?.let {
                data.addAll(it)
            }
            val desc = binding?.etContent?.text?.toString() ?: ""
            val rectificationRequirements =
                binding?.etSubsequentMeasures?.text?.toString() ?: ""
            val req = CommitRectifiedReq(
              flowInstanceId, taskInstanceId, data, desc, rectificationRequirements)
            viewModel.commit(req)
        }
        viewModel.uploadResult.observe(this) {
            LoadingManager.stopLoading()
            if (it) {
                ToastUtil.showCustomToast("提交成功")
                setResult(RESULT_OK)
                finish()
            }
        }
    }

    companion object{
        const val FLOW_INSTANCE_ID = "flowInstanceId"
        const val TASK_INSTANCE_ID = "taskInstanceId"
        fun startTo(ctx: Context, flowInstanceId: Int, taskInstanceId: Int, requestCode: Int){
            ActivityUtils.startForResult(ctx, CommitRectifiedActivity::class.java, requestCode){
                putExtra(FLOW_INSTANCE_ID, flowInstanceId)
                putExtra(TASK_INSTANCE_ID, taskInstanceId)
            }
        }
    }
}