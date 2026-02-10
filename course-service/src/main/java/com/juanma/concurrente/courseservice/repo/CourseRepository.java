package com.juanma.concurrente.courseservice.repo;

import com.juanma.concurrente.courseservice.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // repositorio jpa estandar
    // hereda crud, paginacion y queries basicas
    // no necesito metodos custom de momento
}
