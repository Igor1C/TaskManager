package com.igor1c.taskmanager.entities;

public class EntityFactory {

    public final static String ACTION_TYPES = "actionTypes";
    public final static String ACTION_TYPE_PARAMS = "actionTypeParams";
    public final static String PARAM_RELATIONS = "paramRelations";
    public final static String TASK_ACTION_PARAMS = "taskActionParams";
    public final static String TASK_ACTIONS = "taskActions";
    public final static String USER_TASKS = "userTasks";
    public final static String USER_TASK_EXECUTIONS = "userTaskExecutions";
    public final static String USER_TASK_SCHEDULES = "userTaskSchedules";



    public static BaseEntity createEntity(String tableName) {

        switch (tableName) {
            case ACTION_TYPES:
                return new ActionTypeEntity();
            case ACTION_TYPE_PARAMS:
                return new ActionTypeParamEntity();
            case PARAM_RELATIONS:
                return new ParamRelationEntity();
            case TASK_ACTION_PARAMS:
                return new TaskActionParamEntity();
            case TASK_ACTIONS:
                return new TaskActionEntity();
            case USER_TASKS:
                return new UserTaskEntity();
            case USER_TASK_EXECUTIONS:
                return new UserTaskExecutionEntity();
            case USER_TASK_SCHEDULES:
                return new UserTaskScheduleEntity();
        }

        return null;

    }

}
