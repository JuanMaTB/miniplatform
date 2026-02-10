package com.juanma.concurrente.batchimport.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "courses")
public class CourseEntity {

    // entidad jpa usada exclusivamente por el batch
    // apunta a la misma tabla que course-service, pero sin depender de ese modulo

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String teacher;

    // fecha de inicio del curso
    // LocalDate encaja mejor que LocalDateTime para un csv
    private LocalDate startDate;

    private Integer capacity;

    // constructor vacio obligatorio para jpa
    public CourseEntity() {}

    // constructor usado desde el ItemProcessor
    public CourseEntity(String title, String teacher, LocalDate startDate, Integer capacity) {
        this.title = title;
        this.teacher = teacher;
        this.startDate = startDate;
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTeacher() {
        return teacher;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
