package com.shuangning.safeconstruction.extension


/**
 * Created by Chenwei on 2023/10/2.
 */

fun String?.safeString(block:String.()-> Unit){
    if (this == null || this.isEmpty()){
        return
    }
    block()
}

fun <T> Any?.safe(block: T.()-> Unit){
    if (this == null){
        return
    }
    block(this as T)
}

inline fun <R> Any?.safeObjects(vararg args: Any?, block: (args: List<Any>) -> R): Any?{
    val list = args.filterNotNullTo(ArrayList<Any>())
    return if (list.size == args.size && this != null){
        list.add(0, this)
        block(list)
        list
    }else{
        null
    }
}

