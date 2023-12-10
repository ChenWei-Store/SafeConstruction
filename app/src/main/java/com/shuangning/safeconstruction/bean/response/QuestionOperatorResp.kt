package com.shuangning.safeconstruction.bean.response

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Chenwei on 2023/12/10.
 */
@JsonClass(generateAdapter = true)
data class QuestionOperatorResp(
    @Json(name = "xianchangmiaoshu") val xianchangmiaoshu: String = "",
    @Json(name = "xunchatupian") val xunchatupian: String = "",
    @Json(name = "jianchafenlei") val jianchafenlei: String = "",
    @Json(name = "jianchaxiang") val jianchaxiang: String = "",
    @Json(name = "zhenggaiqixian") val zhenggaiqixian: String = "",
    @Json(name = "zhenggaiyaoqiu") val zhenggaiyaoqiu: String = "",
    @Json(name = "createTime") val createTime: String = "",
    @Json(name = "danweileixing") val danweileixing: String = "",
    @Json(name = "xingming") val xingming: String = "",
    @Json(name = "zhenggaichuliren") val zhenggaichuliren: String = "",
    @Json(name = "zhenggaishijian") val zhenggaishijian: String = "",
    @Json(name = "houqicuoshi") val houqicuoshi: String = "",
    @Json(name = "shenheyijian") val shenheyijian: String = "",
    @Json(name = "zhenggaitupian") val zhenggaitupian: String = "",
    @Json(name = "shenpijieguo") val shenpijieguo: String = "",
    @Json(name = "biaoduan") val biaoduan: String = "",
    @Json(name = "_richangxunchabianhao") val richangxunchabianhao: String = "",

): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(xianchangmiaoshu)
        parcel.writeString(xunchatupian)
        parcel.writeString(jianchafenlei)
        parcel.writeString(jianchaxiang)
        parcel.writeString(zhenggaiqixian)
        parcel.writeString(zhenggaiyaoqiu)
        parcel.writeString(createTime)
        parcel.writeString(danweileixing)
        parcel.writeString(xingming)
        parcel.writeString(zhenggaichuliren)
        parcel.writeString(zhenggaishijian)
        parcel.writeString(houqicuoshi)
        parcel.writeString(shenheyijian)
        parcel.writeString(zhenggaitupian)
        parcel.writeString(shenpijieguo)
        parcel.writeString(biaoduan)
        parcel.writeString(richangxunchabianhao)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionOperatorResp> {
        override fun createFromParcel(parcel: Parcel): QuestionOperatorResp {
            return QuestionOperatorResp(parcel)
        }

        override fun newArray(size: Int): Array<QuestionOperatorResp?> {
            return arrayOfNulls(size)
        }
    }

}