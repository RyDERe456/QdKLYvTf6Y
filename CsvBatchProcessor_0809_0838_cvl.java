// 代码生成时间: 2025-08-09 08:38:55
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class CsvBatchProcessor {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public FlatFileItemReader<Item> reader() {
        FlatFileItemReader<Item> reader = new FlatFileItemReader<>();
        reader.setResource(resourceLoader.getResource("classpath:input.csv"));
        reader.setLineMapper(new DefaultLineMapper<Item>() {
            private BeanWrapperFieldSetMapper<Item> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

            public void initialize() throws Exception {
                fieldSetMapper.setTargetType(Item.class);
            }

            @Override
            protected Map<String, Object> extractValues(org.springframework.batch.item.file.transform.FieldSet fs) {
                return fieldSetMapper.mapFieldSet(fs);
            }
        });
        return reader;
    }

    @Bean
    public FlatFileItemWriter<Item> writer() {
        FlatFileItemWriter<Item> writer = new FlatFileItemWriter<>();
        writer.setResource(resourceLoader.getResource("classpath:output.csv"));
        writer.setLineAggregator(new LineAggregator<Item>() {
            private StringBuilder lineBuilder = new StringBuilder();

            @Override
            public String aggregate(Item item) {
                this.lineBuilder.setLength(0);
                this.lineBuilder.append(item.getField1()).append(",")
                        .append(item.getField2()).append(",").append("...");
                return lineBuilder.toString();
            }
        });
        return writer;
    }

    @Bean
    public Step step1(FlatFileItemReader<Item> reader, FlatFileItemWriter<Item> writer) {
        return stepBuilderFactory.get("step1")
                .<Item, Item>chunk(10)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    public Job job(Job job, Step step1) {
        return jobBuilderFactory.get("csvBatchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build()
                .on(ExitStatus.FAILED.getExitCode())
                .end()
                .on(ExitStatus.COMPLETED.getExitCode())
                .end();
    }

    public void runJob(JobParameters jobParameters) throws Exception {
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            throw new RuntimeException("Job failed");
        }
    }
}

class Item {
    private String field1;
    private String field2;
    // Add other fields
    public String getField1() { return field1; }
    public void setField1(String field1) { this.field1 = field1; }
    public String getField2() { return field2; }
    public void setField2(String field2) { this.field2 = field2; }
    // Add other getters and setters
}
