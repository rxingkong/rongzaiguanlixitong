package com.springboot.project.service;

import com.springboot.project.entity.SysMenu;
import com.springboot.project.utils.R;
import com.springboot.project.utils.ResultData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuService {
    ResultData findByPage(int limit, int offset);
    ResultData findByPage(int limit, int offset,String search);
    ResultData findByPage(int limit, int offset,String search,String sort,String order);
    R del(List<Long> ids);
    R selectMenu();
    R saveMenu(SysMenu sysMenu);
    R fidMenu(long menuId);
    R update(SysMenu sysMenu);

    public List<String>  findPermsByUserId(long userId);
    R findUserMenu();
}
