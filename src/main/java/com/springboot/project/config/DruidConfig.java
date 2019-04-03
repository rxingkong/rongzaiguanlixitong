package com.springboot.project.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//@Configuration
//public class DruidConfig {
//    @Bean
//    //加载配置文件中以spring.datasoutce来头的配置
//    @ConfigurationProperties(value = "spring.datasource")
//    public DataSource dataSource(){
//        System.out.println("=========");
//        DruidDataSource druidDataSource = new DruidDataSource();
//        return druidDataSource;
//    }
//}
@Configuration
public class DruidConfig {


    @Bean(name = "druidDatasource")
    //加载配置文件中以spring.datasource开头的配置
    @ConfigurationProperties(value = "spring.datasource")
    public DataSource dataSource(){
        System.out.println("-------->init  dataSource！");
        DruidDataSource druidDataSource = new DruidDataSource();

        return druidDataSource;
    }


}