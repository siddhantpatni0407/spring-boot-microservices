package com.sid.spring.batch.app.config;

import com.sid.spring.batch.app.constant.AppConstants;
import com.sid.spring.batch.app.entity.Customer;
import com.sid.spring.batch.app.exception.ExceptionSkipPolicy;
import com.sid.spring.batch.app.listener.StepSkipListener;
import com.sid.spring.batch.app.repository.CustomerRepository;
import com.sid.spring.batch.app.service.ItemWriterService;
import com.sid.spring.batch.app.service.ItemProcessorService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@AllArgsConstructor
public class BatchProcessingConfig {

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private CustomerRepository customerRepository;

    private ItemWriterService itemWriterService;

    @Bean
    public FlatFileItemReader<Customer> itemReader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(AppConstants.FILE_PATH));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(AppConstants.CSV_DATA_LINE_TO_SKIP);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(AppConstants.DATA_DELIMITER);
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(AppConstants.CSV_HEADER_DATA);

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    @Bean
    public ItemProcessorService itemProcessorService() {
        return new ItemProcessorService();
    }

    @Bean
    public RepositoryItemWriter<Customer> writer() {
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName(AppConstants.REPOSITORY_METHOD_SAVE);
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("slaveStep").<Customer, Customer>chunk(5)
                .reader(itemReader())
                .processor(itemProcessorService())
                .writer(itemWriterService)
                .faultTolerant()
                //.skipLimit(100)
                //.skip(NumberFormatException.class)
                //.noSkip(IllegalArgumentException.class)
                .listener(skipListener())
                .skipPolicy(skipPolicy())
                .build();
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importCustomer")
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public SkipPolicy skipPolicy() {
        return new ExceptionSkipPolicy();
    }

    @Bean
    public SkipListener skipListener() {
        return new StepSkipListener();
    }

}