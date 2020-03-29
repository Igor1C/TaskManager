package com.igor1c.taskmanager;

import com.igor1c.taskmanager.database.UserTaskExecutionsTable;
import com.igor1c.taskmanager.helpers.DBHelper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;
import java.util.TimeZone;

@SpringBootApplication
public class Application {

    final static String DEFAULT_PORT = "32380";
    static int TICK_SECONDS_INTERVAL = 60;

    public static void main(String ... args) {

        HashMap<String, Object> props = new HashMap<>();
        processParameters(args, props);

        new SpringApplicationBuilder()
                .sources(Application.class)
                .properties(props)
                .run(args);

        TimeZone.setDefault(TimeZone.getDefault());
        DBHelper.createDatabase();

        try {
            scheduleCheck();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void processParameters(String[] args, HashMap<String, Object> props) {

        setPort(args, props);
        setTickInterval(args);

    }

    private static void setPort(String args[], HashMap<String, Object> props) {

        String port;

        if (args.length > 0)
            port = args[0];
        else
            port = DEFAULT_PORT;

        props.put("server.port", port);

    }

    private static void setTickInterval(String args[]) {

        if (args.length > 1)
            TICK_SECONDS_INTERVAL = Integer.parseInt(args[1]);

    }

    private static void scheduleCheck() throws InterruptedException {

        while (true) {
            UserTaskExecutionsTable userTaskExecutionsTable = new UserTaskExecutionsTable();
            userTaskExecutionsTable.executeAllSchedules();

            Thread.sleep(TICK_SECONDS_INTERVAL * 1000);
        }

    }

}
