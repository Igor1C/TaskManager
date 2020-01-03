package com.igor1c.taskmanager.controllers.responses;

import com.igor1c.taskmanager.entities.ActionTypeEntity;
import com.igor1c.taskmanager.entities.ActionTypeParamEntity;
import com.igor1c.taskmanager.entities.BaseEntity;

import java.util.List;

public class GetActionTypeInfoResponse extends BaseEntityListResponse {

    BaseEntity actionTypeEntity;



    public BaseEntity getActionTypeEntity() {
        return actionTypeEntity;
    }

    public void setActionTypeEntity(BaseEntity actionTypeEntity) {
        this.actionTypeEntity = actionTypeEntity;
    }

}
