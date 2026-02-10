package com.juanma.concurrente.courseservice.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "courses")
public class Course {

    // entidad jpa sencilla
    // representa el curso persistido en bd

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String teacher;

    // LocalDate es suficiente, no necesito timestamp aqui
    private LocalDate startDate;

    private Integer capacity;

    // constructor vacio obligatorio para jpa
    public Course() {}

    // constructor de conveniencia para crear desde service o batch
    public Course(String title, String teacher, LocalDate startDate, Integer capacity) {
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
