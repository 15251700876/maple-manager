package com.hanfeng.User.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (UserInfo)实体类
 *
 * @author makejava
 * @since 2021-05-01 11:33:01
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -41889189881844690L;
    
    private Long id;
    
    private String name;
    
    private String phone;
    
    private String account;
    
    private String password;
    
    private String email;
    
    private Date createTime;
    
    private Date updateTime;
    
    private Integer deleteStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

}