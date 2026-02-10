package com.juanma.concurrente.courseservice.service;

import com.juanma.concurrente.courseservice.api.dto.CourseRequest;
import com.juanma.concurrente.courseservice.api.dto.CourseWithEnrollmentsResponse;
import com.juanma.concurrente.courseservice.api.dto.EnrollmentDto;
import com.juanma.concurrente.courseservice.domain.Course;
import com.juanma.concurrente.courseservice.repo.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class CourseService {

    // base url del micro de enrollments en local
    private static final String ENROLLMENT_SERVICE_BASE_URL = "http://localhost:8082";

    private final CourseRepository courseRepository;

    // RestTemplate bloqueante, encaja con spring mvc
    // para un proyecto simple prefiero esto a meter webclient + config extra
    private final RestTemplate restTemplate;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.restTemplate = new RestTemplate();
    }

    public List<Course> findAll() {
        // listado simple desde bd
        return courseRepository.findAll();
    }

    public Course create(CourseRequest request) {

        // mapeo manual request -> entidad
        // aqui podria validar nulls o limpiar strings si quiero mas robustez
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setTeacher(request.getTeacher());
        course.setStartDate(request.getStartDate());
        course.setCapacity(request.getCapacity());

        return courseRepository.save(course);
    }

    public Course update(Long id, CourseRequest request) {

        // si no existe, error claro
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));

        existing.setTitle(request.getTitle());
        existing.setTeacher(request.getTeacher());
        existing.setStartDate(request.getStartDate());
        existing.setCapacity(request.getCapacity());

        return courseRepository.save(existing);
    }

    public void delete(Long id) {

        // evito delete silencioso para ids inexistentes
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Course not found: " + id);
        }
        courseRepository.deleteById(id);
    }

    // detalle enriquecido:
    // 1) leo curso local (bd)
    // 2) llamo al otro micro para enrollments del curso
    // 3) devuelvo dto combinado
    public CourseWithEnrollmentsResponse findByIdWithEnrollments(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + id));

        String url = ENROLLMENT_SERVICE_BASE_URL + "/enrollments/course/" + id;

        EnrollmentDto[] enrollmentsArray = restTemplate.getForObject(url, EnrollmentDto[].class);

        // si el micro remoto devuelve null, lo trato como lista vacia
        List<EnrollmentDto> enrollments = (enrollmentsArray == null)
                ? Collections.emptyList()
                : Arrays.asList(enrollmentsArray);

        // construyo la respuesta final sin exponer la entidad completa del otro servicio
        CourseWithEnrollmentsResponse response = new CourseWithEnrollmentsResponse();
        response.setId(course.getId());
        response.setTitle(course.getTitle());
        response.setTeacher(course.getTeacher());
        response.setStartDate(course.getStartDate());
        response.setCapacity(course.getCapacity());
        response.setEnrollments(enrollments);

        return response;
    }
}
