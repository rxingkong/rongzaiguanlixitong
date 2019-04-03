package com.springboot.project.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.project.entity.ScheduleJob;
import com.springboot.project.entity.ScheduleJobExample;
import com.springboot.project.mapper.ScheduleJobMapper;
import com.springboot.project.service.ScheduleService;
import com.springboot.project.utils.*;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Resource
    private ScheduleJobMapper scheduleJobMapper;
    @Resource
    private Scheduler scheduler;


    @Override
    public ResultData scheduList(Pager pager, String seacher) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        ScheduleJobExample example = new ScheduleJobExample();
        if (StringUtils.isNotEmpty(seacher)){

            ScheduleJobExample.Criteria criteria = example.createCriteria();
            criteria.andBeanNameLike("%"+seacher+"%");
        }
        List<ScheduleJob> list = scheduleJobMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        ResultData resultData = new ResultData(info.getTotal(),info.getList());

        return resultData;
    }

    @Override
    public R save(ScheduleJob scheduleJob) {
        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());
        scheduleJob.setCreateTime(new Date());
        //1,保存schedule_job表
        int i = scheduleJobMapper.insert(scheduleJob);
        //2真正定时任务的创建
        ScheduleUtils.createScheduleTask(scheduler,scheduleJob);

        return i>0? R.ok(): R.error();
    }

    @Override
    public R info(long jobId) {
        ScheduleJob scheduleJob=scheduleJobMapper.selectByPrimaryKey(jobId);
        return R.ok().put("scheduleJob",scheduleJob);
    }

   // @Override
//    public R update(ScheduleJob scheduleJob) {
//        //1，修改数据库
//        int i = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
//        //2，修改定时任务
//        ScheduleUtils.updateScheduleTask(scheduler,scheduleJob);
//        return i>0?R.ok(): R.error();
//    }
    @Override
    public R update(ScheduleJob scheduleJob) {
        //1,修改数据库Schedule_job表
        int i = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
        //2,修改真正的定时任务
        ScheduleUtils.updateScheduleTask(scheduler,scheduleJob);
        return i>0?R.ok():R.error();
    }

    @Override
    public R delete(List<Long> jobIds) {
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);
       int i = scheduleJobMapper.deleteByExample(example);
       //删除定时任务失败
        for (Long jobId : jobIds) {
            ScheduleUtils.deleteScheduleTask(scheduler,jobId);
        }


        return i>0? R.ok(): R.error();
    }

    @Override
    public R pause(List<Long> jobIds) {
        ScheduleJobExample example=new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria=example.createCriteria();
        criteria.andJobIdIn(jobIds);
        ScheduleJob scheduleJob=new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.PAUSE.getValue());
        int i =scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
        //2 真正暂停任务
        for (Long jobId : jobIds) {
            ScheduleUtils.pause(scheduler,jobId);

        }
        return i>0?R.ok():R.error();

    }

    @Override
    public R resume(List<Long> jobIds) {
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());
        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
        for (Long jobId : jobIds) {
            ScheduleUtils.resume(scheduler,jobId);
        }
        return i>0?R.ok(): R.error();
    }

    @Override
    public R run(List<Long> jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.runOnce(scheduler,jobId);
        }
        return R.ok();
    }
//@Override
//public R pause(List<Long> jobIds) {
//    //1，修改数据库Schedule_job表的状态 status
//    ScheduleJobExample example = new ScheduleJobExample();
//    ScheduleJobExample.Criteria criteria =  example.createCriteria();
//    criteria.andJobIdIn(jobIds);
//    ScheduleJob scheduleJob = new ScheduleJob();
//    scheduleJob.setStatus(SysConstant.ScheduleStatus.PAUSE.getValue());
//    int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
//
//    //2,真正暂停任务
//    for (Long jobId : jobIds) {
//        ScheduleUtils.pause(scheduler,jobId);
//    }
//
//    return i>0?R.ok():R.error();
//
//}
//
//    @Override
//    public R resume(List<Long> jobIds) {
//        //1,修改表的状态
//        ScheduleJobExample example = new ScheduleJobExample();
//        ScheduleJobExample.Criteria criteria =  example.createCriteria();
//        criteria.andJobIdIn(jobIds);
//        ScheduleJob scheduleJob = new ScheduleJob();
//        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());
//        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
//
//        //2,真正恢复任务
//        for (Long jobId : jobIds) {
//            ScheduleUtils.resume(scheduler,jobId);
//        }
//        return i>0?R.ok():R.error();
//    }
//
//    @Override
//    public R run(List<Long> jobIds) {
////        ScheduleJobExample example = new ScheduleJobExample();
////        ScheduleJobExample.Criteria criteria =  example.createCriteria();
////        criteria.andJobIdIn(jobIds);
////        ScheduleJob scheduleJob = new ScheduleJob();
////        scheduleJob.setStatus(SysConstant.ScheduleStatus.PAUSE.getValue());
////        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
//
//        for (Long jobId : jobIds) {
//            ScheduleUtils.runOnce(scheduler,jobId);
//        }
//        return R.ok();
//    }
}
