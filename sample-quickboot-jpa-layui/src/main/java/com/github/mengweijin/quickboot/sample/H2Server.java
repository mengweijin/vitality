package com.github.mengweijin.quickboot.sample;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;

import java.sql.SQLException;

@Slf4j
public class H2Server {

    private static final String PORT = "9999";
    private static Server server;

    public static void start() throws SQLException {
        server = Server.createTcpServer(new String[]{"-tcp", "-tcpAllowOthers", "-ifNotExists", "-tcpPort", PORT})
                .start();
        log.info(server.getURL());
    }

    public static void stop() {
        if (server != null) {
            server.stop();
        }
    }

    public static void main(String[] args) {
        try {
            H2Server.start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
