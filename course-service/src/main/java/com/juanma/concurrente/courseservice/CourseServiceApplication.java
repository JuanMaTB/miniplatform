package com.juanma.concurrente.courseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseServiceApplication {

    // entrypoint del microservicio de cursos
    // levanta mvc, jpa, aop y el servidor h2 tcp
    public static void main(String[] args) {
        SpringApplication.run(CourseServiceApplication.class, args);
    }
}
