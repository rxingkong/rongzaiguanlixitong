package com.springboot.project.controller;

import com.springboot.project.utils.R;
import com.springboot.project.utils.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LogoutContriller {
    @RequestMapping("/logout")
    @ResponseBody
    public R logout(){
        ShiroUtils.logout();
        return R.ok();
    }
}
