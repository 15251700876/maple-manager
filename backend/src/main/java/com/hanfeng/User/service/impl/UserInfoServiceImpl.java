package com.hanfeng.User.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hanfeng.User.dao.UserInfoDao;
import com.hanfeng.User.entity.UserInfo;
import com.hanfeng.User.service.IUserInfoService;
import com.hanfeng.util.page.PageBody;
import com.hanfeng.util.page.PageQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserInfo)表服务实现类
 *
 * @author makejava
 * @since 2021-05-01 11:33:02
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserInfo queryById(Long id) {
        return this.userInfoDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<UserInfo> queryAllByLimit(int offset, int limit) {
        return this.userInfoDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserInfo insert(UserInfo userInfo) {
        this.userInfoDao.insert(userInfo);
        return userInfo;
    }

    /**
     * 修改数据
     *
     * @param userInfo 实例对象
     * @return 实例对象
     */
    @Override
    public UserInfo update(UserInfo userInfo) {
        this.userInfoDao.update(userInfo);
        return this.queryById(userInfo.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.userInfoDao.deleteById(id) > 0;
    }


    @Override
    public PageBody<UserInfo> queryAll(PageQuery<UserInfo> query) {
        Page<UserInfo> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        userInfoDao.queryAll(query.getQueryData() == null ? new UserInfo() : query.getQueryData());
        return PageBody.getPageBody(page);
    }

    @Override
    public UserInfo queryUserByEmail(String email) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        UserInfo userInfo = userInfoDao.selectOne(wrapper);
        if (userInfo == null || userInfo.getId() == null) {
            return null;
        }
        return userInfo;
    }

    @Override
    public UserInfo queryByPhoneNum(String phone) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("phone", phone);
        UserInfo userInfo = userInfoDao.selectOne(wrapper);
        if (userInfo == null || userInfo.getId() == null) {
            return null;
        }
        return userInfo;
    }

    @Override
    public UserInfo queryByAccount(String account) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("account", account);
        UserInfo userInfo = userInfoDao.selectOne(wrapper);
        if (userInfo == null || userInfo.getId() == null) {
            return null;
        }
        return userInfo;
    }
}