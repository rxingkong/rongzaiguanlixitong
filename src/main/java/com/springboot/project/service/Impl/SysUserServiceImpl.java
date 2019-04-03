package com.springboot.project.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.project.entity.SysUser;
import com.springboot.project.entity.SysUserExample;
import com.springboot.project.mapper.SysUserMapper;
import com.springboot.project.service.SysUserService;
import com.springboot.project.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(value = "sysUserServiceImpl")
public class SysUserServiceImpl implements SysUserService{
    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.selectByExample(null);
    }

    @Override
    public R login(SysUser sysUser) {

        //方法一
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        //方法2
        criteria.andUsernameEqualTo(sysUser.getUsername());
        List<SysUser> list = sysUserMapper.selectByExample(example);
        if (list==null||list.size()==0){
            return R.error("账号不存在");
        }
        SysUser u = list.get(0);
        if (!u.getPassword().equals(sysUser.getPassword())){
            return R.error("密码错误");
        }
        if (u.getStatus()==0){
            return R.error("账号被冻结");
        }
        return R.ok().put("u",u);
    }

    @Override
    public ResultData findUserByPage(Pager pager, String search, Sorter sorter) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysUserExample example = new SysUserExample();
        if (sorter!=null&& StringUtils.isNotEmpty(sorter.getSort())){
            example.setOrderByClause("user_id"+sorter.getOrder());
        }
        SysUserExample.Criteria criteria = example.createCriteria();
        if (search!=null&&!"".equals(search)){
            criteria.andUsernameLike("%"+search+"%");
        }
        List<SysUser> list = sysUserMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        ResultData resultData = new ResultData(info.getTotal(),info.getList());
         return resultData;
    }

    @Override
    public SysUser findByUname(String name) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(name);
        List<SysUser> list = sysUserMapper.selectByExample(example);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public R addUser(SysUser sysUser) {
        int i = sysUserMapper.insert(sysUser);
        if (i>0){
            return R.ok();
        }
        return R.error("添加失败");
    }

    @Override
    public R findUser(long userId) {
        SysUser user= sysUserMapper.selectByPrimaryKey(userId);
        return R.ok().put("user",user);
    }

    @Override
    public R update(SysUser sysUser) {
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (i>0){
           return R.ok();
        }
        return R.error("操作失败");
    }

    @Override
    public R del(List<Long> ids) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdIn(ids);
        int i = sysUserMapper.deleteByExample(example);
        if (i>0){
            return R.ok();
        }
        return R.error("删除失败");
    }

    @Override
    public List<SysUser> findLockAccount() {
        return sysUserMapper.findLockAccount();
    }

    @Override
    public int unLockAccount(SysUser user) {
        return sysUserMapper.unLockAccount(user);
    }

    @Override
    public R findPieDate() {
        List<Map<String,Object>> list = sysUserMapper.findPieDate();
        List list1 = new ArrayList();
        for (Map<String, Object> map : list) {
            String name=map.get("name")+"";
            list1.add(name);
        }
        return R.ok().put("pieData",list).put("legendData",list1);
    }

    @Override
    public R findBaarData() {
        List<Map<String,Object>> list =sysUserMapper.findBaarData();
        List xAxiData=new ArrayList();
        List series0Data = new ArrayList();
        List series1Data=new ArrayList();
        for (Map<String, Object> map : list) {
            String deptName=map.get("deptName")+"";
            Object boy=map.get("boy");
            Object girl=map.get("girl");
            xAxiData.add(deptName);
            series0Data.add(boy);
            series1Data.add(girl);

        }
        return R.ok().put("xAxiData",xAxiData).put("series0Data",series0Data)
                .put("series1Data",series1Data);
    }

    @Override
    public List<Map<String, Object>> exprotExcel() {
        return sysUserMapper.findUserForExprot();
    }
}
