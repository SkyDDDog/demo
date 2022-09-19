package com.lyd.demo.config;

import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;

/**
 * Created by maple on 2018-09-27.
 */
//@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {

    //注入数据源和事务管理器
//    @Bean
//    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
//            @Qualifier("dataSource") DataSource dataSource,
//            @Qualifier("transactionManager") PlatformTransactionManager transactionManager,
//            SpringAsyncExecutor springAsyncExecutor) throws IOException {
//        return this.baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
//    }


}
