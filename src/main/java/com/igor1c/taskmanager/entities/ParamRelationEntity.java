package com.igor1c.taskmanager.entities;

public class ParamRelationEntity extends BaseEntity {

    private long actionType;
    private long actionTypeParam;



    public ParamRelationEntity() {}

    public ParamRelationEntity(long actionType, long actionTypeParam) {

        setActionType(actionType);
        setActionTypeParam(actionTypeParam);

    }

    public ParamRelationEntity(long id, long actionType, long actionTypeParam) {

        this(actionType, actionTypeParam);
        setId(id);

    }



    public long getActionType() {
        return actionType;
    }

    public void setActionType(long actionType) {
        this.actionType = actionType;
    }

    public long getActionTypeParam() {
        return actionTypeParam;
    }

    public void setActionTypeParam(long actionTypeParam) {
        this.actionTypeParam = actionTypeParam;
    }
}
