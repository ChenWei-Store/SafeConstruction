package com.shuangning.safeconstruction.bean.other

import android.os.Parcel
import android.os.Parcelable
import com.shuangning.safeconstruction.base.adapter.ItemViewType
import com.shuangning.safeconstruction.ui.adapter.FINE_BOTTOM

/**
 * Created by Chenwei on 2023/12/19.
 */
data class FineBottom(
    var beifakuandanwei: String = "",
    var jianchadanwei: String = "",
    var totalMoney: Float = 0f,
): Parcelable, ItemViewType() {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readFloat(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(beifakuandanwei)
        parcel.writeString(jianchadanwei)
        parcel.writeFloat(totalMoney)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FineBottom> {
        override fun createFromParcel(parcel: Parcel): FineBottom {
            return FineBottom(parcel)
        }

        override fun newArray(size: Int): Array<FineBottom?> {
            return arrayOfNulls(size)
        }
    }

    override fun getItemType(): Int {
        return FINE_BOTTOM
    }
}