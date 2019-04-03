package com.springboot.project.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
@WebFilter(filterName = "statfilter",urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions",value = "*.js,*.css,*.jpg,*.png,*.gif,*.bmp,/druid/*")
        })
public class DruidWebStatFilter extends WebStatFilter{


}
