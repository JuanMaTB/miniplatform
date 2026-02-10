package com.juanma.concurrente.courseservice.api.dto;

import java.time.LocalDate;

public class CourseRequest {

    // dto de entrada para crear o actualizar cursos
    // solo datos, sin validaciones ni logica

    private String title;
    private String teacher;
    private LocalDate startDate;
    private Integer capacity;

    // constructor vacio para deserializacion json
    public CourseRequest() {}

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
