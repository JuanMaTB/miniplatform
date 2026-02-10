package com.juanma.concurrente.batchimport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchImportApplication {

    // entrypoint del modulo batch
    // con esto levanta el contexto de spring y ejecuta el job si esta configurado para arrancar al inicio
    public static void main(String[] args) {
        SpringApplication.run(BatchImportApplication.class, args);
    }
}
