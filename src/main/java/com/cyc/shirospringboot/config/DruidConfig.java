package com.cyc.shirospringboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
    //后台监控 死代码以后可以直接复制 web.xml, ServletRegistrationBean
    //因为SpringBoot内置了servlet容器,所以没有web.xml,
    // 替代方法就是ServletRegistrationBean想要啥就注册进去

    @Bean
    public ServletRegistrationBean StatViewServlet(){
        //访问druid就可以查看监控
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        //后台需要有人登陆,账号密码配置
        HashMap<String, String> initParameters = new HashMap<>();
        //增加配置
        initParameters.put("loginUsername","admin");
        initParameters.put("loginPassword","123456");
        //允许谁可以访问
        initParameters.put("allow",""); //参数为空就代表所有人可以访问
        //禁止谁可以访问
        initParameters.put("kuangshen","192.168.11.123");
        bean.setInitParameters(initParameters);//设置初始化参数
        return bean;
    }

    //filter的注册
    public FilterRegistrationBean webStartFilter(){

        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        //这个类是Druid的类，过滤哪些请求不要被统计
        bean.setFilter(new WebStatFilter());

        //可以过滤哪些请求
        HashMap<String, String> initParameters = new HashMap<>();
        //这些不进行统计
        initParameters.put("exclusions","*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);
        //可以过滤
        return bean;
    }


}
