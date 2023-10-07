package com.shuangning.safeconstruction.utils2

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Chenwei on 2023/9/27.
 * TODO:
 */
object JsonUtils {
    fun get() = Moshi.Builder().build()

    /**
     * 对象转json，适合简单类型
     *
     */
    inline fun <reified T> toJson(t: T) =
        get().adapter(T::class.java).indent("").toJson(t) ?: ""

    /**
     * 对象转json，适合泛型类型
     *
     */
    inline fun <reified T> toJson(t: T, type: Type) =
        get().adapter<T>(type).indent("").toJson(t) ?: ""

    /**
     * json转对象，适合简单类型
     */
    inline fun <reified T> fromJson(json: String): T? =
        get().adapter(T::class.java).fromJson(json)
    /**
     * json转对象，适合泛型类型
     */
    inline fun <T> fromJson(json: String, type: Type): T? =
        get().adapter<T>(type).fromJson(json)


    /**
     * json转mutableList
     */
    inline fun <reified T> listFromJson(json: String): MutableList<T> = fromJson<MutableList<T>>(
        json, Types.newParameterizedType(
            MutableList::class.java, T::class.java
        )
    ) ?: mutableListOf()

    /**
     * json转mutableMap
     */
    inline fun <reified K, reified V> mapFromJson(json: String): MutableMap<K, V> = fromJson(
        json,
        Types.newParameterizedType(MutableMap::class.java, K::class.java, V::class.java)
    ) ?: mutableMapOf()

}