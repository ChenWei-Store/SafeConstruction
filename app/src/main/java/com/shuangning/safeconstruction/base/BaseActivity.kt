package com.shuangning.safeconstruction.base

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.BuildConfig
import com.shuangning.safeconstruction.base.dialog.LoadingManager
import com.shuangning.safeconstruction.utils2.ActivityUtils
import com.shuangning.safeconstruction.utils2.AdaptScreenUtil
import com.shuangning.safeconstruction.utils2.EventbusUtils
import com.shuangning.safeconstruction.bean.base.MessageEvent
import com.shuangning.safeconstruction.constants.EventCode
import com.shuangning.safeconstruction.manager.StartActivityManager
import com.shuangning.safeconstruction.utils.DeviceUtils
import com.shuangning.safeconstruction.utils2.MyLog
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Chenwei on 2023/9/7.
 */
abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {
    protected var binding: VB? = null
    protected var curFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        initRequestOrientation()
        doBeforeSuperView()
        super.onCreate(savedInstanceState)
        doBeforeSetContentView()
        binding = getViewBinding(layoutInflater)
        if (BuildConfig.DESIGN_WIDTH_DP > 0){
            AdaptScreenUtil.setCustomDensity(this, application, BuildConfig.DESIGN_WIDTH_DP)
        }
        binding?.let {
            setContentView(it.root)
        }
        if(isHideActionbar()){
            supportActionBar?.hide()
        }
        observeViewModel()
        initData()
        initView(savedInstanceState)
        initListener()
        ActivityUtils.addActivity(this)

    }

    override fun onStart() {
        super.onStart()
        if(isRegisterEventbus()){
            EventbusUtils.register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if(isRegisterEventbus()){
            EventbusUtils.unregister(this)
        }
    }

    private fun initRequestOrientation() {
        if (DeviceUtils.isTablet()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        AdaptScreenUtil.resetScreen(this)
        ActivityUtils.removeActivity(this)

        LoadingManager.stopLoading()
    }

    override fun onBackPressed() {
      ActivityUtils.removeActivity()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        initRequestOrientation()
        AdaptScreenUtil.setCustomDensity(this, application, BuildConfig.DESIGN_WIDTH_DP)
    }
    open fun isRegisterEventbus(): Boolean{
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEventComming(event: MessageEvent?){
        event?.let {
            MyLog.d("onEventComming code: ${event.eventCode}")
            if (EventCode.LOGIN == event.eventCode){
                ActivityUtils.finishAllActivities()
                StartActivityManager.startToLogin(this)
            }else{
                receiveEvent(event.eventCode, event.obj)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public fun onStickyEventComming(event: MessageEvent?){
        event?.let {
            MyLog.d("onStickyEventComming code: ${event.eventCode}")
            receiveStrickyEvent(event.eventCode, event.obj)
        }
    }

    open fun receiveEvent(code: Int, obj: Any?){

    }
    open fun receiveStrickyEvent(code: Int, obj: Any?){

    }

    open fun isHideActionbar(): Boolean{
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        MyLog.d("onActivityResult requestCode:$requestCode resultCode:$resultCode")
    }
    abstract fun getViewBinding(layoutInflater: LayoutInflater): VB?
    abstract fun initView(savedInstanceState: Bundle?)
    abstract fun initData()
    abstract fun doBeforeSetContentView()
    abstract fun doBeforeSuperView()
    abstract fun initListener()
    abstract fun observeViewModel()

    protected fun switchFragment(to: Fragment, containerId: Int){
        val transaction = supportFragmentManager.beginTransaction()
        if (curFragment != null && curFragment != to){
            if(!to.isAdded){
                transaction.hide(curFragment!!).add(containerId, to).commitAllowingStateLoss()
            }else{
                transaction.hide(curFragment!!).show(to).commitAllowingStateLoss()
            }
            curFragment = to
        }else{
            curFragment = to
            if (!to.isAdded){
                transaction.add(containerId, to).commitAllowingStateLoss()
            }else{
                transaction.show(to).commitAllowingStateLoss()
            }
        }
    }

}