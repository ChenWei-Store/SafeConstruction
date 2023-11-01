package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType

/**
 * Created by Chenwei on 2023/10/8.
 */
data class HomeHeaderBean(
    val imageId: Int = 0,
    val titleId: Int = 0,
    val functionId: Int = 0,
    val projectName: String,
    val bannerUrls: MutableList<String>,
): ItemViewType(){
    override fun getItemType(): Int {
        return HEADER
    }
}


