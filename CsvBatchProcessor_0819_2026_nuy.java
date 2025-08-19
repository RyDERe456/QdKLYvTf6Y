// 代码生成时间: 2025-08-19 20:26:31
package com.example.batchprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;import org.springframework.batch.item.file.FlatFileItemReader;import org.springframework.batch.item.file.FlatFileItemWriter;import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;import org.springframework.batch.item.file.transform.LineAggregator;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.core.io.ResourceLoader;import java.util.List;

@Configuration
@EnableBatchProcessing
public class CsvBatchProcessor {

    private static final Logger log = LoggerFactory.getLogger(CsvBatchProcessor.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ResourceLoader resourceLoader;

    // 定义CSV文件的ItemReader
    @Bean
    public FlatFileItemReader<YourCustomObject> reader() {
        FlatFileItemReader<YourCustomObject> reader = new FlatFileItemReader<>();
        reader.setResource(resourceLoader.getResource("classpath:input.csv"));
        reader.setLinesToSkip(1); // 跳过首行（标题行）
        reader.setFieldSetMapper(new BeanWrapperFieldSetMapper<YourCustomObject>() {
            public void mapFieldSets(List<YourCustomObject> fieldSets) {
                fieldSets.add(new YourCustomObject());
            }
        });
        return reader;
    }

    // 定义写入CSV文件的ItemWriter
    @Bean
    public FlatFileItemWriter<YourCustomObject> writer() {
        FlatFileItemWriter<YourCustomObject> writer = new FlatFileItemWriter<>();
        writer.setResource(resourceLoader.getResource("classpath:output.csv"));
        writer.setLineAggregator(new LineAggregator<YourCustomObject>() {
            public String aggregate(YourCustomObject item) {
                return String.format("%s,%s,%s", item.getField1(), item.getField2(), item.getField3()); // 格式化输出
            }
        });
        writer.setFieldExtractor(new BeanWrapperFieldExtractor<>());
        return writer;
    }

    // 定义处理CSV文件的Step
    @Bean
    public Step step1(FlatFileItemReader<YourCustomObject> reader, FlatFileItemWriter<YourCustomObject> writer) {
        return stepBuilderFactory.get("step1")
                .<YourCustomObject, YourCustomObject>chunk(10) // 批处理大小
                .reader(reader)
                .writer(writer)
                .build();
    }

    // 定义Job，包含一个Step
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .start(step1)
                .listener(listener)
                .build();
    }

    // 启动Job
    public void launchJob(JobParameters jobParameters) {
        try {
            JobExecution jobExecution = jobLauncher.run(importUserJob(null), jobParameters);
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("Job has completed successfully.");
            } else {
                log.error("Job failed to complete.");
            }
        } catch (Exception e) {
            log.error("An error occurred while executing the job.", e);
        }
    }
}

// YourCustomObject类，用于映射CSV文件中的字段
class YourCustomObject {
    private String field1;
    private String field2;
    private String field3;
    // getters and setters
}

// JobCompletionNotificationListener类，用于监听Job的完成状态
class JobCompletionNotificationListener implements JobExecutionListener {
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job completed successfully.");
        } else {
            log.error("Job failed to complete.");
        }
    }
}