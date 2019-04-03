package com.springboot.project.controller;

import com.springboot.project.entity.SysMenu;
import com.springboot.project.log.MyLog;
import com.springboot.project.service.SysMenuService;
import com.springboot.project.utils.R;
import com.springboot.project.utils.ResultData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;
    @MyLog(value = "查询菜单信息",description = "分页查询并且按照名称查询菜单列表")
   @RequiresPermissions("sys:menu:list")
    @RequestMapping("/sys/menu/list")
    public ResultData menuList(int limit,int offset,String search,String sort,String order){
        return sysMenuService.findByPage(limit,offset,search,sort,order);
    }
    @MyLog(value = "删除菜单",description = "根据菜单编号删除菜单")
@RequiresPermissions("sys:menu:delete")
    @RequestMapping("/sys/menu/del")
    public R del(@RequestBody List<Long> ids){
    return sysMenuService.del(ids);
    }

    /**
     * 查询目录和菜单
     */
    @MyLog(value = "查询菜单目录",description = "查询菜单目录")
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/select")
    public R selectMenu(){
        return sysMenuService.selectMenu();
    }
    @MyLog(value = "新增菜单，目录，按钮",description = "新增菜单，目录，按钮")
   @RequiresPermissions("sys:menu:save")
    @RequestMapping("/sys/menu/save")
    public R saveMenu(@RequestBody SysMenu sysMenu){
        return sysMenuService.saveMenu(sysMenu);
    }
    @MyLog(value = "查询菜单",description = "查询菜单")
   @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/info/{menuId}")
    public R findMenu(@PathVariable long menuId){
        return sysMenuService.fidMenu(menuId);

    }
    @MyLog(value = "修改菜单",description = "根据菜单编号修改菜单")
@RequiresPermissions("sys:menu:update")
    @RequestMapping("/sys/menu/update")
    public R update(@RequestBody SysMenu sysMenu){
        return sysMenuService.update(sysMenu);
    }
    @MyLog(value = "查询用户能访问的菜单",description = "根据菜单编号查询用户能访问的菜单等信息")
    @RequestMapping("sys/menu/user")
    public R menuUser(){
        return sysMenuService.findUserMenu();
    }
}
