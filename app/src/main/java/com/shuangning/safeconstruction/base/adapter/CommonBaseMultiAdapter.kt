package com.shuangning.safeconstruction.base.adapter

import androidx.viewbinding.ViewBinding


/**
 * Created by Chenwei on 2023/10/10.
 */
abstract class CommonBaseMultiAdapter<T: IItemViewType, V: ViewBinding>(data: MutableList<T>): CommonBaseAdapter<T, V>(data) {

    override fun getItemViewType(position: Int): Int {
        return data[position].getItemType()
    }

}