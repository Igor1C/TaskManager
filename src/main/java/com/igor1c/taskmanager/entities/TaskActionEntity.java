package com.igor1c.taskmanager.entities;

public class TaskActionEntity extends BaseEntity {

    private String name;
    private long actionType;
    private long userTask;



    public TaskActionEntity() {}

    public TaskActionEntity(long id, String name, long actionType, long userTask) {

        setId(id);
        setName(name);
        setActionType(actionType);
        setUserTask(userTask);

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

    public void setActionType(long actionType) {
        this.actionType = actionType;
    }

    public long getUserTask() {
        return userTask;
    }

    public void setUserTask(long userTask) {
        this.userTask = userTask;
    }

}
