package com.shuangning.safeconstruction.utils2

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonReaderSkipNullValuesWrapper
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 * Created by Chenwei on 2023/9/27.
 * TODO:
 */
object JsonUtils {
    fun get() = Moshi.Builder()
        .add(DefaultIfNullFactory())
        .build()

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

    /**
     * 如果解析时json中一个成员变量为null，则跳过不解析，需要在实体类中设置默认值
     */
    class DefaultIfNullFactory : JsonAdapter.Factory {
        override fun create(
            type: Type,
            annotations: MutableSet<out Annotation>,
            moshi: Moshi
        ): JsonAdapter<*>? {
            val delegate = moshi.nextAdapter<Any>(this, type, annotations)
            if (!annotations.isEmpty()) return null
            if (type === Boolean::class.javaPrimitiveType) return delegate
            if (type === Byte::class.javaPrimitiveType) return delegate
            if (type === Char::class.javaPrimitiveType) return delegate
            if (type === Double::class.javaPrimitiveType) return delegate
            if (type === Float::class.javaPrimitiveType) return delegate
            if (type === Int::class.javaPrimitiveType) return delegate
            if (type === Long::class.javaPrimitiveType) return delegate
            if (type === Short::class.javaPrimitiveType) return delegate
            if (type === Boolean::class.java) return delegate
            if (type === Byte::class.java) return delegate
            if (type === Char::class.java) return delegate
            if (type === Double::class.java) return delegate
            if (type === Float::class.java) return delegate
            if (type === Int::class.java) return delegate
            if (type === Long::class.java) return delegate
            if (type === Short::class.java) return delegate
            if (type === String::class.java) return delegate
            return object : JsonAdapter<Any>() {
                override fun fromJson(reader: JsonReader): Any? {
                    return if (reader.peek() == JsonReader.Token.BEGIN_OBJECT) {
                        delegate.fromJson(JsonReaderSkipNullValuesWrapper(reader))
                    } else {
                        delegate.fromJson(reader)
                    }
                }
                override fun toJson(writer: JsonWriter, value: Any?) {
                    return delegate.toJson(writer, value)
                }
            }
        }
    }



}