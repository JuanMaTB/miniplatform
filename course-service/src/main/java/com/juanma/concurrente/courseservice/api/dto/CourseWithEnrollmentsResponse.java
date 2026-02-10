package com.juanma.concurrente.courseservice.api.dto;

import java.time.LocalDate;
import java.util.List;

public class CourseWithEnrollmentsResponse {

    // dto de salida compuesto
    // combina datos locales del curso con datos remotos (enrollments)

    private Long id;
    private String title;
    private String teacher;
    private LocalDate startDate;
    private Integer capacity;
    private List<EnrollmentDto> enrollments;

    public CourseWithEnrollmentsResponse() {
        // constructor vacio para serializacion
    }

    public CourseWithEnrollmentsResponse(
            Long id,
            String title,
            String teacher,
            LocalDate startDate,
            Integer capacity,
            List<EnrollmentDto> enrollments
    ) {
        this.id = id;
        this.title = title;
        this.teacher = teacher;
        this.startDate = startDate;
        this.capacity = capacity;
        this.enrollments = enrollments;
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

    public List<EnrollmentDto> getEnrollments() {
        return enrollments;
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

    public void setEnrollments(List<EnrollmentDto> enrollments) {
        this.enrollments = enrollments;
    }
}
