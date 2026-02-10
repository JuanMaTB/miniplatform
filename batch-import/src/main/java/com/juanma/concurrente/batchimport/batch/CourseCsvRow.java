package com.juanma.concurrente.batchimport.batch;

import java.time.LocalDate;

public class CourseCsvRow {

    // esta clase no es una entidad ni un dto de api
    // es solo un objeto intermedio para spring batch
    // representa una fila tal cual viene del csv

    private String title;
    private String teacher;
    private LocalDate startDate;
    private Integer capacity;

    // constructor vacio obligatorio
    // spring batch + BeanWrapperFieldSetMapper lo necesitan
    public CourseCsvRow() {}

    // getters usados por el processor
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

    // setters usados por el reader
    // los nombres deben coincidir con los definidos en el tokenizer
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
