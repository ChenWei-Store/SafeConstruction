package com.shuangning.safeconstruction.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
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
import com.shuangning.safeconstruction.databinding.ActivityAddGroupEducationBinding
import com.shuangning.safeconstruction.extension.prepareStartForResult
import com.shuangning.safeconstruction.manager.FROM_GROUP_EDUCATION
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.ui.viewmodel.AddGroupEducationViewModel
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils.UIUtils
import com.shuangning.safeconstruction.utils2.ActivityUtils
import com.shuangning.safeconstruction.utils2.JsonUtils
import org.json.JSONObject
import java.io.File
import java.util.Date

/**
 * Created by Chenwei on 2023/11/4.
 */
class AddGroupEducationActivity : BaseActivity<ActivityAddGroupEducationBinding>(),
    ActivityResultCallback<ActivityResult> {
    private var data: MutableList<String> = mutableListOf()
    private var classData: String? = null
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var isWork = true
    private var isAfterWork = false
    private val viewModel by viewModels<AddGroupEducationViewModel>()
    private val selection: String by lazy {
        intent?.getStringExtra(SELECTION) ?: ""
    }
    private val persons = mutableListOf<ParticipantItem>()
    private val selectedPerson = mutableListOf<ParticipantItem>()
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAddGroupEducationBinding? {
        return ActivityAddGroupEducationBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("班组教育")
        launcher = prepareStartForResult(this)
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
            launcher.launch(
                MultiSelectActivity.getIntent(
                    this@AddGroupEducationActivity,
                    FROM_GROUP_EDUCATION,
                    persons
                )
            )
        }

        binding?.ivAdd?.setOnClickListener {
            //录屏
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "video/*"
            startActivityForResult(intent, 10)
//            val file = File()
//            viewModel.uploadVideo()
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
            if (classData.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "请选择班组")
                return@setOnClickListener
            }

            val trainTopic = binding?.tvNumber?.text.toString()?:""
            if (trainTopic.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "当前选择班组没有记录编号")
                return@setOnClickListener
            }

            val squadLeader = binding?.tvClassLeader?.text.toString()?:""
            if (squadLeader.isNullOrEmpty()){
                XPopCreateUtils.showTipDialog(this, "提示", "当前选择班组没有班组长")
                return@setOnClickListener
            }
            if (selectedPerson.size == 0){
                XPopCreateUtils.showTipDialog(this, "提示", "请选择参会人员")
                return@setOnClickListener
            }
            val constructionState = if (isWork){
                "施工"
            }else{
                "未施工"
            }
            val educationTime = if (isAfterWork){
                "班后"
            }else{
                "班前"
            }
            val userNum = UserInfoManager.getUserInfo()?.userId?:""
            val participant = Participant(selectedPerson)
            val jsonObject = JSONObject(JsonUtils.toJson(participant))
            val data = AddGroupEducationReq(classData!!, trainTopic, squadLeader, participant, constructionState, educationTime, userNum)
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
    }

    override fun onActivityResult(result: ActivityResult) {
        if (result.resultCode == RESULT_OK) {
            val data = result.data?.getParcelableArrayListExtra<MultiSelectBean>("data")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            val uri = data?.data
            uri?.let {
                val cursor = contentResolver.query(uri, null, null, null, null)
                if (cursor?.moveToFirst() == true) {
                    val videoPath =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
                    cursor.close()
                    val file = File(videoPath)
                    viewModel.uploadVideo(file)
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
}