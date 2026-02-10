package com.juanma.concurrente.courseservice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {

    // aspecto transversal para medir rendimiento
    // se aplica a todos los metodos dentro del paquete service
    // no ensucia la logica de negocio y es facil de activar o quitar

    @Around("execution(* com.juanma.concurrente.courseservice.service..*(..))")
    public Object logTiempoEjecucion(ProceedingJoinPoint pjp) throws Throwable {

        // tiempo inicial en nanos para mayor precision
        long inicio = System.nanoTime();
        try {
            // ejecucion real del metodo interceptado
            return pjp.proceed();
        } finally {
            // el finally garantiza que medimos incluso si hay excepcion
            long fin = System.nanoTime();
            double ms = (fin - inicio) / 1_000_000.0;

            // salida simple por consola
            // suficiente para ver comportamiento y comparar tiempos
            System.out.println(
                    "[AOP] metodo=" + pjp.getSignature().toShortString() +
                            " tiempo=" + String.format("%.3f", ms) + " ms"
            );
        }
    }
}
