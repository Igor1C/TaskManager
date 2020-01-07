package com.igor1c.taskmanager.entities;

public class TaskActionEntity extends BaseEntity {

    private String name;
    private long actionType;
    private long userTask;
    private long taskOrder;



    public TaskActionEntity() {}

    public TaskActionEntity(String name, long actionType, long userTask, long taskOrder) {

        setName(name);
        setActionType(actionType);
        setUserTask(userTask);
        setTaskOrder(taskOrder);

    }

    public TaskActionEntity(long id, String name, long actionType, long userTask, long taskOrder) {

        this(name, actionType, userTask, taskOrder);
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

    public long getTaskOrder() {
        return taskOrder;
    }

    public void setTaskOrder(long taskOrder) {
        this.taskOrder = taskOrder;
    }

}
