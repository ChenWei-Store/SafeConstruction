package com.shuangning.safeconstruction.utils2

import android.os.Parcelable
import com.shuangning.safeconstruction.base.BaseApplication
import com.tencent.mmkv.MMKV

/**
 * Created by Chenwei on 2023/9/5.
 */
class KeyValueUtils {
    fun init(){
        MMKV.initialize(BaseApplication.appContext)
    }
    fun putAll(cryptKey:String? = null, put: MMKV.()->Unit){
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, cryptKey)
        kv.put()
    }

    fun putAllWithId(id: String, cryptKey:String? = null, put: MMKV.()->Unit){
        val kv =  MMKV.mmkvWithID(id, MMKV.MULTI_PROCESS_MODE, cryptKey)
        kv.put()
    }

    fun <T> get(cryptKey:String? = null, key: String, default: T): T{
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, cryptKey)
        return when(default){
            is String -> kv.decodeString(key, default)
            is Float -> kv.decodeFloat(key, default)
            is Boolean -> kv.decodeBool(key, default)
            is Int -> kv.decodeInt(key, default)
            is Long -> kv.decodeLong(key, default)
            is Double -> kv.decodeDouble(key, default)
            else -> Unit
        } as T
    }

    fun <T> getWithId(id: String, key: String, valueType: T, cryptKey:String? = null): T{
        val kv =  MMKV.mmkvWithID(id, MMKV.MULTI_PROCESS_MODE, cryptKey)
        return when(valueType){
            is String -> kv.decodeString(key, "")
            is Float -> kv.decodeFloat(key, 0f)
            is Boolean -> kv.decodeBool(key, false)
            is Int -> kv.decodeInt(key, 0)
            is Long -> kv.decodeLong(key, 0)
            is Double -> kv.decodeDouble(key, 0.0)
            else -> Unit
        } as T
    }

    fun <T: Parcelable> getParcelable(cryptKey:String? = null, key: String, tClass: Class<T>,
                                           default: T): T{
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, cryptKey)
        return kv.decodeParcelable(key, tClass, default) as T
    }

    fun <T: Parcelable> getParcelableWithId(id: String, cryptKey:String? = null, key: String, tClass: Class<T>,
                                                 default: T): T{
        val kv =  MMKV.mmkvWithID(id, MMKV.MULTI_PROCESS_MODE, cryptKey)
        return kv.decodeParcelable(key, tClass, default) as T
    }

    fun getBytes(cryptKey: String? = null, key: String): ByteArray?{
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, cryptKey)
        return kv.decodeBytes(key, ByteArray(0))
    }

    fun getBytesWithId(id: String, cryptKey:String? = null, key: String): ByteArray?{
        val kv =  MMKV.mmkvWithID(id, MMKV.MULTI_PROCESS_MODE, cryptKey)
        return kv.decodeBytes(key, ByteArray(0))
    }

    fun remove(cryptKey: String? = null, key: String){
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, cryptKey)
        kv.removeValueForKey(key)
    }

    fun removeWithId(id: String, cryptKey: String? = null, key: String){
        val kv =  MMKV.mmkvWithID(id, MMKV.MULTI_PROCESS_MODE, cryptKey)
        kv.removeValueForKey(key)
    }

    fun clear(cryptKey: String? = null){
        val kv = MMKV.defaultMMKV(MMKV.MULTI_PROCESS_MODE, cryptKey)
        kv.clearAll()
    }
    fun clearWithId(id: String, cryptKey: String? = null){
        val kv =  MMKV.mmkvWithID(id, MMKV.MULTI_PROCESS_MODE, cryptKey)
        kv.clearAll()
    }
}