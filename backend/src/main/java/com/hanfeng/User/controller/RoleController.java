package com.hanfeng.User.controller;

import com.hanfeng.User.entity.Role;
import com.hanfeng.User.service.IRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Role)表控制层
 *
 * @author makejava
 * @since 2021-05-01 11:33:00
 */
@RestController
@RequestMapping("role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private IRoleService roleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Role selectOne(Long id) {
        return this.roleService.queryById(id);
    }

}