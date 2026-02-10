package com.juanma.concurrente.enrollmentservice.api.dto;

public class EnrollmentCreateRequest {

    // dto de entrada para crear una matricula
    // solo contiene los datos minimos necesarios

    private Long courseId;
    private String studentName;

    public EnrollmentCreateRequest() {
        // constructor vacio para deserializacion json
    }

    public Long getCourseId() {
        return courseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
