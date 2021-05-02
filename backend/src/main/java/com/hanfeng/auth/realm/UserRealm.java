package com.hanfeng.auth.realm;

import com.hanfeng.User.entity.UserInfo;
import com.hanfeng.User.service.IUserInfoService;
import com.hanfeng.auth.ShiroUtil;
import com.hanfeng.util.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;

/**
 * @author HanFeng
 */
@Service
public class UserRealm extends AuthorizingRealm {
    @Resource
    IUserInfoService userInfoService;

    /**
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Object primaryPrincipal = principalCollection.getPrimaryPrincipal();

        HashSet<String> roles = new HashSet<>();
        HashSet<String> permissions = new HashSet<>();

        authorizationInfo.setRoles(roles);

        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 执行授权逻辑
     *
     * @param authenticationToken token
     * @return 1
     * @throws AuthenticationException e
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //登录账号和密码
        String account = (String) authenticationToken.getPrincipal();
        UserInfo userInfo;
        if (account.matches(Constant.EMAIL_REGEX)) {
            userInfo = userInfoService.queryUserByEmail(account);
        } else if (account.matches(Constant.PHONE_NUM_REGEX)) {
            userInfo = userInfoService.queryByPhoneNum(account);
        } else {
            userInfo = userInfoService.queryByAccount(account);
        }
        if (userInfo == null) {
            throw new AccountException("用户不存在");
        }
        ByteSource salt = ByteSource.Util.bytes(ShiroUtil.getSalt(userInfo));
        return new SimpleAuthenticationInfo(userInfo.getAccount(), userInfo.getPassword(),
                salt, getName());
    }

    /**
     *
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        setCredentialsMatcher(hashedCredentialsMatcher());
    }

    /**
     * 11111
     *
     * @return HashedCredentialsMatcher
     */
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName(Constant.MD5);
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(Constant.HASH_ITERATIONS);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);

        return hashedCredentialsMatcher;
    }
}
