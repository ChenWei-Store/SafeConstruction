package com.shuangning.safeconstruction.bean.other

import android.os.Parcel
import android.os.Parcelable
import com.shuangning.safeconstruction.base.adapter.ItemViewType

/**
 * Created by Chenwei on 2023/10/18.
 */
data class MultiSelectBean(
    val id: Int,
    val reason: String,
    var isSelect: Boolean = false
): ItemViewType(),Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()?:"",
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(reason)
        parcel.writeByte(if (isSelect) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MultiSelectBean> {
        override fun createFromParcel(parcel: Parcel): MultiSelectBean {
            return MultiSelectBean(parcel)
        }

        override fun newArray(size: Int): Array<MultiSelectBean?> {
            return arrayOfNulls(size)
        }
    }
}