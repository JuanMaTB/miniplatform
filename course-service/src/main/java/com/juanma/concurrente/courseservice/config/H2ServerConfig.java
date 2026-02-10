package com.juanma.concurrente.courseservice.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2ServerConfig {

    // servidor h2 en modo tcp
    // permite que varias apps (course, batch, etc) usen la misma bd en fichero

    @Bean(name = "h2TcpServer", initMethod = "start", destroyMethod = "stop")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer(
                "-tcp",
                "-tcpAllowOthers",
                "-tcpPort", "9092",
                "-ifNotExists"
        );
    }

    // consola web de h2
    // util solo para inspeccionar datos en local
    @Bean(name = "h2WebServer", initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer(
                "-web",
                "-webAllowOthers",
                "-webPort", "8089"
        );
    }
}
