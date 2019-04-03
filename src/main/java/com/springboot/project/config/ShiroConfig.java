package com.springboot.project.config;



import com.springboot.project.realm.UserRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;

import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;


@Configuration
public class ShiroConfig {
    @Bean(name = "sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //l;JSESSIONID=2666f5da-89d7-4186-96aa-c74f1162bd13
        sessionManager.setSessionIdUrlRewritingEnabled(false);//禁止url栏拼接sessionid

        sessionManager.setGlobalSessionTimeout(1000*60*60);//session默认过期时间是半小时
        //定期清除过期会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        return sessionManager;
    }
    @Bean(value = "securityManager")//方法的参数相当于传入spring容器中创建的对象
    public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(userRealm);
        //缓存管理
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        securityManager.setCacheManager(ehCacheManager);
        //cookie
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        Cookie cookie = rememberMeManager.getCookie();
        cookie.setMaxAge(60*60*24*30);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;

    }
    //shiro注解在spring容器中生效
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator= new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);//aop  cglib
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;


    }
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录主页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //设置成功主页面
        shiroFilterFactoryBean.setSuccessUrl("/index.html");
        //没有权限页面
        shiroFilterFactoryBean.setUnauthorizedUrl("unauthorized.html");
        //什么Map能保证存取顺序？
        //linkedHashMap
        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        /**
         *   /static/** = anon
         *                 /login = anon
         *                 /logout = logout
         *                 /admin/** = perms["sys:user:*"]
         *                 #多个角色同时满足
         *                 /users/** = roles["admin"]
         *                 /menu/**  = user
         *                 #其他请求都需要认证后才能访问
         *                 /** = authc
         */
        map.put("/public/**","anon");
        map.put("/json/**","anon");//假数据
        map.put("/captcha.jpg","anon");//验证码
        map.put("/del","anon");
        map.put("/sys/login","anon");
        //map.put("/sys/menu/*","perms[\"sys:menu\"]");
        map.put("/**","user");//选中记住我能访问的资源
       map.put("/**","authc");//登录后才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }
}
