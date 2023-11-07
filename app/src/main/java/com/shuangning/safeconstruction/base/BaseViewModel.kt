package com.shuangning.safeconstruction.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Chenwei on 2023/10/2.
 */
open class BaseViewModel: ViewModel() {
    private val _toast: MutableLiveData<String?> = MutableLiveData()
    val toast: LiveData<String?> = _toast

}