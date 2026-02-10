package com.juanma.concurrente.courseservice.api.dto;

public class CourseInformeResponse {

    // dto de salida para el endpoint de informe
    // aqui no hay logica, solo datos ya calculados

    private long totalCourses;
    private long availableCourses;
    private String maxCapacityCourse;

    public CourseInformeResponse() {
        // constructor vacio para serializacion json
    }

    public CourseInformeResponse(long totalCourses, long availableCourses, String maxCapacityCourse) {
        this.totalCourses = totalCourses;
        this.availableCourses = availableCourses;
        this.maxCapacityCourse = maxCapacityCourse;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public long getAvailableCourses() {
        return availableCourses;
    }

    public void setAvailableCourses(long availableCourses) {
        this.availableCourses = availableCourses;
    }

    public String getMaxCapacityCourse() {
        return maxCapacityCourse;
    }

    public void setMaxCapacityCourse(String maxCapacityCourse) {
        this.maxCapacityCourse = maxCapacityCourse;
    }
}
