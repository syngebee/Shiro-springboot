package com.cyc.shirospringboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager dwm){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(dwm);
        //添加Shiro的内置过滤器
        /*
            anon: 无需认证就可以访问
            authc: 必须认证才能访问
            user: 必须拥有 记住我 功能才能使用
            perms: 拥有对某个资源的权限才能访问
            role: 拥有某个角色权限才能访问
        */
        //处理链式是链式的,所以可以用一把LinkedHashMap
        //配置拦截路径
        Map<String, String> filterMap = new LinkedHashMap<>();
        //授权

        //user下的资源必须认证才能使用
        //权限控制，注意顺序
        filterMap.put("/user/add","perms[user:add]");
        filterMap.put("/user/update","perms[user:update]");
        filterMap.put("/logout","logout");
        filterMap.put("/user/*","authc");

        //设置执行链
        bean.setFilterChainDefinitionMap(filterMap);
        //设置登录的请求
        bean.setLoginUrl("/toLogin");
        bean.setUnauthorizedUrl("/noauth");


        return bean;
    }

    //DefaultWebSecurityManager
    @Bean("securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm 管理
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建 realm对象, 需要自定义类
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //整合Shiro 和 thymeLeaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
