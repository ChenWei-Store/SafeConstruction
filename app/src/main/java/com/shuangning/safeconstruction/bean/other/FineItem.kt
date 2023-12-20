package com.shuangning.safeconstruction.bean.other

import android.os.Parcel
import android.os.Parcelable
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.ui.adapter.FINE_DETAIL

/**
 * Created by Chenwei on 2023/12/19.
 */
data class FineItem(
    var dealType: String = "",
    var money: Float = 0f,
    var desc: String = ""
): Parcelable, ItemViewType() {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readFloat(),
        parcel.readString()?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dealType)
        parcel.writeFloat(money)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FineItem> {
        override fun createFromParcel(parcel: Parcel): FineItem {
            return FineItem(parcel)
        }

        override fun newArray(size: Int): Array<FineItem?> {
            return arrayOfNulls(size)
        }
    }

    override fun getItemType(): Int {
        return FINE_DETAIL
    }
}