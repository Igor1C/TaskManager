package com.igor1c.taskmanager;

import com.igor1c.taskmanager.database.UserTaskExecutionsTable;
import com.igor1c.taskmanager.helpers.DBHelper;
import com.igor1c.taskmanager.helpers.PropertiesHelper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;
import java.util.TimeZone;

@SpringBootApplication
public class Application {

    public static void main(String ... args) {

        HashMap<String, Object> props = new HashMap<>();
        processParameters(args, props);

        new SpringApplicationBuilder()
                .sources(Application.class)
                .properties(props)
                .run(args);

        TimeZone.setDefault(TimeZone.getDefault());
        DBHelper.createDatabase();
        DBHelper.openStaticConnection();

        try {
            scheduleCheck();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void processParameters(String[] args, HashMap<String, Object> props) {

        props.put("server.port", PropertiesHelper.getPORT());
        props.put("spring.datasource.url", PropertiesHelper.getDbPath());
        props.put("spring.datasource.username", PropertiesHelper.getDbUser());
        props.put("spring.datasource.password", PropertiesHelper.getDbPassword());

    }

    private static void scheduleCheck() throws InterruptedException {

        int tickInterval = PropertiesHelper.getTickInterval();

        while (true) {
            UserTaskExecutionsTable userTaskExecutionsTable = new UserTaskExecutionsTable();
            userTaskExecutionsTable.executeAllSchedules();

            Thread.sleep(tickInterval * 1000);
        }

    }

}
