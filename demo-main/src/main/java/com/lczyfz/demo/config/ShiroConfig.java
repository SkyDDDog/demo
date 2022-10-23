package com.lczyfz.demo.config;

import com.lczyfz.edp.springboot.sys.shiro.JwtFilter;
import com.lczyfz.edp.springboot.sys.shiro.JwtRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by maple on 2018-09-20.
 */
@Configuration
public class ShiroConfig {

    //    @Bean
//    public CacheManager ehCacheManager() {
//        URL myUrl = getClass().getResource("/cache/myehcache.xml");
//        XmlConfiguration xmlConfig = new XmlConfiguration(myUrl);
//        CacheManager myCacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
//        return myCacheManager;
//    }
    @Bean
    public FilterRegistrationBean corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        // 允许cookies跨域
        config.setAllowCredentials(true);
        // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
        // #允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        // 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        // 设置监听器的优先级
        bean.setOrder(0);

        return bean;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, JwtFilter jwtFilter) {

        Logger logger = LoggerFactory.getLogger(getClass());

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的".jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwtFilter", jwtFilter);
        shiroFilterFactoryBean.setFilters(filters);

//        shiroFilterFactoryBean.setLoginUrl("/login");


        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

//        //游客，开发权限
//        filterChainDefinitionMap.put("/guest/**", "anon");
//        //用户，需要角色权限 “user”
//        filterChainDefinitionMap.put("/user/**", "roles[user]");
//        //管理员，需要角色权限 “admin”
//        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
//        //开放登陆接口
//        filterChainDefinitionMap.put("/login", "anon");


        //开放swagger接口
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        //开放activiti接口
        filterChainDefinitionMap.put("/static/modeler.html", "anon");




        // 根据需要配置需要拦截的url
        filterChainDefinitionMap.put("/api/login", "anon");// 开放登录接口
        filterChainDefinitionMap.put("/api/token/refresh", "anon");// 开放token刷新接口

        filterChainDefinitionMap.put("/api/user/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/system/area/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/system/crontask/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/system/dictionary/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/system/menu/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/system/office/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/system/role/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/system/userrole/**", "jwtFilter");

        filterChainDefinitionMap.put("/api/elastic/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/store/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/storeMonitorManage/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/storeCommodityHostpot/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/storeDataAnalysisController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/storeMonitorLog/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/productMonitorManage/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/storeTalentManageController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/storeKeywordManagerController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/storeKeywordMonitorController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/dataCompareController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/warning/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/category/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/offshelf/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/hotListController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/opinionHotController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/homeDataController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/storeOperationDataController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/storeOperationLiveDataController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/operationAnalysisController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/crowdPortraitController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/fileSynchronizationRecordController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/soarWordAnalysisController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/rankingAnalysisController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/shopUpNewAnalysisController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/shopDrainageAnalysisController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/jstDataController/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/business/shopActivityAnalysisController/**", "jwtFilter");

        filterChainDefinitionMap.put("/api/exam/user/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/exam/subject/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/exam/paper/**", "jwtFilter");
        filterChainDefinitionMap.put("/api/exam/contest/**", "jwtFilter");


        //其余接口一律拦截


        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "anon");



        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        logger.info("执行顺序 : " + filterChainDefinitionMap);

        logger.info("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;


    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
                = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(jwtRealm());

        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
//        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//        securityManager.setSubjectDAO(subjectDAO);


        return securityManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public JwtRealm jwtRealm() {
        return new JwtRealm();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }
}



