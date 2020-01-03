package com.igor1c.taskmanager.controllers.responses;

import com.igor1c.taskmanager.entities.BaseEntity;

import java.util.List;

public class BaseEntityListResponse {

    List<BaseEntity> baseEntityList;


    public List<BaseEntity> getBaseEntityList() {
        return baseEntityList;
    }

    public void setBaseEntityList(List<BaseEntity> baseEntityList) {
        this.baseEntityList = baseEntityList;
    }

}
