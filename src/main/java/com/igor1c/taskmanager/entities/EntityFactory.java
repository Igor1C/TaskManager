package com.igor1c.taskmanager.entities;

public class EntityFactory {

    public static BaseEntity createEntity(String tableName) {

        switch (tableName) {
            case "actionTypes":
                return new ActionTypeEntity();
            case "actionTypeParams":
                return new ActionTypeParamEntity();
        }

        return null;

    }

}
