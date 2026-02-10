package com.juanma.concurrente.enrollmentservice.repository;

import com.juanma.concurrente.enrollmentservice.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // query derivada por nombre
    // spring data genera automaticamente el sql:
    // select * from enrollments where course_id = ?
    List<Enrollment> findByCourseId(Long courseId);
}
