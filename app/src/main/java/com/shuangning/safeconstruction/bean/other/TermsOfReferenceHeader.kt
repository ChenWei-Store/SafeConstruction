package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType

/**
 * Created by Chenwei on 2023/10/18.
 */
data class TermsOfReferenceHeader(val title: String): ItemViewType(){
    override fun getItemType(): Int {
        return HEADER
    }
}