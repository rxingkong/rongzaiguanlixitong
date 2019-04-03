package com.springboot.project.controller;

import com.springboot.project.service.SysUserService;
import com.springboot.project.utils.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class EchartsController {
    @Resource
    private SysUserService sysUserService;
    @RequestMapping("/sys/echarts/pie")
    public R pie(){
        return sysUserService.findPieDate();
    }
    @RequestMapping("/sys/echarts/bar")
    public R bar(){
        return sysUserService.findBaarData();
    }
}
