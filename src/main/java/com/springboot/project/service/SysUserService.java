package com.springboot.project.service;

import com.springboot.project.entity.SysUser;
import com.springboot.project.utils.Pager;
import com.springboot.project.utils.R;
import com.springboot.project.utils.ResultData;
import com.springboot.project.utils.Sorter;

import java.util.List;
import java.util.Map;

public interface SysUserService {
    public List<SysUser> findAll();
    public R login(SysUser sysUser);
    public SysUser findByUname(String name);
    public ResultData findUserByPage(Pager pager, String search, Sorter sorter);
    public R addUser(SysUser sysUser);
    public R findUser(long userId);
    public R update(SysUser sysUser );
    public R del(List<Long> ids);
    List<SysUser> findLockAccount();
    int unLockAccount(SysUser user);
    R findPieDate();
    R findBaarData();
    List<Map<String,Object>> exprotExcel();


}
