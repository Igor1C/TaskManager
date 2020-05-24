package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.*;

import java.util.ArrayList;

public class TaskActionParamsTable extends TableController<TaskActionParamEntity> {

    public TaskActionParamsTable() {

        super(  "taskActionParams",
                new String[]{"taskAction", "actionTypeParam", "paramValue", "extraParamTaskAction", "extraParamType"},
                new String[]{"booleanParamValue", "useExtraParam"});

    }

    public void createTable() {

        String query =  "CREATE TABLE taskActionParams(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   taskAction BIGINT NOT NULL,\n" +
                        "   actionTypeParam BIGINT NOT NULL,\n" +
                        "   paramValue VARCHAR(255),\n" +
                        "   booleanParamValue BOOLEAN,\n" +
                        "   useExtraParam BOOLEAN,\n" +
                        "   extraParamTaskAction BIGINT DEFAULT NULL,\n" +
                        "   extraParamType BIGINT DEFAULT NULL\n" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE taskActionParams\n" +
                        "   ADD FOREIGN KEY (taskAction)\n" +
                        "   REFERENCES taskActions(id);\n" +
                        "ALTER TABLE taskActionParams\n" +
                        "   ADD FOREIGN KEY (actionTypeParam)\n" +
                        "   REFERENCES actionTypeParams(id);\n" +
                        "ALTER TABLE taskActionParams\n" +
                        "   ADD FOREIGN KEY (extraParamTaskAction)\n" +
                        "   REFERENCES taskActions(id);\n" +
                        "ALTER TABLE taskActionParams\n" +
                        "   ADD FOREIGN KEY (extraParamType)\n" +
                        "   REFERENCES actionTypeParams(id);";

        executeDbQuery(query);

    }

    public void createExtraConstraints() {}

    public void fillTable() {}



    public BaseEntity fillEntity(BaseEntity baseEntity) {

        TaskActionParamEntity entity = (TaskActionParamEntity) baseEntity;

        ActionTypeParamsTable actionTypeParamsTable = new ActionTypeParamsTable();
        ActionTypeParamEntity actionTypeParam = (ActionTypeParamEntity) actionTypeParamsTable.selectById(entity.getActionTypeParam());
        entity.setActionTypeParamDescription(actionTypeParam.getDescription());
        entity.setBooleanType(actionTypeParam.isBooleanType());

        return baseEntity;

    }

}
