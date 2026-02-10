package com.juanma.concurrente.enrollmentservice.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ENROLLMENTS")
public class Enrollment {

    // entidad jpa que representa una matricula
    // vive solo en este microservicio

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // referencia al curso, sin relacion jpa
    // asi evitamos acoplar dominios entre micros
    @Column(name = "COURSE_ID", nullable = false)
    private Long courseId;

    @Column(name = "STUDENT_NAME", nullable = false)
    private String studentName;

    // fecha de creacion de la matricula
    // LocalDate es suficiente para el caso
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDate createdAt;

    // constructor vacio obligatorio para jpa
    public Enrollment() {
    }

    // constructor de conveniencia para crear desde el store
    public Enrollment(Long courseId, String studentName, LocalDate createdAt) {
        this.courseId = courseId;
        this.studentName = studentName;
        this.createdAt = createdAt;
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
