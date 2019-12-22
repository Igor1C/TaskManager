package com.igor1c.database;

import com.igor1c.entities.TaskActionParamEntity;

public class TaskActionParamsTable extends TableController<TaskActionParamEntity> {

    public TaskActionParamsTable() {
        super("taskActionParams");
    }

    public void createTable() {

        String query =  "CREATE TABLE taskActionParams(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   actionTypeParam BIGINT NOT NULL,\n" +
                        "   taskAction BIGINT NOT NULL\n" +
                        ");";

        executeQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE taskActionParams\n" +
                        "   ADD FOREIGN KEY (actionTypeParam)\n" +
                        "   REFERENCES actionTypeParams(id);\n" +
                        "ALTER TABLE taskActionParams\n" +
                        "   ADD FOREIGN KEY (taskAction)\n" +
                        "   REFERENCES taskActions(id);";

        executeQuery(query);

    }

    public void createExtraConstraints() {}

    public void fillTable() {}

    public void insert(TaskActionParamEntity entity) {}

}
