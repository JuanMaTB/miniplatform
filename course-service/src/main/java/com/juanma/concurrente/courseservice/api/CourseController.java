package com.juanma.concurrente.courseservice.api;

import com.juanma.concurrente.courseservice.api.dto.CourseInformeResponse;
import com.juanma.concurrente.courseservice.api.dto.CourseRequest;
import com.juanma.concurrente.courseservice.api.dto.CourseWithEnrollmentsResponse;
import com.juanma.concurrente.courseservice.domain.Course;
import com.juanma.concurrente.courseservice.service.CourseInformeService;
import com.juanma.concurrente.courseservice.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    // controller mvc "clasico"
    // aqui solo entra/sale http, la logica de verdad vive en service

    private final CourseService courseService;
    private final CourseInformeService courseInformeService;

    public CourseController(CourseService courseService, CourseInformeService courseInformeService) {
        this.courseService = courseService;
        this.courseInformeService = courseInformeService;
    }

    @GetMapping
    public List<Course> list() {
        // devuelve todos los cursos tal cual (entidad)
        // si mas adelante quiero aislar api de entidad, aqui meteria un dto
        return courseService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course create(@RequestBody CourseRequest req) {
        // request dto -> service -> entidad guardada
        return courseService.create(req);
    }

    @GetMapping("/{id}")
    public CourseWithEnrollmentsResponse getById(@PathVariable Long id) {
        // endpoint "enriquecido": curso + enrollments traidos del otro micro
        return courseService.findByIdWithEnrollments(id);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody CourseRequest req) {
        // update parcial/total segun lo que haga el service
        return courseService.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        // delete sin body, 204
        courseService.delete(id);
    }

    // informe agregado (sale de un servicio separado para no mezclarlo con crud)
    @GetMapping("/informe")
    public CourseInformeResponse informe() {
        return courseInformeService.generarInforme();
    }
}
