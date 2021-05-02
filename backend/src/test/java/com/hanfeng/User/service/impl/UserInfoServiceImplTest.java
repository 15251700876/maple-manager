package com.hanfeng.User.service.impl;


import com.hanfeng.User.entity.UserInfo;
import com.hanfeng.User.service.IUserInfoService;
import com.hanfeng.auth.ShiroUtil;
import com.hanfeng.cache.RedisUtil;
import com.hanfeng.util.page.PageBody;
import com.hanfeng.util.page.PageQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInfoServiceImplTest {
    @Resource
    IUserInfoService userInfoService;
    @Resource
    RedisUtil redisUtil;


    public void tearDown() throws Exception {
    }

    @Test
    public void testQueryById() {
//        String prefix = "user#";
//        boolean b = redisUtil.hasKey("user#1");
//        if (!b) {
        UserInfo userInfo = userInfoService.queryById(1L);
//            boolean absent = redisUtil.setIfAbsent(prefix + userInfo.getId(), userInfo.toString(), 30, TimeUnit.MINUTES);
//
//        }

        String s = ShiroUtil.md5Password(userInfo.getPassword(), userInfo);



    }

    public void testQueryAllByLimit() {
    }

    @Test
    public void testInsert() {
        Date now = null;
        for (int i = 0; i < 1000; i++) {
            now = new Date();
            UserInfo userInfo = new UserInfo();
            long millis = System.currentTimeMillis();
            userInfo.setAccount("acount" + millis);
            userInfo.setName("小明" + i);
            userInfo.setCreateTime(now);
            userInfo.setUpdateTime(now);
            userInfo.setEmail(millis + "@foxmail.com");

            String s = String.valueOf(millis);
            String substring = s.substring(s.length() - 8);
            userInfo.setPhone(152 + substring);
            userInfo.setPassword(millis + "");
            userInfoService.insert(userInfo);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void testUpdate() {
    }

    public void testDeleteById() {
    }

    @Test
    public void testQueryAll() {
        PageQuery<UserInfo> pageQuery = new PageQuery<>();
        pageQuery.setPageNum(2);
        pageQuery.setPageSize(2);
        PageBody<UserInfo> pageBody = userInfoService.queryAll(pageQuery);
        Assert.notEmpty(pageBody.getItemList(), "");
    }
}