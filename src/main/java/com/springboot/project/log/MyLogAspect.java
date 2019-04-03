package com.springboot.project.log;

import com.alibaba.fastjson.JSON;
import com.springboot.project.entity.SysLog;
import com.springboot.project.service.SysLogService;
import com.springboot.project.utils.HttpContextUtils;
import com.springboot.project.utils.IPUtils;
import com.springboot.project.utils.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect//增强
@Component
public class MyLogAspect {
    //注入
    @Resource
    private SysLogService sysLogService;
    @Pointcut(value = "@annotation(com.springboot.project.log.MyLog)")
    public void myPointcut(){}
    //后置增强
    @AfterReturning(pointcut = "myPointcut()")
    public void after(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method mylog= signature.getMethod();
        MyLog myLog=mylog.getAnnotation(MyLog.class);
        String uname= ShiroUtils.getCurrentUser().getUsername();
        String opration= myLog.value();
        String methodName = joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName();
        String params= JSON.toJSONString(joinPoint.getArgs());
        String ip= IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest());
        SysLog sysLog = new SysLog();
        sysLog.setCreateDate(new Date());
        sysLog.setIp(ip);
        sysLog.setMethod(methodName);
        sysLog.setParams(params);
        sysLog.setUsername(uname);
        sysLog.setOperation(opration);
        int i = sysLogService.savaLog(sysLog);
        System.out.println(i>0?"保存日志成功":"保存日志失败");

    }
}
