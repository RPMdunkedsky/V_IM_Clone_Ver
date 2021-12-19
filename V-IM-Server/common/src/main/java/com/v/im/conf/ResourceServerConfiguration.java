package com.v.im.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * ResourceServerConfigurerAdapter配置适配器使用一个特殊的过滤器来检查请求中的承载令牌，以便通过OAuth2对请求进行认证
 * ResourceServerConfigurerAdapter 先拦截
 */
@Configuration
@EnableResourceServer
@Order(1)
public class ResourceServerConfiguration  extends ResourceServerConfigurerAdapter {


    private static Logger logger = LoggerFactory.getLogger(ResourceServerConfiguration.class);


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        logger.debug("ResourceServerConfiguration");
        http
                .authorizeRequests()
                //配置security访问控制，必须认证过后才可以访问
                .antMatchers("/api/**").authenticated()
                //支持跨域
                .and()
                .cors()
                .and()
                .rememberMe()
                .and()
                //如果你只是创建一个非浏览器客户端使用的服务,你可能会想要禁用CSRF保护
                .csrf()
                .disable();
    }
}
