package com.forezp.config;

import com.forezp.handler.AuthenctiationStatusHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

/**
 * Created by fangzhipeng on 2017/6/1.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    Logger log = LoggerFactory.getLogger(ResourceServerConfig.class);

    @Autowired
    private AuthenctiationStatusHandler authenctiationStatusHandler;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http//.antMatcher("/pass/**")
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .requestMatchers().antMatchers("/pass/**","/test/**").and()
                .csrf().disable()
                .formLogin().loginPage("/index.html").loginProcessingUrl("/login")
                .successHandler(authenctiationStatusHandler)
                .failureHandler(authenctiationStatusHandler)
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(authenctiationStatusHandler);
//                .and()
//                .authorizeRequests()
//                .antMatchers("/user/login","/user/register","/login").permitAll()
//                .antMatchers("/**").authenticated();
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("foo").tokenStore(new JwtTokenStore(jwtTokenEnhancer1()));
    }

//    @Autowired
//    TokenStore tokenStore;
//
//    @Autowired
//    JwtAccessTokenConverter tokenConverter;

//
//    @Autowired
//    JwtAccessTokenConverter jwtAccessTokenConverter;

//    @Bean
//    @Qualifier("tokenStore1")
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(jwtAccessTokenConverter);
//    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer1() {
        JwtAccessTokenConverter converter =  new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("public.cert");
        String publicKey ;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }
}
