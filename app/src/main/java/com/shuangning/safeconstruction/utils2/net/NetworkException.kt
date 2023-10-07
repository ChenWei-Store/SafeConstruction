package com.shuangning.safeconstruction.utils2.net

/**
 * Created by Chenwei on 2023/9/27.
 */
class NetworkException(val code: Int, override val message: String): Exception() {
    override fun toString(): String {
        return "code:$code message:$message"
    }
}