package com.springboot.project.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.project.entity.SysMenu;
import com.springboot.project.entity.SysMenuExample;
import com.springboot.project.mapper.SysMenuMapper;
import com.springboot.project.service.SysMenuService;
import com.springboot.project.utils.R;
import com.springboot.project.utils.ResultData;
import com.springboot.project.utils.ShiroUtils;
import com.springboot.project.utils.StringUtils;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SysMenuServiceImpl implements SysMenuService{
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public ResultData findByPage(int limit, int offset) {
        PageHelper.offsetPage(offset,limit);
        List<SysMenu> list = sysMenuMapper.selectByExample(null);
        PageInfo info = new PageInfo(list);
        long total=info.getTotal();
        List<SysMenu> list1=info.getList();
        ResultData resultData = new ResultData(total,list1);
        return resultData;
    }

    @Override
    public ResultData findByPage(int limit, int offset, String search) {
        PageHelper.offsetPage(offset,limit);
        SysMenuExample example = null;
          if (search!=null&&!"".equals(search)) {
             example= new SysMenuExample();
              SysMenuExample.Criteria criteria = example.createCriteria();
              criteria.andNameLike("%"+search+"%");
          }

        List<SysMenu> list = sysMenuMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        long total = info.getTotal();
        List<List> list1=info.getList();
        ResultData resultData = new ResultData(total,list1);
        return resultData;
    }

    @Override
    public ResultData findByPage(int limit, int offset, String search, String sort, String order) {
        PageHelper.offsetPage(offset,limit);
        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria criteria = example.createCriteria();
        if (search!=null&&!"".equals(search)){
            criteria.andNameLike("%"+search+"%");
        }
        if (sort!=null&&sort.equals("menuId")){
            sort="menu_id";
        }
        example.setOrderByClause(sort+" "+order);
        List<SysMenu> list = sysMenuMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        long total = info.getTotal();
        List<SysMenu> list1 = info.getList();
        ResultData resultData = new ResultData(total,list1);
        return resultData;
    }

    @Override
    public R del(List<Long> ids) {
        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria criteria = example.createCriteria();
        for (Long id : ids) {
            if (id<20){
                return R.error("系统菜单,不能删除！请核对");
            }
        }
        criteria.andMenuIdIn(ids);
        int i = sysMenuMapper.deleteByExample(example);
        if (i>0){
            return R.ok();
        }
        return R.error("删除失败");

    }
    /**
     *  //select  * from sys_menu
     where  type!=2;
     *
     */
    @Override
    public R selectMenu() {
        List<SysMenu> list = sysMenuMapper.findMenuNotButton();
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0l);
        sysMenu.setParentId(-1l);
        sysMenu.setName("顶级目录");
        sysMenu.setOrderNum(0);
        list.add(sysMenu);
        return R.ok().put("menuList",list);

    }

    @Override
    public R saveMenu(SysMenu sysMenu) {
        int i = sysMenuMapper.insert(sysMenu);
        if (i>0){
            return R.ok("新增成功");
        }
        return R.error("新增失败");
    }

    @Override
    public R fidMenu(long menuId) {
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId);
        return R.ok().put("menu",sysMenu);
    }

    @Override
    public R update(SysMenu sysMenu) {
        int i = sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
        if (i>0){
            return R.ok();
        }
        return R.error("修改失败");
    }

    @Override
    public List<String> findPermsByUserId(long userId) {
//        List<String> list = sysMenuMapper.findPermsByUserId(userId);
//        Set set = new HashSet<String>();
//        for (String s : list) {
//            String ss[] = s.split(",");
//            for (String s1 : ss) {
//                set.add(s1);
//            }
//        }
//        List<String> newLike = new ArrayList<>();
//        newLike.addAll(set);
//        return newLike;

        //null  或者 "sys:user:list,sys:user:info,sys:user:select"
        List<String> list = sysMenuMapper.findPermsByUserId(userId);
        Set set = new HashSet<String>();
        for (String s : list) {
            if (StringUtils.isNotEmpty(s)){
                //"sys:user:list,sys:user:info,sys:user:select"
                //"sys:user:list"
                String ss[] =  s.split(",");
                for (String s1 : ss) {
                    set.add(s1);
                }
            }
        }

        List<String> newList = new ArrayList<>();
        newList.addAll(set);

        return newList;

    }

    @Override
    public R findUserMenu() {
        long userId= ShiroUtils.getUserId();
        List<SysMenu> dir = sysMenuMapper.findDir(userId);
        for (SysMenu sysMenu : dir) {
            List<SysMenu> menuList = sysMenuMapper.findMenu(sysMenu.getMenuId(),userId);
           sysMenu.setList(menuList);
        }
        List<String> permissions =this.findPermsByUserId(userId);

        return R.ok().put("menuList",dir).put("permissions",permissions);
    }
}
