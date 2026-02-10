package com.juanma.concurrente.courseservice.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // truco para h2 en modo tcp:
    // si spring crea el DataSource antes de arrancar el servidor h2, la conexion falla (connection refused)
    // con @DependsOn obligo el orden: primero h2TcpServer, luego el pool hikari

    @Bean
    @DependsOn("h2TcpServer")
    public DataSource dataSource(DataSourceProperties properties) {

        // dejo que spring construya el datasource con las props (url, user, driver, etc)
        // pero fuerzo hikari porque es el pool por defecto y va fino
        HikariDataSource ds = properties.initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        // ajustes sencillos para local:
        // - pool pequeno (no necesito 20 conexiones para una demo)
        // - timeout razonable para no colgarse si algo esta caido
        ds.setMaximumPoolSize(5);
        ds.setConnectionTimeout(10_000);

        return ds;
    }
}
