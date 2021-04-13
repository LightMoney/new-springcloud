package com.forezp.config;


import com.forezp.filter.JwtAuthenticationTokenFilter;
import com.forezp.handler.AuthenctiationStatusHandler;
import com.forezp.service.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by fangzhipeng on 2017/5/27.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(2)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AuthenctiationStatusHandler authenctiationStatusHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
//                .exceptionHandling()
//                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//            .and()
                .formLogin().loginPage("/index.html").loginProcessingUrl("/login")
                .successHandler(authenctiationStatusHandler)
                .failureHandler(authenctiationStatusHandler)
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(authenctiationStatusHandler)
                .and()
                .authorizeRequests().anyRequest().authenticated()
//                .antMatchers("/test/**").authenticated()
//                .anyRequest().permitAll()
//                .antMatchers("/test/**").hasRole("USER")
//                .antMatchers("/pass/**").permitAll()
            .and()
                .httpBasic();
//        token拦截 可以携带token免登陆（感觉可以不用）
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    UserServiceDetail userServiceDetail;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetail)
                .passwordEncoder(new BCryptPasswordEncoder());
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("jack").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
    }
//@Override
//    public void configure(WebSecurity web) throws Exception {
//    web.ignoring().antMatchers("oauth/**");
//    }
}
