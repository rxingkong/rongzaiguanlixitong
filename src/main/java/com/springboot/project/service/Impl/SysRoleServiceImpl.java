package com.springboot.project.service.Impl;

import com.springboot.project.mapper.SysRoleMapper;
import com.springboot.project.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService{
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<String> findRolesByUserId(long userId) {

        return sysRoleMapper.findRolesByUserId(userId);
    }
}
