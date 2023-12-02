package com.shuangning.safeconstruction.utils2

import com.shuangning.safeconstruction.BuildConfig
import com.shuangning.safeconstruction.TemplateAppIndex
import com.shuangning.safeconstruction.bean.base.MessageEvent
import org.greenrobot.eventbus.EventBus

/**
 * Created by Chenwei on 2023/9/8.
 */
object EventbusUtils {
    fun init(){
        EventBus.builder()
            .addIndex(TemplateAppIndex())
            .logNoSubscriberMessages(BuildConfig.DEBUG)
            .logNoSubscriberMessages(BuildConfig.DEBUG)
            .installDefaultEventBus()
    }
    private fun getEventbus() = EventBus.getDefault()
    fun register(any: Any) = getEventbus().register(any)
    fun unregister(any: Any) = getEventbus().unregister(any)
    fun post(code: Int, any: Any? = null)= getEventbus().post(MessageEvent(code, any))
    fun postSticky(code: Int, any: Any? = null) = getEventbus().postSticky(MessageEvent(code, any))
    fun cancelEventDelivery(event: MessageEvent) = getEventbus().cancelEventDelivery(event)
}