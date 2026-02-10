package com.juanma.concurrente.enrollmentservice.service;

import com.juanma.concurrente.enrollmentservice.domain.Enrollment;
import com.juanma.concurrente.enrollmentservice.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentStore {

    // capa de servicio simple
    // centraliza acceso al repositorio y reglas basicas

    private final EnrollmentRepository repo;

    public EnrollmentStore(EnrollmentRepository repo) {
        this.repo = repo;
    }

    public Enrollment create(Long courseId, String studentName) {

        // crea la entidad con la fecha actual
        // no hay validacion extra para mantenerlo directo
        Enrollment e = new Enrollment(courseId, studentName, LocalDate.now());
        return repo.save(e);
    }

    public List<Enrollment> findAll() {
        return repo.findAll();
    }

    public List<Enrollment> findByCourseId(Long courseId) {
        return repo.findByCourseId(courseId);
    }
}
