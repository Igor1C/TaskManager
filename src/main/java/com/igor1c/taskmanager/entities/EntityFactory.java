package com.igor1c.taskmanager.entities;

public class EntityFactory {

    public static BaseEntity createEntity(String tableName) {

        switch (tableName) {
            case "actionTypes":
                return new ActionTypeEntity();
            case "actionTypeParams":
                return new ActionTypeParamEntity();
            case "paramRelations":
                return new ParamRelationEntity();
            case "taskActionParams":
                return new TaskActionParamEntity();
            case "taskActions":
                return new TaskActionEntity();
            case "userTasks":
                return new UserTaskEntity();
        }

        return null;

    }

}
