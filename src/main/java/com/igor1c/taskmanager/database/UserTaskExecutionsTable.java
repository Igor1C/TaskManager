package com.igor1c.taskmanager.database;

import com.igor1c.taskmanager.entities.BaseEntity;
import com.igor1c.taskmanager.entities.UserTaskEntity;
import com.igor1c.taskmanager.entities.UserTaskExecutionEntity;
import com.igor1c.taskmanager.tasks.UserTaskController;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTaskExecutionsTable extends TableController<UserTaskExecutionEntity> {

    public UserTaskExecutionsTable() {

        super(  "userTaskExecutions",
                new String[]{"userTask", "executionDate"},
                new String[]{"autoExecution", "successfulExecution"});

    }



    /* TABLE CREATION */

    public void createTable() {

        String query =  "CREATE TABLE userTaskExecutions(\n" +
                        "   id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
                        "   userTask BIGINT NOT NULL,\n" +
                        "   executionDate TIMESTAMP\n," +
                        "   autoExecution BOOLEAN,\n" +
                        "   successfulExecution BOOLEAN\n" +
                        ");";

        executeDbQuery(query);

    }

    public void createForeignKeys() {

        String query =  "ALTER TABLE userTaskSchedules\n" +
                        "   ADD FOREIGN KEY (userTask)\n" +
                        "   REFERENCES userTasks(id);";

        executeDbQuery(query);

    }

    public void createExtraConstraints() {}

    public void fillTable() {}



    /* PROCESSING OF ENTITY */

    public BaseEntity fillEntity(BaseEntity baseEntity) {

        return baseEntity;

    }



    /* FUNCTIONAL */

    public void executeAllSchedules() {

        openConnection();

        String query = getScheduleCheckQuery();
        executeQuery(query);

        query = getScheduleCheckFinalQuery();
        ResultSet resultSet = executePreparedStatement(query);

        UserTasksTable userTasksTable = new UserTasksTable();

        try {
            while (resultSet.next()) {
                long userTaskId = resultSet.getLong(resultSet.findColumn("userTask"));
                UserTaskEntity userTaskEntity = (UserTaskEntity) userTasksTable.selectById(userTaskId);
                userTasksTable.fillEntity(userTaskEntity);

                UserTaskController userTaskController = new UserTaskController(userTaskEntity);
                userTaskController.processUserTask();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();

    }

    private static String getScheduleCheckQuery() {

        String query =  "/*\n" +
                        "CHECK DATABASE SCHEDULES AND EXECUTIONS FOR NEXT EXECUTIONS.\n" +
                        "*/\n" +
                        "\n" +
                        "DROP TABLE IF EXISTS currentSchedules;\n" +
                        "DROP TABLE IF EXISTS currentExecutions;\n" +
                        "DROP TABLE IF EXISTS maximumExecutions;\n" +
                        "DROP TABLE IF EXISTS latestExecutions;\n" +
                        "DROP TABLE IF EXISTS plannedExecutions;\n" +
                        "\n" +
                        "CREATE MEMORY TABLE currentSchedules AS\n" +
                        "SELECT\n" +
                        "  userTaskSchedules.id AS userTaskSchedule,\n" +
                        "  userTaskSchedules.userTask,\n" +
                        "  userTaskSchedules.scheduleType,\n" +
                        "  userTaskSchedules.scheduleTime,\n" +
                        "  userTaskSchedules.intervalTime\n" +
                        "FROM\n" +
                        "  userTaskSchedules\n" +
                        "WHERE\n" +
                        "  scheduleType = 1\n" +
                        "    OR scheduleType = 2;\n" +
                        "\n" +
                        "CREATE MEMORY TABLE currentExecutions AS\n" +
                        "SELECT\n" +
                        "  userTaskExecutions.id,\n" +
                        "  userTaskExecutions.userTask,\n" +
                        "  userTaskExecutions.executionDate,\n" +
                        "  userTaskExecutions.autoExecution,\n" +
                        "  userTaskExecutions.successfulExecution\n" +
                        "FROM\n" +
                        "  userTaskExecutions\n" +
                        "    JOIN\n" +
                        "      currentSchedules\n" +
                        "    ON\n" +
                        "      userTaskExecutions.userTask = currentSchedules.userTask;\n" +
                        "\n" +
                        "CREATE MEMORY TABLE maximumExecutions AS\n" +
                        "SELECT\n" +
                        "  currentExecutions.userTask,\n" +
                        "  MAX(currentExecutions.executionDate) AS executionDate\n" +
                        "FROM\n" +
                        "  currentExecutions\n" +
                        "GROUP BY\n" +
                        "  currentExecutions.userTask;\n" +
                        "\n" +
                        "CREATE MEMORY TABLE latestExecutions AS\n" +
                        "SELECT\n" +
                        "  currentExecutions.id,\n" +
                        "  currentExecutions.userTask,\n" +
                        "  currentExecutions.executionDate,\n" +
                        "  currentExecutions.autoExecution,\n" +
                        "  currentExecutions.successfulExecution\n" +
                        "FROM\n" +
                        "  currentExecutions\n" +
                        "    JOIN\n" +
                        "      maximumExecutions\n" +
                        "    ON\n" +
                        "      currentExecutions.userTask = maximumExecutions.userTask\n" +
                        "        AND currentExecutions.executionDate = maximumExecutions.executionDate;\n" +
                        "\n" +
                        "CREATE MEMORY TABLE plannedExecutions AS\n" +
                        "SELECT\n" +
                        "  currentSchedules.userTaskSchedule,\n" +
                        "  currentSchedules.userTask,\n" +
                        "  currentSchedules.scheduleType,\n" +
                        "  currentSchedules.scheduleTime,\n" +
                        "  currentSchedules.intervalTime,\n" +
                        "  latestExecutions.executionDate,\n" +
                        "  CASE\n" +
                        "    WHEN latestExecutions.executionDate IS NULL THEN CURRENT_DATE()\n" +
                        "    WHEN scheduleType = 1 THEN CURRENT_DATE() + currentSchedules.scheduleTime\n" +
                        "    WHEN scheduleType = 2 THEN DATEADD(second, currentSchedules.intervalTime, latestExecutions.executionDate)\n" +
                        "  END AS plannedExecutionDate\n" +
                        "FROM\n" +
                        "  currentSchedules\n" +
                        "    LEFT JOIN\n" +
                        "      latestExecutions\n" +
                        "    ON\n" +
                        "      currentSchedules.userTask = latestExecutions.userTask;";


        return query;

    }

    private static String getScheduleCheckFinalQuery() {

        String query =  "SELECT\n" +
                        "  userTaskSchedule,\n" +
                        "  userTask,\n" +
                        "  scheduleType,\n" +
                        "  scheduleTime,\n" +
                        "  intervalTime,\n" +
                        "  executionDate,\n" +
                        "  plannedExecutionDate,\n" +
                        "  CURRENT_TIMESTAMP() AS currentDate,\n" +
                        "  CASE\n" +
                        "    WHEN CURRENT_TIMESTAMP() > plannedExecutionDate\n" +
                        "      AND (plannedExecutionDate > executionDate\n" +
                        "        OR executionDate IS NULL) THEN TRUE\n" +
                        "    WHEN CURRENT_TIMESTAMP() > plannedExecutionDate\n" +
                        "      AND plannedExecutionDate > executionDate THEN TRUE\n" +
                        "    ELSE FALSE\n" +
                        "  END AS needToExecute\n" +
                        "FROM\n" +
                        "  plannedExecutions\n" +
                        "WHERE\n" +
                        "  CURRENT_TIMESTAMP() > plannedExecutionDate\n" +
                        "    AND (plannedExecutionDate > executionDate\n" +
                        "      OR executionDate IS NULL)";

        return query;

    }


}
