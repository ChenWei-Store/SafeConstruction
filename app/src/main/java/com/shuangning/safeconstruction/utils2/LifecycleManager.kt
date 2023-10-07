package com.shuangning.safeconstruction.utils2

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created by Chenwei on 2023/9/11.
 * TODO:111
 */
class LifecycleManager: LifecycleEventObserver {
    fun register(lifecycleOwner: LifecycleOwner){
        register(lifecycleOwner.lifecycle)
    }

    fun register(lifecycle: Lifecycle){
        lifecycle.addObserver(this)
    }

    fun onCreate(){
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event){
            Lifecycle.Event.ON_CREATE ->{}
            Lifecycle.Event.ON_START->{}
            Lifecycle.Event.ON_RESUME->{}
            Lifecycle.Event.ON_PAUSE->{}
            Lifecycle.Event.ON_STOP->{}
            Lifecycle.Event.ON_DESTROY->{}
            Lifecycle.Event.ON_ANY->{}
            else -> {}
        }
    }
}