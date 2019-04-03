package com.springboot.project.config;



import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class QuartzConfig {

    /**
     * org.quartz.scheduler.instanceName=myQuartzScheduler
     * org.quartz.scheduler.instanceid=AUTO
     * # Configure ThreadPool 线程池属性
     * #线程池的实现类（一般使用SimpleThreadPool即可满足几乎所有用户的需求）
     * org.quartz.threadPool.class=org.quartz.simpl.SimpleThreadPool
     * #指定线程数，至少为1（无默认值）(一般设置为1-100直接的整数合适)
     * org.quartz.threadPool.threadCount=10
     * #设置线程的优先级（最大为java.lang.Thread.MAX_PRIORITY 10，最小为Thread.MIN_PRIORITY 1，默认为5）
     * org.quartz.threadPool.threadPriority=5
     * #保存job和Trigger的状态信息到内存中的类
     * org.quartz.jobStore.class=org.quartz.impl.jdbcjobstore.JobStoreTX
     * org.quartz.jobStore.tablePrefix=QRTZ_
     * @return
     */
//    @Bean
//
//    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier(value = "druidDatasource") DataSource dataSource){
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        //quartz参数
//        Properties properties = new Properties();
//        //配置实例
//        properties.setProperty("org.quartz.schedul er.instanceName","MyQuartzScheduler");//实例名称
//        properties.setProperty("org.quartz.scheduler.instanceid","AUTO");
//
//        properties.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
//
//
//        properties.setProperty("org.quartz.threadPool.threadCount","10");
//        properties.setProperty("org.quartz.jobStore.class","org.quartz.impl.jdbcjobstore.JobStoreTX");
//        properties.setProperty(" org.quartz.jobStore.tablePrefix","QRTZ_");
//        schedulerFactoryBean.setQuartzProperties(properties);
//        schedulerFactoryBean.setAutoStartup(true);
//        schedulerFactoryBean.setOverwriteExistingJobs(true);//覆盖一直job
//        schedulerFactoryBean.setStartupDelay(5);
//        schedulerFactoryBean.setDataSource(dataSource);
//        return schedulerFactoryBean;
//
//
//
//
//    }
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier(value = "druidDatasource") DataSource dataSource){

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        Properties p = new Properties();
        p.setProperty("org.quartz.scheduler.instanceName","MyQuartzScheduler");
        p.setProperty("org.quartz.scheduler.instanceid","AUTO");
        p.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
        p.setProperty("org.quartz.threadPool.threadCount","10");
        p.setProperty("org.quartz.jobStore.class","org.quartz.impl.jdbcjobstore.JobStoreTX");
        p.setProperty(" org.quartz.jobStore.tablePrefix","QRTZ_");
        schedulerFactoryBean.setQuartzProperties(p);

        schedulerFactoryBean.setAutoStartup(true);

        schedulerFactoryBean.setOverwriteExistingJobs(true);//覆盖已知job
        schedulerFactoryBean.setStartupDelay(5);//

        schedulerFactoryBean.setDataSource(dataSource);

        return  schedulerFactoryBean;

    }
}
