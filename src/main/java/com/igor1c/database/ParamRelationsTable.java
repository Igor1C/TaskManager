package com.igor1c.database;

public class ParamRelationsTable extends TableController {

    public ParamRelationsTable() {
        super("paramRelations");
    }

    public void createTable() {

        String query =  "CREATE TABLE paramRelations(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   actionType BIGINT NOT NULL,\n" +
                        "   actionTypeParam BIGINT NOT NULL\n" +
                        ");";

        executeQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE paramRelations\n" +
                        "   ADD FOREIGN KEY (actionType)\n" +
                        "   REFERENCES actionTypes(id);\n" +
                        "ALTER TABLE taskActions\n" +
                        "   ADD FOREIGN KEY (actionTypeParam)\n" +
                        "   REFERENCES actionTypeParam(id);";

        executeQuery(query);

    }

    public void createExtraConstraints() {}

    public void fillTable() {}

}
