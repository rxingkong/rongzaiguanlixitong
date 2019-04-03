package com.springboot.project.quartz.task;

import com.springboot.project.entity.SysUser;
import com.springboot.project.service.SysUserService;
import com.springboot.project.utils.Lg;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component(value = "unlockAccount")
public class UnlockAccount {
//    @Resource
//    private SysUserService sysUserService;
//    public void unLock(){
//        Lg.log("解封账号开始");
//        List<SysUser> list=sysUserService.findLockAccount();
//        for (SysUser user : list) {
//            Date date = user.getLockdate();
//            Date now = new Date();
//            long time = now.getTime() - date.getTime();
//            long day = time/(1000*60*60*24);
//            if (day>=3){
//                Lg.log("准备解封账号");
//                SysUser sysUser = new SysUser();
//                sysUser.setUserId(user.getUserId());
//                sysUser.setStatus((byte)1);
//                sysUserService.unLockAccount(sysUser);
//                Lg.log("解封账号成功");
//
//            }else {
//                Lg.log("未到解封时间");
//
//            }
//        }
//    }


    @Resource
    private SysUserService sysUsersService;

    public void unLock(){//status =   1  正常  0 冻结
        // System.out.println("解封账户！");
        Lg.log("解封账户开始");
        List<SysUser> list =  sysUsersService.findLockAccount();
        for (SysUser user : list) {
            Date date = user.getLockdate();
            Date now = new Date();
            long time = now.getTime() - date.getTime();
            System.out.println(now.getTime()+"解封时间");
            System.out.println(date.getTime()+"封锁时间");
            long day = time/(1000*60*60*24);
            if (day>=3){
                //时间到了   解封账户
                Lg.log("准备解封账户！");
                SysUser sysUser = new SysUser();
                sysUser.setUserId(user.getUserId());
                sysUser.setStatus((byte)1);
                sysUsersService.unLockAccount(sysUser);
                Lg.log("解封账户成功");
            }else{
                //时间没到
                Lg.log("-->未到解封时间");
            }

        }
    }
}
