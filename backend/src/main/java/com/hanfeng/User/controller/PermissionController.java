package com.hanfeng.User.controller;

import com.hanfeng.User.entity.Permission;
import com.hanfeng.User.service.IPermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Permission)表控制层
 *
 * @author makejava
 * @since 2021-05-01 11:33:05
 */
@RestController
@RequestMapping("permission")
public class PermissionController {
    /**
     * 服务对象
     */
    @Resource
    private IPermissionService permissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Permission selectOne(Long id) {
        return this.permissionService.queryById(id);
    }

}