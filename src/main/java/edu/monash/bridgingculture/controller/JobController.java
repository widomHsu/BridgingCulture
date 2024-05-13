package edu.monash.bridgingculture.controller;

import edu.monash.bridgingculture.controller.annotation.Log;
import edu.monash.bridgingculture.controller.utils.JobUtils;
import edu.monash.bridgingculture.service.entity.ResponseDO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

    @Resource
    JobUtils jobUtils;

    /**
     * Retrieves job-related data.
     *
     * @return Response containing job-related statistics.
     */
    @GetMapping("/market")
    @Log
    public ResponseDO getJob(){
        return ResponseDO.success(jobUtils.getJob());
    }
}
