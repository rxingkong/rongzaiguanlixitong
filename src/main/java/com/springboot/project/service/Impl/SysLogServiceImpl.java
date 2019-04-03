package com.springboot.project.service.Impl;

import com.springboot.project.entity.SysLog;
import com.springboot.project.mapper.SysLogMapper;
import com.springboot.project.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysLogServiceImpl implements SysLogService{
    @Resource
    private SysLogMapper sysLogMapper;

    @Override
    public int savaLog(SysLog sysLog) {
        return sysLogMapper.insert(sysLog);
    }
}
