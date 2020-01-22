package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.ActionTypeEntity;
import com.igor1c.taskmanager.entities.ActionTypeParamEntity;
import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.ParamRelationEntity;

public class ParamRelationsTable extends TableController<ParamRelationEntity> {

    public ParamRelationsTable() {

        super(  "paramRelations",
                new String[]{"actionType", "actionTypeParam"});

    }

    public void createTable() {

        String query =  "CREATE TABLE paramRelations(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   actionType BIGINT NOT NULL,\n" +
                        "   actionTypeParam BIGINT NOT NULL\n" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE paramRelations\n" +
                        "   ADD FOREIGN KEY (actionType)\n" +
                        "   REFERENCES actionTypes(id);\n" +
                        "ALTER TABLE paramRelations\n" +
                        "   ADD FOREIGN KEY (actionTypeParam)\n" +
                        "   REFERENCES actionTypeParams(id);";

        executeDbQuery(query);

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



    public BaseEntity fillEntity(BaseEntity baseEntity) {
        return baseEntity;
    }

}
