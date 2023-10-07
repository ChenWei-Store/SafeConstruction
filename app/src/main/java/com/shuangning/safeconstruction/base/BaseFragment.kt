package com.shuangning.safeconstruction.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.shuangning.safeconstruction.bean.base.MessageEvent
import com.shuangning.safeconstruction.utils2.EventbusUtils
import com.shuangning.safeconstruction.utils2.MyLog
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by Chenwei on 2023/9/7.
 * TODO:
 */
abstract class BaseFragment<VB: ViewBinding>: Fragment() {
    protected var activity: Activity? = null
    protected var binding: VB ?= null
    protected var root: View ?= null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as? Activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        if (isRegisterEventbus()){
            EventbusUtils.register(this)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isRegisterEventbus()){
            EventbusUtils.unregister(this)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root?.let {
            if (it.parent != null) {
                //把当前的根布局从其父控件中移除
                (it.parent as? ViewGroup)?.removeView(root)
            }
        }?:let {
            binding = getViewBinding(inflater, container)
            root = binding?.root
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initData()
        initView(savedInstanceState)
        initListener()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
    override fun onDestroy() {
        super.onDestroy()

    }
    open fun isRegisterEventbus(): Boolean{
        return false
    }
    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB?
    protected abstract fun initView(savedInstanceState: Bundle?)
    protected abstract fun initData()
    protected abstract fun initListener()
    protected abstract fun observeViewModel()
    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    /**
     * 返回按键触发时调用
     * @return 返回true代表自己处理返回逻辑,Activity不用处理
     * 返回false代表没有处理逻辑,交由Activity处理
     */
    open fun onBackPressed(): Boolean {
        return false
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onEventComming(event: MessageEvent?){
        event?.let {
            MyLog.d("onEventComming code: ${event.eventCode}")
            receiveEvent(event.eventCode, event.obj)
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
}