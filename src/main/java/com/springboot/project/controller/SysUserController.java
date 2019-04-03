package com.springboot.project.controller;

import com.google.code.kaptcha.Producer;
import com.springboot.project.dto.SysUserDto;
import com.springboot.project.entity.SysUser;
import com.springboot.project.log.MyLog;
import com.springboot.project.service.SysUserService;
import com.springboot.project.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
public class SysUserController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private Producer producer;

    @RequestMapping("/findAll")
    public List<SysUser> dindAll(){
        return sysUserService.findAll();

    }
    @RequestMapping("/sys/login")
    public R login(@RequestBody SysUserDto sysUser){
        //服务端生成的验证码
        String code=ShiroUtils.getCaptcah();
        //用户输入的验证码
        String c = sysUser.getCaptcha();
        if (code!=null&&!code.equalsIgnoreCase(c)){
            return R.error("验证码错误");
        }
        //传统登录
       // return  sysUserService.login(sysUser);
        String s=null;
        try {
            //得到subject
            Subject subject = SecurityUtils.getSubject();
            String pwd = sysUser.getPassword();
            Md5Hash md5Hash = new Md5Hash(pwd,sysUser.getUsername(),1024);
            pwd=md5Hash.toString();
             //2 把用户名和密码封装成UsernamePasswordTolen对象
            UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(),pwd);

            if (sysUser.isRememberMe()){
                token.setRememberMe(true);
                System.out.println("eerfgwesrgsdgvsdfgsdg");
            }

            //3登录
            subject.login(token);

            return R.ok();
        } catch (AuthenticationException e) {
            e.printStackTrace();
           s=e.getMessage();
        }
        return R.error(s);

    }
    @MyLog(value = "用户列表",description = "分页显示用户")
    @RequiresPermissions("sys:user:list")
    @RequestMapping("/sys/user/list")
    public ResultData findUserByPage(Pager pager, String search, Sorter sorter){
        return sysUserService.findUserByPage(pager,search,sorter);
    }
    @RequestMapping("/captcha.jpg")
    public void captcah(HttpServletResponse response){
        try {
            String text=producer.createText();//生成的验证码
            ShiroUtils.setAttribute("code",text);
            BufferedImage bufferedImage = producer.createImage(text);
            OutputStream os = response.getOutputStream();
            //把生成的验证码展示到客户端
            ImageIO.write(bufferedImage,"jpg",os);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @MyLog(value = "查询用户信息",description = "显示用户信息")
   // @RequiresPermissions("sys:user:select")
    @RequestMapping("/sys/user/info")
    public R userInfo(){
        // System.out.println("--->shiro中取出用户信息："+SecurityUtils.getSubject().getPrincipal());
        SysUser user = ShiroUtils.getCurrentUser();
        return  R.ok().put("user",user);

    }

    @RequestMapping("/sys/user/save")
    public R addUser(@RequestBody SysUser sysUser){
        return sysUserService.addUser(sysUser);
    }
    @RequestMapping("/sys/user/info/{userId}")
    public R findUser(@PathVariable long userId){
        return sysUserService.findUser(userId);
    }
    @RequestMapping("/sys/user/update")
    public R update(@RequestBody SysUser sysUser){
        return sysUserService.update(sysUser);

    }

    @RequestMapping("/sys/user/del")
    public R del(@RequestBody List<Long> ids){
        return sysUserService.del(ids);
    }

}
