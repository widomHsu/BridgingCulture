package edu.monash.bridgingculture.service;

import edu.monash.bridgingculture.intf.JobService;
import edu.monash.bridgingculture.intf.mapper.JobMapper;
import edu.monash.bridgingculture.service.entity.job.Job;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

import static edu.monash.bridgingculture.service.utils.ThreadPoolUtils.executorService;

@Service
public class JobServiceImpl implements JobService {

    @Resource
    JobMapper jobMapper;

    /**
     * Retrieves job-related data.
     *
     * @return Job containing various job-related statistics.
     * @throws RuntimeException if an error occurs while fetching job data.
     */
    @Override
    public Job getJob() {
        Job job = new Job();

        Future<List<Job.Job1Employment>> submit1 = executorService.submit(() -> jobMapper.selectJob1Employment());
        Future<List<Job.Job2Top20Occupations>> submit2 = executorService.submit(() -> jobMapper.selectJob2Top20Occupations());
        Future<List<Job.Job3Industry>> submit3 = executorService.submit(() -> jobMapper.selectJob3Industry());
        Future<List<Job.Job4Proportion>> submit4 = executorService.submit(() -> jobMapper.selectJob4Proportion());
        Future<List<Job.Job5Occupations>> submit5 = executorService.submit(() -> jobMapper.selectJob5Occupations());
        Future<List<Job.Job6FullPart>> submit6 = executorService.submit(() -> jobMapper.selectJob6FullPart());
        Future<List<Job.Job7Changing>> submit7 = executorService.submit(() -> jobMapper.selectJob7Changing());
        Future<List<Job.Job8SkillLevel>> submit8 = executorService.submit(() -> jobMapper.selectJob8SkillLevel());
        Future<List<Job.Job9NewWorkers>> submit9 = executorService.submit(() -> jobMapper.selectJob9NewWorkers());
        Future<List<Job.Job10WorkersIndustry>> submit10 = executorService.submit(() -> jobMapper.selectJob10WorkersIndustry());

        try {
            job.setJob1_participationRate(submit1.get());
            job.setJob2_topOccupations(submit2.get());
            job.setJob3_newWorkersByIndustry(submit3.get());
            job.setJob4_proportionByIndustry(submit4.get());
            job.setJob5_demandBySkill(submit5.get());
            job.setJob6_fullAndPart(submit6.get());
            job.setJob7_changeByIndustry(submit7.get());
            job.setJob8_newWorkersBySkill(submit8.get());
            job.setJob9_newWorkersByRegion(submit9.get());
            job.setJob10_newWorkersByIndustry(submit10.get());

        } catch (Exception e) {
            throw new RuntimeException("Error fetching job data", e);
        }

        return job;
    }
}
