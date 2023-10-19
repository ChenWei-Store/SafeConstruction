package com.shuangning.safeconstruction.bean.other

import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.base.adapter.LEVEL_THREE

/**
 * Created by Chenwei on 2023/10/18.
 */
data class TermsOfReferenceLevelThree(val title: String): ItemViewType(){
    override fun getItemType(): Int {
        return LEVEL_THREE
    }
}
