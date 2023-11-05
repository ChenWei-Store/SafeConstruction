package com.shuangning.safeconstruction.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import com.shuangning.safeconstruction.R
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.base.widget.DrawableTextView
import com.shuangning.safeconstruction.bean.other.MultiSelectBean
import com.shuangning.safeconstruction.databinding.ActivityAddGroupEducationBinding
import com.shuangning.safeconstruction.extension.prepareStartForResult
import com.shuangning.safeconstruction.manager.FROM_GROUP_EDUCATION
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.utils.TimeUtils
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils.UIUtils
import java.util.Date

/**
 * Created by Chenwei on 2023/11/4.
 */
class AddGroupEducationActivity: BaseActivity<ActivityAddGroupEducationBinding>()
    , ActivityResultCallback<ActivityResult>{
    private var data: MutableList<String> = mutableListOf()
    private var classData: String?= null
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var isWork = true
    private var isAfterWork = false
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAddGroupEducationBinding? {
        return ActivityAddGroupEducationBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("班组教育")
        launcher = prepareStartForResult(this)
    }

    override fun initData() {
        data.add("钢箱梁安装")
        data.add("钢箱梁安装")
        data.add("钢箱梁安装")
        data.add("钢箱梁安装")
        data.add("钢箱梁安装")
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.view1?.setOnClickListener {
            //选择班组
            val array = data.toList().toTypedArray()
            XPopCreateUtils.showLisBottomDialog(this, array){
                    index, text->
                classData = text
                binding?.tvClass?.text = text
                binding?.tvNumber?.text = "$text-${TimeUtils.parseTime(Date(), TimeUtils.yyyyMMddHHmmss)}"
                binding?.tvClassLeader?.text = "AAABBBCCCDDD"
            }
        }

        binding?.view4?.setOnClickListener {
            //选择参会人员
            classData ?:let {
                ToastUtil.showCustomToast("请先选择班组")
                return@setOnClickListener
            }
            launcher.launch( MultiSelectActivity.getIntent(this@AddGroupEducationActivity, FROM_GROUP_EDUCATION))
        }

        binding?.ivAdd?.setOnClickListener {
            //录屏
        }

        binding?.tvNotWorks?.setOnDrawableClickListener(object:
            DrawableTextView.BaseOnDrawableClickListener(){
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
        binding?.tvWorks?.setOnDrawableClickListener(object:
            DrawableTextView.BaseOnDrawableClickListener(){
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

        binding?.tvPreworks?.setOnDrawableClickListener(object:
            DrawableTextView.BaseOnDrawableClickListener(){
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

        binding?.tvAfterWorks?.setOnDrawableClickListener(object:
            DrawableTextView.BaseOnDrawableClickListener(){
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

    }

    override fun observeViewModel() {
    }

    override fun onActivityResult(result: ActivityResult) {
        if(result.resultCode == RESULT_OK){
            val data = result.data?.getParcelableArrayListExtra<MultiSelectBean>("data")
            var name  = ""
            data?.forEach {
                if (it.isSelect){
                    name += it.reason
                }
            }
            binding?.tvPerson?.text = name
        }
    }
}