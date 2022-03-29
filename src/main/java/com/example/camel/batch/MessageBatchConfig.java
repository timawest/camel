package com.example.camel.batch;

import com.example.camel.DTO.MessagDTO;
import com.example.camel.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ConsumerTemplate;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class MessageBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ConsumerTemplate consumerTemplate;
    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    @Bean
    public CamelTimeoutItemReader<String> camelReader() throws Exception {
        return new CamelTimeoutItemReader<>(consumerTemplate, "direct:camelroute");
    }

    @Bean
    public MessageItemProcessor processor() {
        return new MessageItemProcessor(objectMapper,messageService);
    }

    @Bean
    public Job importUserJob(Step step12) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step12)
                .end()
                .build();
    }
    @Bean
    public Step step12() throws Exception {
        return stepBuilderFactory.get("step12")
                .<String, MessagDTO> chunk(3)
                .reader(camelReader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    private ItemWriter<? super MessagDTO> writer() {
        return new ItemWriter<MessagDTO>() {
            @Override
            public void write(List<? extends MessagDTO> list) throws Exception {
                System.out.println("fdvd");
            }
        };
    }
}
