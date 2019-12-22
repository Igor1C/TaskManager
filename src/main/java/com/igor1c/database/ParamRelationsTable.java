package com.igor1c.database;

import com.igor1c.entities.ActionTypeEntity;
import com.igor1c.entities.ActionTypeParamEntity;
import com.igor1c.entities.ParamRelationEntity;

public class ParamRelationsTable extends TableController<ParamRelationEntity> {

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
                        "ALTER TABLE paramRelations\n" +
                        "   ADD FOREIGN KEY (actionTypeParam)\n" +
                        "   REFERENCES actionTypeParams(id);";

        executeQuery(query);

    }

    public void createExtraConstraints() {}

    public void fillTable() {

        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_FILE_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_FILE_PATH));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_FILE_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_USER));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_FILE_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_PASSWORD));

        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_SERVER_SRV));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_SERVER_REF));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_USER));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_PASSWORD));

    }

    public void insert(ParamRelationEntity entity) {

        String query =  "INSERT INTO " + getTableName() + " (actionType, actionTypeParam) VALUES (" + entity.getActionType() + ", " + entity.getActionTypeParam() + ");";
        executeQuery(query);

    }

}
