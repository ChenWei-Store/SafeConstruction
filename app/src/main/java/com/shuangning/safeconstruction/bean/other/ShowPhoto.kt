package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.ui.adapter.SHOW_PHOTO

/**
 * Created by Chenwei on 2023/10/19.
 */
data class ShowPhoto(val url: String): ItemViewType(){
    override fun getItemType(): Int {
        return SHOW_PHOTO
    }
}
