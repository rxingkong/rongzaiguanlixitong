package com.springboot.project.service;

import com.springboot.project.entity.ScheduleJob;
import com.springboot.project.utils.Pager;
import com.springboot.project.utils.R;
import com.springboot.project.utils.ResultData;

import java.util.List;

public interface ScheduleService {
    ResultData scheduList(Pager pager,String seacher);
    R save(ScheduleJob scheduleJob);
    R info(long jobId);
    public R update(ScheduleJob scheduleJob);
    R delete(List<Long> jobIds);
    //暂停
    public R pause(List<Long> jobIds);
    //恢复
    public R resume(List<Long> jobIds);
    //立即执行
    public R run(List<Long> jobIds);

}
