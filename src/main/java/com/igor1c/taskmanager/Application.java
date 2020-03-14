package com.igor1c.taskmanager;

import com.igor1c.taskmanager.helpers.DBHelper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;

@SpringBootApplication
public class Application {

    final static String DEFAULT_PORT = "32380";

    public static void main(String ... args) {

        HashMap<String, Object> props = new HashMap<>();
        setPort(args, props);

        new SpringApplicationBuilder()
                .sources(Application.class)
                .properties(props)
                .run(args);

        DBHelper.createDatabase();



        //UserTaskController userTaskController = new UserTaskController(1);
        //userTaskController.processUserTask();

    }

    private static void setPort(String args[], HashMap<String, Object> props) {

        String port;

        if (args.length > 0)
            port = args[0];
        else
            port = DEFAULT_PORT;

        props.put("server.port", port);

    }

}
