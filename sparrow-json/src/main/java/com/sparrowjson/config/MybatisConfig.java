package com.sparrowjson.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * TODO
 *
 * @author hanliang
 * @version 1.0
 * @date 2020/6/17 11:29
 */
@Configuration
@MapperScan(basePackages="com.sparrowjson.**.mapper")
@EnableTransactionManagement
public class MybatisConfig {
}
