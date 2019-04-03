package com.springboot.project.service.Impl;

import com.springboot.project.entity.ScheduleJobLog;
import com.springboot.project.mapper.ScheduleJobLogMapper;
import com.springboot.project.service.ScheduleJobLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "scheduleJobLogServiceImpl")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService{
//    @Resource
//    private ScheduleJobLogMapper scheduleJobLogMapper;
//    @Override
//    public void insertLog(ScheduleJobLog scheduleJobLog) {
//        scheduleJobLogMapper.insert(scheduleJobLog);
//    }

    @Resource
    private ScheduleJobLogMapper scheduleJobLogMapper;

    @Override
    public void insertLog(ScheduleJobLog scheduleJobLog) {
        scheduleJobLogMapper.insert(scheduleJobLog);
    }

}
