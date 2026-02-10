package com.juanma.concurrente.enrollmentservice.api;

import com.juanma.concurrente.enrollmentservice.api.dto.EnrollmentCreateRequest;
import com.juanma.concurrente.enrollmentservice.domain.Enrollment;
import com.juanma.concurrente.enrollmentservice.service.EnrollmentStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    // controlador reactivo (webflux)
    // internamente el store es bloqueante, asi que aislamos llamadas en boundedElastic

    private final EnrollmentStore store;

    public EnrollmentController(EnrollmentStore store) {
        this.store = store;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Enrollment> create(@RequestBody EnrollmentCreateRequest req) {

        // fromCallable permite integrar codigo bloqueante en un flujo reactivo
        // boundedElastic esta pensado justo para este tipo de casos
        return Mono.fromCallable(() -> store.create(req.getCourseId(), req.getStudentName()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping
    public Flux<Enrollment> listAll() {

        // obtengo la lista completa desde el store y la convierto a flux
        return Mono.fromCallable(store::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable);
    }

    @GetMapping("/course/{courseId}")
    public Flux<Enrollment> listByCourse(@PathVariable Long courseId) {

        // endpoint usado por course-service para enriquecer respuestas
        return Mono.fromCallable(() -> store.findByCourseId(courseId))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable);
    }
}
