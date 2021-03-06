package com.hanfeng.User.service;

import com.hanfeng.User.entity.UserInfo;
import com.hanfeng.util.page.PageBody;
import com.hanfeng.util.page.PageQuery;

import java.util.List;

/**
 * (UserInfo)表服务接口
 *
 * @author makejava
 * @since 2021-05-01 11:33:01
 */
public interface IUserInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserInfo queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<UserInfo> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    UserInfo insert(UserInfo userInfo);

    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    UserInfo update(UserInfo userInfo);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);


    /**
     * 分页查询
     * <p>
     * 根据用户 信息查询用户的列表
     *
     * @param query 查询的信息
     * @return
     */
    public PageBody<UserInfo> queryAll(PageQuery<UserInfo> query);

    /**
     * 根据用户的邮箱查询用户是否存在
     *
     * @param email email
     * @return userInfo
     */
    UserInfo queryUserByEmail(String email);

    /**
     * 根据用户的邮箱查询用户是否存在
     *
     * @param phone phone
     * @return userInfo
     */
    UserInfo queryByPhoneNum(String phone);
    /**
     * 根据用户的邮箱查询用户是否存在
     *
     * @param account account
     * @return userInfo
     */
    UserInfo queryByAccount(String account);
}