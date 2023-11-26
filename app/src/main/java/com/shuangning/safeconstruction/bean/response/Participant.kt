package com.shuangning.safeconstruction.bean.response

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/11/26.
 */
@JsonClass(generateAdapter = true)
data class Participant(
    @Json(name = "user") val user: MutableList<ParticipantItem> = mutableListOf(),
)

@JsonClass(generateAdapter = true)
data class ParticipantItem(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "state") val state: String = "",
    @Json(name = "avatar") val avatar: String = "",
    @Json(name = "fullName") val fullName: String = "",
    @Json(name = "username") val username: String = "",
    @Json(name = "leaderIds") val leaderIds: String = "",
    @Json(name = "createTime") val createTime: String = "",
    @Json(name = "description") val description: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(state)
        parcel.writeString(avatar)
        parcel.writeString(fullName)
        parcel.writeString(username)
        parcel.writeString(leaderIds)
        parcel.writeString(createTime)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParticipantItem> {
        override fun createFromParcel(parcel: Parcel): ParticipantItem {
            return ParticipantItem(parcel)
        }

        override fun newArray(size: Int): Array<ParticipantItem?> {
            return arrayOfNulls(size)
        }
    }

}