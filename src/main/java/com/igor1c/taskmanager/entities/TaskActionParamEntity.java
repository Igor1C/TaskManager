package com.igor1c.taskmanager.entities;

public class TaskActionParamEntity extends BaseEntity {

    private String name;
    private long actionTypeParam;
    private long taskAction;



    public TaskActionParamEntity() {}

    public TaskActionParamEntity(String name, long actionTypeParam, long taskAction) {

        setName(name);
        setActionTypeParam(actionTypeParam);
        setTaskAction(taskAction);

    }

    public TaskActionParamEntity(long id, String name, long actionTypeParam, long taskAction) {

        this(name, actionTypeParam, taskAction);
        setId(id);

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getActionTypeParam() {
        return actionTypeParam;
    }

    public void setActionTypeParam(long actionTypeParam) {
        this.actionTypeParam = actionTypeParam;
    }

    public long getTaskAction() {
        return taskAction;
    }

    public void setTaskAction(long taskAction) {
        this.taskAction = taskAction;
    }

}
