package com.juanma.concurrente.courseservice.service;

import com.juanma.concurrente.courseservice.api.dto.CourseInformeResponse;
import com.juanma.concurrente.courseservice.api.dto.EnrollmentDto;
import com.juanma.concurrente.courseservice.domain.Course;
import com.juanma.concurrente.courseservice.repo.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class CourseInformeService {

    // url base del micro de enrollments (local)
    // esto queda centralizado aqui para no regarlo por el codigo
    private static final String ENROLLMENT_SERVICE_BASE_URL = "http://localhost:8082";

    private final CourseRepository courseRepository;

    // RestTemplate es bloqueante (mvc). perfecto aqui porque este modulo no es webflux
    // lo instancio aqui para mantenerlo simple, sin meter config extra
    private final RestTemplate restTemplate;

    public CourseInformeService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.restTemplate = new RestTemplate();
    }

    public CourseInformeResponse generarInforme() {

        // concurrencia clasica:
        // - ExecutorService con pool fijo
        // - Callable para devolver resultados
        // - Future para esperar y combinar
        // aqui tiene sentido porque son calculos independientes y se pueden sacar en paralelo
        ExecutorService executor = Executors.newFixedThreadPool(3);

        try {
            Callable<Long> totalCoursesTask = () -> courseRepository.count();

            Callable<Long> availableCoursesTask = () -> {
                // curso "disponible" = capacity > numero de enrollments
                // el numero de enrollments se pide al otro micro por curso
                List<Course> courses = courseRepository.findAll();

                long count = 0;
                for (Course c : courses) {
                    int enrolled = getEnrollmentsCount(c.getId());
                    Integer capacity = c.getCapacity();
                    int cap = (capacity == null) ? 0 : capacity;

                    if (cap > enrolled) {
                        count++;
                    }
                }
                return count;
            };

            Callable<String> maxCapacityCourseTask = () -> {
                // saco el curso con mayor capacity y devuelvo su title
                // si hay empate no me importa cual, con max() vale
                List<Course> courses = courseRepository.findAll();

                return courses.stream()
                        .max(Comparator.comparingInt(c -> c.getCapacity() == null ? 0 : c.getCapacity()))
                        .map(Course::getTitle)
                        .orElse(null);
            };

            // submit -> Future
            Future<Long> totalFuture = executor.submit(totalCoursesTask);
            Future<Long> availableFuture = executor.submit(availableCoursesTask);
            Future<String> maxCapacityFuture = executor.submit(maxCapacityCourseTask);

            // get() bloquea hasta tener resultado (y propaga excepciones)
            long total = totalFuture.get();
            long available = availableFuture.get();
            String maxTitle = maxCapacityFuture.get();

            return new CourseInformeResponse(total, available, maxTitle);

        } catch (Exception e) {
            // si falla la llamada al otro micro o algo de bd, prefiero cortar y devolver error claro
            throw new RuntimeException("error generando informe de cursos", e);
        } finally {
            // shutdown siempre, para no dejar hilos vivos
            executor.shutdown();
        }
    }

    private int getEnrollmentsCount(Long courseId) {

        // endpoint remoto: devuelve lista de enrollments del curso
        // uso array porque RestTemplate mapea facil a EnrollmentDto[]
        String url = ENROLLMENT_SERVICE_BASE_URL + "/enrollments/course/" + courseId;

        EnrollmentDto[] enrollmentsArray = restTemplate.getForObject(url, EnrollmentDto[].class);

        if (enrollmentsArray == null) {
            return 0;
        }
        return enrollmentsArray.length;
    }
}
