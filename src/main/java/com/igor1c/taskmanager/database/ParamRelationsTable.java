package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.ActionTypeEntity;
import com.igor1c.taskmanager.entities.ActionTypeParamEntity;
import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.ParamRelationEntity;

import java.util.ArrayList;

public class ParamRelationsTable extends TableController<ParamRelationEntity> {

    public ParamRelationsTable() {

        super(  "paramRelations",
                new String[]{"actionType", "actionTypeParam"},
                new String[]{"autoGeneration"});

    }



    /* TABLE CREATION */

    public void createTable() {

        String query =  "CREATE TABLE paramRelations(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   actionType BIGINT NOT NULL,\n" +
                        "   actionTypeParam BIGINT NOT NULL,\n" +
                        "   autoGeneration BOOLEAN\n" +
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

        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_FILE_UNLOAD_DT, ActionTypeParamEntity.EXECUTABLE_PATH, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_FILE_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_FILE_PATH, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_FILE_UNLOAD_DT, ActionTypeParamEntity.USER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_FILE_UNLOAD_DT, ActionTypeParamEntity.PASSWORD, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_FILE_UNLOAD_DT, ActionTypeParamEntity.DESTINATION_FOLDER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_FILE_UNLOAD_DT, ActionTypeParamEntity.DESTINATION_FILE, true));

        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.EXECUTABLE_PATH, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_SERVER_SRV, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.ONE_C_DB_SERVER_REF, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.USER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.PASSWORD, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.DESTINATION_FOLDER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ONE_C_SERVER_UNLOAD_DT, ActionTypeParamEntity.DESTINATION_FILE, true));

        insert(new ParamRelationEntity(ActionTypeEntity.ZIP_PACK_ARCHIVE, ActionTypeParamEntity.SOURCE_FOLDER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ZIP_PACK_ARCHIVE, ActionTypeParamEntity.DESTINATION_FOLDER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.ZIP_PACK_ARCHIVE, ActionTypeParamEntity.DESTINATION_FILE, true));

        insert(new ParamRelationEntity(ActionTypeEntity.FTP_UPLOAD_FILE, ActionTypeParamEntity.SOURCE_FOLDER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.FTP_UPLOAD_FILE, ActionTypeParamEntity.SOURCE_FILE, false));
        insert(new ParamRelationEntity(ActionTypeEntity.FTP_UPLOAD_FILE, ActionTypeParamEntity.FTP_ADDRESS, false));
        insert(new ParamRelationEntity(ActionTypeEntity.FTP_UPLOAD_FILE, ActionTypeParamEntity.PORT, false));
        insert(new ParamRelationEntity(ActionTypeEntity.FTP_UPLOAD_FILE, ActionTypeParamEntity.DESTINATION_FOLDER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.FTP_UPLOAD_FILE, ActionTypeParamEntity.USER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.FTP_UPLOAD_FILE, ActionTypeParamEntity.PASSWORD, false));

        insert(new ParamRelationEntity(ActionTypeEntity.WAIT_FOR_FILE, ActionTypeParamEntity.DESTINATION_FOLDER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.WAIT_FOR_FILE, ActionTypeParamEntity.DESTINATION_FILE, false));
        insert(new ParamRelationEntity(ActionTypeEntity.WAIT_FOR_FILE, ActionTypeParamEntity.TIMEOUT, false));

        insert(new ParamRelationEntity(ActionTypeEntity.WAIT_TIME, ActionTypeParamEntity.TIMEOUT, false));

        insert(new ParamRelationEntity(ActionTypeEntity.CREATE_FOLDER, ActionTypeParamEntity.DESTINATION_FOLDER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.CREATE_FOLDER, ActionTypeParamEntity.NEW_FOLDER, false));
        insert(new ParamRelationEntity(ActionTypeEntity.CREATE_FOLDER, ActionTypeParamEntity.FINAL_FOLDER, true));

        insert(new ParamRelationEntity(ActionTypeEntity.DELETE_FOLDER, ActionTypeParamEntity.DESTINATION_FOLDER, false));

    }



    /* CRUD */

    public ArrayList<BaseEntity> selectByActionTypeAndActionTypeParam(long actionType, long actionTypeParam) {

        ArrayList<BaseEntity> paramRelationsArray = select(
                "actionType = " + actionType +
                "   AND actionTypeParam = " + actionTypeParam);

        return paramRelationsArray;

    }



    /* PROCESSING OF ENTITY */

    public BaseEntity fillEntity(BaseEntity baseEntity) {
        return baseEntity;
    }

}
