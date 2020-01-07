package com.igor1c.taskmanager.entities;

public class TaskActionEntity extends BaseEntity {

    private String name;
    private long actionType;
    private long userTask;
    private long order;



    public TaskActionEntity() {}

    public TaskActionEntity(String name, long actionType, long userTask, long order) {

        setName(name);
        setActionType(actionType);
        setUserTask(userTask);
        setOrder(order);

    }

    public TaskActionEntity(long id, String name, long actionType, long userTask, long order) {

        this(name, actionType, userTask, order);
        setId(id);

    }



    public BaseEntity createEntity() {
        return new TaskActionEntity();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getActionType() {
        return actionType;
    }

    public String getActionTypeString() {
        return ActionTypeEntity.getActionTypeEntity(getActionType()).getName();
    }

    public void setActionType(long actionType) {
        this.actionType = actionType;
    }

    public long getUserTask() {
        return userTask;
    }

    public void setUserTask(long userTask) {
        this.userTask = userTask;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

}
