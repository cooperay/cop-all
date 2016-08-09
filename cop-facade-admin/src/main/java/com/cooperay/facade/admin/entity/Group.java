package com.cooperay.facade.admin.entity;

import com.cooperay.common.entity.BaseEntity;

public class Group extends BaseEntity {

    private String groupName;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }
}