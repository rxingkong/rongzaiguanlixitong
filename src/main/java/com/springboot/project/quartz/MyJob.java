package com.springboot.project.quartz;

import com.alibaba.fastjson.JSON;
import com.springboot.project.entity.ScheduleJob;
import com.springboot.project.entity.ScheduleJobLog;
import com.springboot.project.service.ScheduleJobLogService;
import com.springboot.project.utils.Lg;
import com.springboot.project.utils.SpringContextUtils;
import com.springboot.project.utils.StringUtils;
import com.springboot.project.utils.SysConstant;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;
import java.util.Date;

public class MyJob implements Job{
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        //System.out.println("helloworld!!!");
//        //取出jobDetail封装参数
//        ScheduleJobLog log=new ScheduleJobLog();
//        //开始时间
//        long start = System.currentTimeMillis();
//        try {
//
//            String json=(String) jobExecutionContext.getJobDetail().getJobDataMap().get(SysConstant.SCHEDULE_DATA_KEY);
//            ScheduleJob scheduleJob = JSON.parseObject(json,ScheduleJob.class);
//            String beanName=scheduleJob.getBeanName();
//            String methodName=scheduleJob.getMethodName();
//            String params=scheduleJob.getParams();
//            Object object= SpringContextUtils.getBean(beanName);
//            Class clazz=object.getClass();
//            Method method=null;
//            if (StringUtils.isEmpty(params)){
//                method=clazz.getDeclaredMethod(methodName);
//                method.invoke(object);
//            }else {
//                method=clazz.getDeclaredMethod(methodName,String.class);
//                method.invoke(method,params);
//
//            }
//            log.setBeanName(beanName);
//            log.setMethodName(beanName);
//            log.setJobId(scheduleJob.getJobId());
//            System.out.println(scheduleJob.getJobId());
//            log.setCreateTime(new Date());
//            log.setParams(params);
//            log.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.setError(e.getMessage());
//
//        }
//        //结束时间
//        long end = System.currentTimeMillis();
//        //ScheduleJobS
//        ScheduleJobLogService scheduleJobLogService =(ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogServiceImpl");
//        log.setTimes((int) (end-start));
//        scheduleJobLogService.insertLog(log);
//        Lg.log("定时任务日志记录成功！");

        //备份数据库
//        BackUpDBTask task = new BackUpDBTask();
//        task.backUp();


//        UnLockAccount a  = new UnLockAccount();
//        a.unLock();

        //取出jobDetail封装参数
        ScheduleJobLog log = new ScheduleJobLog();
        long start = System.currentTimeMillis();
        try{

            String json  = (String)jobExecutionContext.getJobDetail().getJobDataMap().get(SysConstant.SCHEDULE_DATA_KEY);
            //{"beanName":"testTask","cronExpression":"* * * * * ?","jobId":23,"methodName":"test","remark":"测试"}
            //{"beanName":"backUpDB","cronExpression":"* * * * * ?","jobId":24,"methodName":"backUp"}
            ScheduleJob scheduleJob = JSON.parseObject(json,ScheduleJob.class);
            String beanName = scheduleJob.getBeanName();
            String methodName = scheduleJob.getMethodName();
            String params = scheduleJob.getParams();//参数
            //在spring容器中，如何根据bean的名称得到这个对象
            //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring.xml");
            //ac.getBean(beanName);

            Object  object =  SpringContextUtils.getBean(beanName);

            //已知方法名称，object表示的对象 ---> 调用方法
            Class clazz =  object.getClass();
            Method method = null;
            if (StringUtils.isEmpty(params)){//无参方法
                method = clazz.getDeclaredMethod(methodName);
                method.invoke(object);
            }else{
                //带参数
                method = clazz.getDeclaredMethod(methodName,String.class);
                method.invoke(object,params);
            }

            log.setBeanName(beanName);
            log.setMethodName(beanName);
            log.setJobId(scheduleJob.getJobId());
            log.setCreateTime(new Date());
            log.setParams(params);
            log.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());



        }catch(Exception e){
            e.printStackTrace();
            log.setError(e.getMessage());
        }
        long end = System.currentTimeMillis();
        ScheduleJobLogService scheduleJobLogSerice = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogServiceImpl");
       log.setTimes ((int) (end-start));
       scheduleJobLogSerice.insertLog(log);
        Lg.log("定时任务日志记录成功！");

    }
}
