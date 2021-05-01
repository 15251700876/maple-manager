package com.hanfeng.User.entity;

import java.io.Serializable;

/**
 * (Permission)实体类
 *
 * @author makejava
 * @since 2021-05-01 11:33:03
 */
public class Permission implements Serializable {
    private static final long serialVersionUID = 459056901918507686L;
    
    private Long permissionId;
    
    private String permissionCode;
    
    private String permissionName;
    
    private String memo;
    /**
    * 0
    */
    private Integer deleteStatus;


    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

}