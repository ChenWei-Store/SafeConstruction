package com.shuangning.safeconstruction.utils2

import android.util.Base64
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 * Created by Chenwei on 2023/10/3.
 */
object EncryptUtils {
    fun getEncryptData(publicKey:String, data:String):String{
        try{
            val publicBytes = Base64.decode(publicKey, Base64.NO_WRAP)
            val keySpec = X509EncodedKeySpec(publicBytes)
            val keyFactory = KeyFactory.getInstance("RSA")
            val pubKey = keyFactory.generatePublic(keySpec)
            val encryptCodeWord = Cipher.getInstance("RSA/ECB/PKCS1Padding")
                .apply { init(Cipher.ENCRYPT_MODE, pubKey) }
                .doFinal(data.toByteArray())
            return Base64.encodeToString(encryptCodeWord, Base64.NO_WRAP)
        }
        catch (ex:Exception){
            MyLog.e("getEncryptPwd error:${ex.message}" )
        }
        return data
    }
}