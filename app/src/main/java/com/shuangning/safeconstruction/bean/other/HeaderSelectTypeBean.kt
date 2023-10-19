package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.HEADER
import com.shuangning.safeconstruction.base.adapter.ItemViewType


/**
 * Created by Chenwei on 2023/10/14.
 */
class HeaderSelectTypeBean: ItemViewType() {
    override fun getItemType(): Int {
        return HEADER
    }
}


