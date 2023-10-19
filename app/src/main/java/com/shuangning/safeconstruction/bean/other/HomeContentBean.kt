package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.CONTENT
import com.shuangning.safeconstruction.base.adapter.ItemViewType

/**
 * Created by Chenwei on 2023/10/8.
 */
data class HomeContentBean(
    val imageId: Int = 0,
    val titleId: Int = 0,
    val functionId: Int = 0,
): ItemViewType(){
    override fun getItemType(): Int {
        return CONTENT
    }
}


