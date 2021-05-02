package com.hanfeng.util;

/**
 * @author HanFeng
 */
public interface Constant {
    /**
     * 加密方式 MD5
     */
    String MD5 = "MD5";
    /**
     * 加密次数
     */
    int HASH_ITERATIONS = 1024;

    /**
     * 单点登录是 登录sessionid 放置在heard中
     * sessionId key
     */
    String AUTHORIZATION = "Authorization";

    /**
     * 邮箱正则
     */
    String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 手机号正则
     */

    String PHONE_NUM_REGEX = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

}
