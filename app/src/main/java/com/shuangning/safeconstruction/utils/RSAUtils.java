package com.shuangning.safeconstruction.utils;

import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * Created by Chenwei on 2023/10/2.
 */
public class RSAUtils {
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static final String KEY_ALGORITHM = "RSA";
    private static KeyFactory keyFactory = null;

    static {
        try {
            keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static Key getPrivateKeyFromBase64KeyEncodeStr(String keyStr) {
        byte[] keyBytes = Base64.decode(keyStr, Base64.NO_WRAP);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        Key privateKey = null;
        try {
            privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 获取base64加密后的字符串的原始公钥
     *
     * @param keyStr
     * @return
     */
    public static Key getPublicKeyFromBase64KeyEncodeStr(String keyStr) {
        byte[] keyBytes = Base64.decode(keyStr, Base64.NO_WRAP);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        Key publicKey = null;
        try {
            publicKey = keyFactory.generatePublic(x509KeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     */
    public static String encryptPublicKey(byte[] data, String key) throws Exception {
        //解密密钥
        byte[] keyBytes = Base64.decode(key, Base64.NO_WRAP);
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//Android端使用
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] bytes = cipher.doFinal(data);
        return new String(Base64.encode(bytes, Base64.NO_WRAP));
    }

    /**
     * 私钥解密
     *
     * @param data       待解密数据
     * @param privateKey 密钥
     * @return byte[] 解密数据
     */
    public static String decryptPrivateKey(String data, String privateKey) throws Exception {


        Key decodePrivateKey = getPrivateKeyFromBase64KeyEncodeStr(privateKey);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//Android端使用
        cipher.init(Cipher.DECRYPT_MODE, decodePrivateKey);

        byte[] encryptedData = Base64.decode(data, Base64.NO_WRAP);
        byte[] decodedData = cipher.doFinal(encryptedData);
        String decodedDataStr = new String(decodedData);
        return decodedDataStr;
    }

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 使用公钥进行分段加密
     *
     * @param dataStr 要加密的数据
     * @return 公钥base64字符串
     * @throws Exception
     */
    public static String encryptByPublicKey(String dataStr, String publicKey)
            throws Exception {
        byte[] data = dataStr.getBytes();
        // 对公钥解密
        Key decodePublicKey = getPublicKeyFromBase64KeyEncodeStr(publicKey);
        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//Android端使用
        cipher.init(Cipher.ENCRYPT_MODE, decodePublicKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        String encodedDataStr = new String(Base64.encode(encryptedData, Base64.NO_WRAP));

        return encodedDataStr;
    }

    /**
     * 使用私钥进行分段解密
     *
     * @param dataStr 使用base64处理过的密文
     * @return 解密后的数据
     * @throws Exception
     */
    public static String decryptByPrivateKey(String dataStr, String privateKey)
            throws Exception {

        byte[] encryptedData = Base64.decode(dataStr, Base64.NO_WRAP);

        Key decodePrivateKey = getPrivateKeyFromBase64KeyEncodeStr(privateKey);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");//Android端使用
        cipher.init(Cipher.DECRYPT_MODE, decodePrivateKey);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        String decodedDataStr = new String(decryptedData, "utf-8");
        return decodedDataStr;
    }
}
