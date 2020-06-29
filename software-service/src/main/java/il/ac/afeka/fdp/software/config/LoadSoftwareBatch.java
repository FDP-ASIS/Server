package il.ac.afeka.fdp.software.config;

import il.ac.afeka.fdp.software.batch.JobCompletionListener;
import il.ac.afeka.fdp.software.batch.loadSoftware.SoftwareProcessor;
import il.ac.afeka.fdp.software.batch.loadSoftware.SoftwareReader;
import il.ac.afeka.fdp.software.batch.loadSoftware.SoftwareWriter;
import il.ac.afeka.fdp.software.data.Software;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableBatchProcessing
public class LoadSoftwareBatch {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public SoftwareReader softwareReader;
    @Autowired
    public SoftwareProcessor softwareProcessor;
    @Autowired
    public SoftwareWriter softwareWriter;

    @Bean
    public Job processJob() {
        return jobBuilderFactory.get("loadSoftwareInfo")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .start(loadSoftwareStep())
                .build();
    }

    @Bean
    public Step loadSoftwareStep() {
        return stepBuilderFactory.get("loadSoftwareFromRepo").
                <Map.Entry<String,String[]>, Software[]>chunk(1)
                .reader(softwareReader)
                .processor(softwareProcessor)
                .writer(softwareWriter)
                .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionListener();
    }
}
