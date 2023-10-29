package com.shuangning.safeconstruction.bean.base

import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.ui.adapter.ADD_PHOTO

/**
 * Created by Chenwei on 2023/10/17.
 */
class AddPhoto: ItemViewType(){
    override fun getItemType(): Int {
        return ADD_PHOTO
    }
}

