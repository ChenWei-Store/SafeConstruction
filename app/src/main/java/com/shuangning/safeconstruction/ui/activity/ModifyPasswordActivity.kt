package com.shuangning.safeconstruction.ui.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.widget.TextView
import com.shuangning.safeconstruction.base.BaseActivity
import com.shuangning.safeconstruction.data.mmkv.MMKVResp
import com.shuangning.safeconstruction.databinding.ActivityModifyPasswordBinding
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.manager.UserInfoManager
import com.shuangning.safeconstruction.manager.XPopCreateUtils
import com.shuangning.safeconstruction.utils.ToastUtil
import com.shuangning.safeconstruction.utils2.ActivityUtils
import java.lang.ref.WeakReference

/**
 * Created by Chenwei on 2023/10/14.
 */
const val TOTAL_MS: Long = 61 * 1000L  //总时间
const val INTERVAL_MS: Long = 1 * 1000L //间隔时间
class ModifyPasswordActivity: BaseActivity<ActivityModifyPasswordBinding>() {
    private var countDown: CountDownTimer?= null
    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityModifyPasswordBinding? {
        return ActivityModifyPasswordBinding.inflate(layoutInflater)
    }

    override fun initView(savedInstanceState: Bundle?) {
        binding?.viewTitle?.setTitle("修改密码")
        UserInfoManager.getUserInfo()?.apply {
            binding?.tvPhone?.text = userName
        }
    }

    override fun initData() {
    }

    override fun doBeforeSetContentView() {
    }

    override fun doBeforeSuperView() {
    }

    override fun initListener() {
        binding?.btnCommit?.setOnClickListener {
            commit()
        }

        binding?.tvSms?.setOnClickListener {
            binding?.tvSms?.apply {
                isEnabled = false
                isClickable = false
                countDown = SmsCountDownTime(WeakReference(this)).apply {
                    start()
                }
            }
        }
    }

    private fun commit(){
        val pwd = binding?.etPwd?.text.toString()
        val repwd = binding?.etRepwd?.text.toString()
        val captchaCode = binding?.etSms?.text.toString()
        if (pwd.isBlank()){
            XPopCreateUtils.showTipDialog(this, "提示", "请输入新密码")
            return
        }
        if (repwd.isBlank()){
            XPopCreateUtils.showTipDialog(this, "提示", "请输入确认密码")
            return
        }
        if (pwd != repwd){
            XPopCreateUtils.showTipDialog(this, "提示", "新密码和确认密码不一致，请重新输入")
            return
        }
        if (captchaCode.isBlank()){
            XPopCreateUtils.showTipDialog(this, "提示", "请输入验证码")
            return
        }
        ToastUtil.showCustomToast("修改成功")
        UserInfoManager.clear()
        MMKVResp.resp.clear()
        ActivityUtils.finishAllActivities()
        StartActivityManager.startToLogin(this)
    }
    override fun observeViewModel() {
    }

    class SmsCountDownTime(private val tvRef: WeakReference<TextView>): CountDownTimer(TOTAL_MS, INTERVAL_MS) {
        override fun onTick(millisUntilFinished: Long) {
            tvRef.get()?.text = "${millisUntilFinished / 1000}s"
        }

        override fun onFinish() {
            tvRef.get()?.isEnabled = true
            tvRef.get()?.isClickable = true
            tvRef.get()?.text = "获取验证码"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDown?.cancel()
    }
}