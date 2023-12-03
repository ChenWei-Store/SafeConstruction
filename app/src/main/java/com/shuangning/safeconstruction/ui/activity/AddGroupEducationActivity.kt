package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.base.widget.DrawableTextView
import com.shuangning.safeconstruction.bean.other.MultiSelectBean
import com.shuangning.safeconstruction.bean.request.AddGroupEducationReq
import com.shuangning.safeconstruction.bean.response.Participant
import com.shuangning.safeconstruction.bean.response.ParticipantItem
import com.shuangning.safeconstruction.bean.response.UploadVideoItem
import com.shuangning.safeconstruction.databinding.ActivityAddGroupEducationBinding
import com.shuangning.safeconstruction.extension.prepareStartForResult
import com.shuangning.safeconstruction.manager.FROM_GROUP_EDUCATION
import com.shuangning.safeconstruction.manager.PermissionManager
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.viewmodel.AddGroupEducationViewModel
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils
import com.shuangning.safeconstruction.utils2.JsonUtils
import com.shuangning.safeconstruction.utils2.MyLog
import java.io.File

/**
 * Created by Chenwei on 2023/11/4.
 */
class AddGroupEducationActivity : BaseActivity<ActivityAddGroupEducationBinding>(){
    private var data: MutableList<String> = mutableListOf()
    private var classData: String? = null
    private var isWork = true
    private var isAfterWork = false
    private val viewModel by viewModels<AddGroupEducationViewModel>()
    private val selection: String by lazy {
        intent?.getStringExtra(SELECTION) ?: ""
    }
    private val persons = mutableListOf<ParticipantItem>()
    private val selectedPerson = mutableListOf<ParticipantItem>()
    private val videos = mutableListOf<UploadVideoItem>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAddGroupEducationBinding? {
        return ActivityAddGroupEducationBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("班组教育")
    }

    override fun initData() {
        LoadingManager.startLoading(this)
        viewModel.getGroup(selection)
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.view1?.setOnClickListener {
            //选择班组
            val array = data.toList().toTypedArray()
            if (array == null || array.size == 0) {
                ToastUtil.showCustomToast("没有班组")
                return@setOnClickListener
            }
            XPopCreateUtils.showLisBottomDialog(this, array) { index, text ->
                classData = text
                binding?.tvClass?.text = text
                classData?.let {
                    LoadingManager.startLoading(this@AddGroupEducationActivity)
                    viewModel.getTeamInfo(selection, it)
                }
            }
        }

        binding?.view4?.setOnClickListener {
            //选择参会人员
            classData ?: let {
                ToastUtil.showCustomToast("请先选择班组")
                return@setOnClickListener
            }
            if (persons.isEmpty()) {
                ToastUtil.showCustomToast("没有参会人员")
                return@setOnClickListener
            }
            MultiSelectActivity.startForResult(
                this@AddGroupEducationActivity,
                FROM_GROUP_EDUCATION,
                persons,
                2
            )
        }

        binding?.ivAdd?.setOnClickListener {
            //录屏
            reqCameraPermissionAndStart()
        }

        binding?.tvNotWorks?.setOnDrawableClickListener(object :
            DrawableTextView.BaseOnDrawableClickListener() {
            override fun onDrawableLeftClick() {
                isWork = false
                binding?.tvNotWorks?.let {
                    UIUtils.setTextLeftDrawable(it, R.drawable.selected)
                }

                binding?.tvWorks?.let {
                    UIUtils.setTextLeftDrawable(it, R.drawable.not_select)
                }
            }
        })
        binding?.tvWorks?.setOnDrawableClickListener(object :
            DrawableTextView.BaseOnDrawableClickListener() {
            override fun onDrawableLeftClick() {
                isWork = true
                binding?.tvWorks?.let {
                    UIUtils.setTextLeftDrawable(it, R.drawable.selected)
                }

                binding?.tvNotWorks?.let {
                    UIUtils.setTextLeftDrawable(it, R.drawable.not_select)
                }
            }
        })

        binding?.tvPreworks?.setOnDrawableClickListener(object :
            DrawableTextView.BaseOnDrawableClickListener() {
            override fun onDrawableLeftClick() {
                isAfterWork = false
                binding?.tvPreworks?.let {
                    UIUtils.setTextLeftDrawable(it, R.drawable.selected)
                }

                binding?.tvAfterWorks?.let {
                    UIUtils.setTextLeftDrawable(it, R.drawable.not_select)
                }
            }
        })

        binding?.tvAfterWorks?.setOnDrawableClickListener(object :
            DrawableTextView.BaseOnDrawableClickListener() {
            override fun onDrawableLeftClick() {
                isAfterWork = false
                binding?.tvAfterWorks?.let {
                    UIUtils.setTextLeftDrawable(it, R.drawable.selected)
                }

                binding?.tvPreworks?.let {
                    UIUtils.setTextLeftDrawable(it, R.drawable.not_select)
                }
            }
        })

        binding?.btnCommit?.setOnClickListener {
            if (classData.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请选择班组")
                return@setOnClickListener
            }

            val trainTopic = binding?.tvNumber?.text.toString() ?: ""
            if (trainTopic.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "当前选择班组没有记录编号")
                return@setOnClickListener
            }

            val squadLeader = binding?.tvClassLeader?.text.toString() ?: ""
            if (squadLeader.isNullOrEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "当前选择班组没有班组长")
                return@setOnClickListener
            }
            if (selectedPerson.size == 0) {
                XPopCreateUtils.showTipDialog(this, "提示", "请选择参会人员")
                return@setOnClickListener
            }
            if (videos.isEmpty()) {
                XPopCreateUtils.showTipDialog(this, "提示", "请上传视频")
                return@setOnClickListener
            }
            val constructionState = if (isWork) {
                "施工"
            } else {
                "未施工"
            }
            val educationTime = if (isAfterWork) {
                "班后"
            } else {
                "班前"
            }
            val jsonVideo = JsonUtils.toJson(videos)
            MyLog.d("jsonVideo:$jsonVideo")
            val userNum = UserInfoManager.getUserInfo()?.userId ?: ""
            val participant = Participant(selectedPerson)
            val data = AddGroupEducationReq(
                classData!!,
                trainTopic,
                squadLeader,
                participant,
                constructionState,
                educationTime,
                userNum,
                jsonVideo
            )
            LoadingManager.startLoading(this)
            viewModel.commit(data)
        }
    }

    override fun observeViewModel() {
        viewModel.group.observe(this) {
            it?.let {
                data.addAll(it)
            }
            LoadingManager.stopLoading()
        }
        viewModel.teamInfo.observe(this) {
            it?.let {
                binding?.tvNumber?.text = it.trainTopic
                binding?.tvClassLeader?.text = it.squadLeader
                if (it.participant.user.isNotEmpty()) {
                    persons.addAll(it.participant.user)
                }
            }
            LoadingManager.stopLoading()
        }
        viewModel.video.observe(this) {
            it?.let { it2 ->
                if (it2.size > 0) {
                    videos.clear()
                    videos.add(it2[0])
                    binding?.player?.visibility = View.VISIBLE
                    binding?.player?.setUp(it2[0].url, true, "")
                }
            }
            LoadingManager.stopLoading()
        }
        viewModel.resp.observe(this) {
            if (it != null) {
                LoadingManager.stopLoading()
                ToastUtil.showCustomToast("提交成功")
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> {
                    val uri = data?.getStringExtra("uri") ?: ""
                    MyLog.d("uri2:$uri")
                    val path = getFilePathFromContentUri(Uri.parse(uri))
                    MyLog.d("path:$path")
                    val file = File(path)
                    LoadingManager.startLoading(this)
                    viewModel.uploadVideo(file)
                }

                2 -> {
                    val data = data?.getParcelableArrayListExtra<MultiSelectBean>("data")
                    data?.let {
                        it.forEach { it1 ->
                            persons.forEach { it2 ->
                                run {
                                    if (it1.id == it2.id && it1.isSelect) {
                                        selectedPerson.add(it2)
                                    }
                                }
                            }
                        }
                        if (selectedPerson.size > 0) {
                            binding?.tvPerson?.text = "已选择${selectedPerson.size}人"
                        }
                    }
                }
            }

        }
    }

    companion object {
        const val SELECTION = "selection"
        fun startTo(ctx: Context, selection: String) {
            ActivityUtils.start(ctx, AddGroupEducationActivity::class.java) {
                putExtra(SELECTION, selection)
            }
        }
    }

    private fun reqCameraPermissionAndStart() {
        PermissionManager.requestCamera(this) {
            ActivityUtils.startForResult(
                this@AddGroupEducationActivity,
                RecordVideoActivity::class.java, 1
            )
        }
    }

    /**
     * 把content uri转为 文件路径
     *
     * @param contentUri      要转换的content uri
     * @return
     */
    private fun getFilePathFromContentUri(contentUri: Uri): String {
        var filePath = ""
        val filePathColumn = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = contentResolver.query(contentUri, filePathColumn, null, null, null)
        cursor?.let {
            val columnIndex = it.getColumnIndex(filePathColumn[0])
            it.moveToFirst()
            filePath = it.getString(columnIndex)
            cursor.close()
        }

        return filePath
    }
}