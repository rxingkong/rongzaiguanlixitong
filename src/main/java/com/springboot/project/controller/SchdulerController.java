package com.springboot.project.controller;

import com.springboot.project.entity.ScheduleJob;
import com.springboot.project.service.ScheduleService;
import com.springboot.project.utils.Pager;
import com.springboot.project.utils.R;
import com.springboot.project.utils.ResultData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SchdulerController {
    @Resource
    private ScheduleService scheduleService;

    @RequestMapping("/schedule/job/list")
    public ResultData scheduList(Pager pager ,String search){
        return scheduleService.scheduList(pager,search);

    }
    @RequestMapping("/schedule/job/save")
    public R save(@RequestBody ScheduleJob scheduleJob){
        return scheduleService.save(scheduleJob);
    }

    @RequestMapping("/schedule/job/info/{jobId}")
    public R info(@PathVariable long jobId){

        return  scheduleService.info(jobId);

    }
//    @RequestMapping("/schedule/job/update")
//    public R update(@RequestBody ScheduleJob scheduleJob){
//        return scheduleService.update(scheduleJob);
//    }

    @RequestMapping("/schedule/job/update")
    public R update(@RequestBody ScheduleJob scheduleJob){
        return scheduleService.update(scheduleJob);
    }
    @RequestMapping("/schedule/job/del")
    public R delete(@RequestBody List<Long> jobIds){
        return scheduleService.delete(jobIds);

    }
    @RequestMapping("/schedule/job/pause")
    public R pause(@RequestBody List<Long> jobIds){
        return scheduleService.pause(jobIds);

    }
    @RequestMapping("/schedule/job/resume")
    public R resume(@RequestBody List<Long> jobIds){
        return scheduleService.resume(jobIds);

    }

    @RequestMapping("/schedule/job/run")
    public R run(@RequestBody List<Long> jobIds){
        return scheduleService.run(jobIds);

    }

//    @RequestMapping("/schedule/job/pause")
//    public R pause(@RequestBody List<Long> jobIds){
//        return scheduleService.pause(jobIds);
//    }
//    @RequestMapping("/schedule/job/resume")
//    public R resume(@RequestBody List<Long> jobIds){
//        return scheduleService.resume(jobIds);
//    }
//    @RequestMapping("/schedule/job/run")
//    public R run(@RequestBody List<Long> jobIds){
//        return scheduleService.run(jobIds);
//    }

}
