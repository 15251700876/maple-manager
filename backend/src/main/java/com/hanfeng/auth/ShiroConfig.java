package com.hanfeng.auth;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author HanFeng
 */
@Configuration
public class ShiroConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;

    @Resource
    RedisSessionDAO redisSessionDAO;
    @Resource
    RedisCacheManager redisCacheManager;
    @Resource
    RedisManager redisManager;

    @PostConstruct
    public void init() {
        redisManager.setHost(host + ":" + port);
        redisManager.setDatabase(2);

    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new AuthSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    @Bean
    public SessionsSecurityManager securityManager(List<Realm> realms, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realms);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }


    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // ????????????SecuritManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        // ?????????????????????????????????
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // ???????????????;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        // ?????????
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // ?????????????????????????????????????????????????????? /**??????????????????
        // authc:??????url????????????????????????????????????; anon:??????url????????????????????????
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        String anon = "anon";
        String authc = "authc";
        Map<String, String> filterChainDefinitionMap = chainDefinition.getFilterChainMap();
        filterChainDefinitionMap.put("/webjars/**", anon);
        filterChainDefinitionMap.put("/login", anon);
        filterChainDefinitionMap.put("/", anon);
        filterChainDefinitionMap.put("/front/**", anon);
        filterChainDefinitionMap.put("/api/**", anon);

        filterChainDefinitionMap.put("/admin/**", authc);
        filterChainDefinitionMap.put("/user/**", authc);
        //????????????????????????????????????????????????????????????????????????????????? url ???????????? ????????????????????????
        filterChainDefinitionMap.put("/**", authc);


        return chainDefinition;
    }
}
