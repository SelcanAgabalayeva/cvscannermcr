package com.selcan.batch_service.config;
import com.selcan.batch_service.CvFileReader;
import com.selcan.batch_service.CvProcessor;
import com.selcan.batch_service.CvWriter;
import com.selcan.batch_service.model.Candidate;
import com.selcan.batch_service.model.CvFile;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    @Bean
    public Job cvJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("cvJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      CvFileReader reader,
                      CvProcessor processor,
                      CvWriter writer) {

        return new StepBuilder("step1", jobRepository)
                .<CvFile, Candidate>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}