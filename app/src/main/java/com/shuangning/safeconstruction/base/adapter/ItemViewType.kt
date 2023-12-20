package com.shuangning.safeconstruction.base.adapter

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by Chenwei on 2023/10/8.
 */
const val HEADER = 0
const val CONTENT = 1
const val BOTTOM = 2
interface IItemViewType{
    fun getItemType() = CONTENT
}

open class ItemViewType(var type: Int = CONTENT): IItemViewType, Parcelable{
    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun getItemType(): Int {
        return type
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemViewType> {
        override fun createFromParcel(parcel: Parcel): ItemViewType {
            return ItemViewType(parcel)
        }

        override fun newArray(size: Int): Array<ItemViewType?> {
            return arrayOfNulls(size)
        }
    }
}

