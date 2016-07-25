package com.cooperay.facade.admin.entity;

import com.cooperay.common.entity.BaseEntity;

public class User extends BaseEntity{
	private static final long serialVersionUID = 1L;

    private String userName;

    private String password;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}