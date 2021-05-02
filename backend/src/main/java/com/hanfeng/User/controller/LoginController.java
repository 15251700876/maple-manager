package com.hanfeng.User.controller;

import com.hanfeng.User.entity.UserInfo;
import com.hanfeng.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * @author HanFeng
 */
@RestController
public class LoginController {

    /**
     * 登录 用户
     *
     * @param userInfo 用户信息
     * @return 1
     */
    @PostMapping("/login")
    public Object login(@RequestBody UserInfo userInfo) {

        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getAccount(), userInfo.getPassword());
        // 执行认证登陆
        String sid;
        try {
            subject.login(token);
            Serializable id = subject.getSession().getId();
            sid = String.valueOf(id);
        } catch (UnknownAccountException uae) {
            return Result.error("用户名或者密码错误!", "null");
        }
        return Result.success(sid);
    }


    @RequestMapping("/toLogin")
    public Object toLogin() {
        Result<String> result = new Result<>();
        return Result.success("", "");

    }


}
