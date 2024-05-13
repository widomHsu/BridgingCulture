package edu.monash.bridgingculture.controller.utils;

import edu.monash.bridgingculture.intf.JobService;
import edu.monash.bridgingculture.service.entity.job.Job;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Utility class for job-related operations.
 * Implements ApplicationRunner to fetch job data during application startup.
 */
@Component
@Getter
public class JobUtils implements ApplicationRunner {

    @Resource
    JobService jobService;
    Job job;

    /**
     * Fetches job-related data from JobService during application startup.
     *
     * @param args Application arguments.
     */
    @Override
    public void run(ApplicationArguments args) {
        job = jobService.getJob();
    }
}
