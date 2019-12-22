package com.igor1c.database;

public class ActionTypesTable extends TableController {

    public ActionTypesTable() {
        super("actionTypes");
    }



    public void createTable() {

        String query =  "CREATE TABLE actionTypes(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   name VARCHAR(255) NOT NULL,\n" +
                        "   description VARCHAR(255) NOT NULL\n" +
                        ");";

        executeQuery(query);

    }

    public void createForeignKeys() {}

    public void createExtraConstraints() {}

    public void fillTable() {

        String query =  "INSERT INTO actionTypes VALUES (1, '1C_FILE_UNLOAD_DT', '1C file database - unload DT');\n" +
                        "INSERT INTO actionTypes VALUES (2, '1C_SERVER_UNLOAD_DT', '1C server database - unload DT');\n" +
                        "INSERT INTO actionTypes VALUES (3, '7Z_PACK_ARCHIVE', '7Z - make archive');\n" +
                        "INSERT INTO actionTypes VALUES (4, 'FTP_UPLOAD_FILE', 'FTP - upload file');";

        executeQuery(query);

    }

}
