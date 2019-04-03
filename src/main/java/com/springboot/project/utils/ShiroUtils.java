package com.springboot.project.utils;

import com.springboot.project.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class ShiroUtils {
    public static void logout(){
        SecurityUtils.getSubject().logout();
    }
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }
    public static void setAttribute(String k,String v){
      getSession().setAttribute(k,v);

    }
    public static Object getAttribute(String k){
        return getSession().getAttribute(k);
    }
    public static String getCaptcah(){
        return getAttribute("code")+"";
    }
    public static SysUser getCurrentUser(){
        return  (SysUser) SecurityUtils.getSubject().getPrincipal();
    }
    public static long getUserId(){
       return getCurrentUser().getUserId();
    }
}
