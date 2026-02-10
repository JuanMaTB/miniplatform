package com.juanma.concurrente.batchimport.batch;

import com.juanma.concurrente.batchimport.domain.CourseEntity;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    // idea general:
    // - reader: lee csv y lo mapea a un dto simple (CourseCsvRow)
    // - processor: limpia/valida y transforma a entidad JPA (CourseEntity)
    // - writer: persiste con JPA
    // - step: chunk oriented (lee/procesa/escribe por lotes)
    // - job: encadena el step y listo

    /* ==========================================================
       1) READER: lee courses.csv
       ========================================================== */
    @Bean
    public FlatFileItemReader<CourseCsvRow> courseReader() {
        return new FlatFileItemReaderBuilder<CourseCsvRow>()
                .name("courseCsvReader") // nombre para trazas y estado del step
                .resource(new ClassPathResource("courses.csv")) // resources/courses.csv
                .linesToSkip(1) // la primera linea suele ser cabecera
                // tokenizer: separa por comas y asigna nombres de columnas
                // con setNames(...) estos nombres deben casar con propiedades del dto
                .lineTokenizer(new DelimitedLineTokenizer() {{
                    setDelimiter(",");
                    setNames("title", "teacher", "startDate", "capacity");
                }})
                // fieldSetMapper: convierte "campos string" a un objeto java
                // BeanWrapperFieldSetMapper tira de setters / propiedades por nombre
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(CourseCsvRow.class);
                }})
                .build();
    }

    /* ==========================================================
       2) PROCESSOR: normaliza y valida
       ========================================================== */
    @Bean
    public ItemProcessor<CourseCsvRow, CourseEntity> courseProcessor() {
        return row -> {
            // normalizacion basica: trims y null-safety
            String title = row.getTitle() == null ? "" : row.getTitle().trim();
            String teacher = row.getTeacher() == null ? "" : row.getTeacher().trim();
            Integer cap = row.getCapacity();

            // si devolvemos null en un ItemProcessor, spring batch "filtra" ese item
            // o sea: no se escribe, pero el job sigue
            if (title.isBlank()) return null;
            if (cap == null || cap <= 0) return null;

            // conversion final al modelo persistente
            // startDate viene ya parseado en CourseCsvRow (si lo tienes como LocalDate)
            return new CourseEntity(title, teacher, row.getStartDate(), cap);
        };
    }

    /* ==========================================================
       3) WRITER: jpa item writer
       ========================================================== */
    @Bean
    public JpaItemWriter<CourseEntity> courseWriter(EntityManagerFactory emf) {
        // writer simple: usa el entityManager para persist/merge segun corresponda
        JpaItemWriter<CourseEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        return writer;
    }

    /* ==========================================================
       4) STEP
       ========================================================== */
    @Bean
    public Step importCoursesStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<CourseCsvRow> reader,
            ItemProcessor<CourseCsvRow, CourseEntity> processor,
            JpaItemWriter<CourseEntity> writer
    ) {
        return new StepBuilder("importCoursesStep", jobRepository)
                // chunk(5): cada 5 items hace commit de la transaccion
                // numero pequeno para que sea facil de entender y de ver en logs
                .<CourseCsvRow, CourseEntity>chunk(5, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    /* ==========================================================
       5) JOB
       ========================================================== */
    @Bean
    public Job importCoursesJob(JobRepository jobRepository, Step importCoursesStep) {
        return new JobBuilder("importCoursesJob", jobRepository)
                .start(importCoursesStep)
                .build();
    }
}
