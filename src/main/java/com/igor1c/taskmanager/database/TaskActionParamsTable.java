package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.TaskActionParamEntity;

public class TaskActionParamsTable extends TableController<TaskActionParamEntity> {

    public TaskActionParamsTable() {

        super(  "taskActionParams",
                new String[]{"name", "actionTypeParam", "taskAction"});

    }

    public void createTable() {

        String query =  "CREATE TABLE taskActionParams(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   actionTypeParam BIGINT NOT NULL,\n" +
                        "   taskAction BIGINT NOT NULL\n" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE taskActionParams\n" +
                        "   ADD FOREIGN KEY (actionTypeParam)\n" +
                        "   REFERENCES actionTypeParams(id);\n" +
                        "ALTER TABLE taskActionParams\n" +
                        "   ADD FOREIGN KEY (taskAction)\n" +
                        "   REFERENCES taskActions(id);";

        executeDbQuery(query);

    }

    public void createExtraConstraints() {}

    public void fillTable() {}



    public BaseEntity fillEntity(BaseEntity baseEntity) {
        return baseEntity;
    }

}
