package com.hanfeng.auth;

import com.hanfeng.User.entity.UserInfo;
import com.hanfeng.util.Constant;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Date;

/**
 * @author HanFeng
 */
public class ShiroUtil {
    /**
     * @param password password
     * @param salt     salt
     * @return
     */
    public static String md5Password(String password, String salt) {
        // 迭代次数
        int hashIterations = Constant.HASH_ITERATIONS;
        // 加密算法MD5
        String algorithmName = Constant.MD5;
        return new SimpleHash(algorithmName, password,
                ByteSource.Util.bytes(salt), hashIterations).toHex();
    }

    public static String md5Password(String password, UserInfo userInfo) {

        String salt = getSalt(userInfo);
        // 迭代次数
        int hashIterations = Constant.HASH_ITERATIONS;
        // 加密算法MD5
        String algorithmName = Constant.MD5;
        return new SimpleHash(algorithmName, password,
                ByteSource.Util.bytes(salt), hashIterations).toHex();
    }


    /**
     * 根据用户名和用户的创建时间生成用户salt
     *
     * @param userInfo userInfo
     * @return 1
     */
    public static String getSalt(UserInfo userInfo) {
        if (userInfo == null) {
            throw new RuntimeException("用户信息未填写完整!");
        }
        if (userInfo.getAccount() == null) {
            throw new RuntimeException("用户信息未填写完整!");
        }
        if (userInfo.getCreateTime() == null) {
            userInfo.setCreateTime(new Date());
        }
        return userInfo.getAccount() + userInfo.getCreateTime().getTime();

    }

    public static void main(String[] args) {






    }

}
