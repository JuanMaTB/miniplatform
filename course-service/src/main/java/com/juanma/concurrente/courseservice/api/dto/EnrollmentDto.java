package com.juanma.concurrente.courseservice.api.dto;

import java.time.LocalDate;

public class EnrollmentDto {

    // dto que representa un enrollment venido de otro microservicio
    // no es entidad local ni se persiste en esta app

    private Long id;
    private Long courseId;
    private String studentName;
    private LocalDate createdAt;

    public EnrollmentDto() {
        // constructor vacio para deserializacion
    }

    public Long getId() {
        return id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
