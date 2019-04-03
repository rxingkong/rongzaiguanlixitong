package com.springboot.project.realm;

import com.springboot.project.entity.SysUser;
import com.springboot.project.service.SysMenuService;
import com.springboot.project.service.SysRoleService;
import com.springboot.project.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component(value = "userRealm")
public class UserRealm extends AuthorizingRealm{
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysMenuService sysMenuService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权");
        //得到当前用户
        SysUser user =(SysUser) principalCollection.getPrimaryPrincipal();
        //根据当前用户id查询角色名
        List<String> roles = sysRoleService.findRolesByUserId(user.getUserId());

        List<String> perms = sysMenuService.findPermsByUserId(user.getUserId());
        System.out.println("xfasvdvadvsdfvadfvadfvev");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(perms);

        return info;
    }
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("----->授权");
//        //得到当前登录的用户
//        SysUser user = (SysUser) principals.getPrimaryPrincipal();
//        //根据当前用户id查询角色名
//        List<String> roles = sysRoleService.findRolesByUserId(user.getUserId());
//        //再查询权限
//        List<String> perms = sysMenuService.findPermsByUserId(user.getUserId());
//
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//
//        info.addRoles(roles);
//        info.addStringPermissions(perms);
//        System.out.println("----->授权over!");
//        return info;
//    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("认证");
        UsernamePasswordToken u=(UsernamePasswordToken) authenticationToken;
        String username = (u.getUsername());
        String password = new String(u.getPassword());
        //调用service层方法
        SysUser sysUser =sysUserService.findByUname(username);
        if (sysUser==null){
            throw new UnknownAccountException("账号未知");
        }
        if (!sysUser.getPassword().equals(password)){
            throw new IncorrectCredentialsException("密码错误");
        }
        if (sysUser.getStatus()==0){
            throw  new LockedAccountException("账号被冻结");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser,password,this.getName());

        return info;
    }
}
