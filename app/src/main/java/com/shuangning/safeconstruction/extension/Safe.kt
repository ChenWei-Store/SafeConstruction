package com.shuangning.safeconstruction.extension


/**
 * Created by Chenwei on 2023/10/2.
 */

fun String?.safeString(block:String.()-> Unit){
    if (this == null){
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

