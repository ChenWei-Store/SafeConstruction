package com.shuangning.safeconstruction.utils;


import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Chenwei on 2023/10/2.
 */
public class Base64Utils {
    private static final String CHARSET_UTF_8 = "UTF-8";
    /**
     * base64加密
     *
     * @param content
     * @return
     */
    public static String base64Encrypt(String content) throws Exception {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        return Base64.encodeToString(content.getBytes(CHARSET_UTF_8), Base64.DEFAULT);
    }

    /**
     * Base64解密
     * @return
     */
    public static String decodeToString(String content) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        return new String(Base64.decode(content.getBytes("UTF-8"), Base64.DEFAULT));
    }
}
