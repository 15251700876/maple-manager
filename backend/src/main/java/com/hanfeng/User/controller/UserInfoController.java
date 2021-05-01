package com.hanfeng.User.controller;

import com.hanfeng.User.entity.UserInfo;
import com.hanfeng.User.service.IUserInfoService;
import com.hanfeng.util.Result;
import com.hanfeng.util.page.PageBody;
import com.hanfeng.util.page.PageQuery;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (UserInfo)表控制层
 *
 * @author makejava
 * @since 2021-05-01 11:33:02
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {
    /**
     * 服务对象
     */
    @Resource
    private IUserInfoService userInfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    public UserInfo selectOne(Long id) {
        return this.userInfoService.queryById(id);
    }

    @PostMapping("/listUserByPage")
    public Object queryUserByPage(@RequestBody PageQuery<UserInfo> query) {
        PageBody<UserInfo> body = userInfoService.queryAll(query);
        return Result.success(body);

    }


}