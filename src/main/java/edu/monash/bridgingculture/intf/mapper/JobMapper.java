package edu.monash.bridgingculture.intf.mapper;

import edu.monash.bridgingculture.service.entity.job.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface JobMapper {
    // 1.9.2.3.7

    @Select("SELECT * FROM job_1_employment")
    List<Job.Job1Employment> selectJob1Employment();

    @Select("SELECT * FROM job_2_top20_occupations")
    List<Job.Job2Top20Occupations> selectJob2Top20Occupations();

    @Select("SELECT * FROM job_3_industry")
    List<Job.Job3Industry> selectJob3Industry();

    @Select("SELECT * FROM job_4_proportion")
    List<Job.Job4Proportion> selectJob4Proportion();

    @Select("SELECT * FROM job_5_occupations")
    List<Job.Job5Occupations> selectJob5Occupations();

    @Select("SELECT * FROM job_6_full_part")
    List<Job.Job6FullPart> selectJob6FullPart();

    @Select("SELECT * FROM job_7_changing")
    List<Job.Job7Changing> selectJob7Changing();

    @Select("SELECT * FROM job_8_skill_level")
    List<Job.Job8SkillLevel> selectJob8SkillLevel();

    @Select("SELECT * FROM job_9_new_workers")
    List<Job.Job9NewWorkers> selectJob9NewWorkers();

    @Select("SELECT * FROM job_10_workers_industry")
    List<Job.Job10WorkersIndustry> selectJob10WorkersIndustry();
}
