//package com.forezp.local;/**
// * @Auther: xyd
// * @Date: 2019/6/25 17:27
// * @Description:
// */
//
//import com.hthl.framework.utils.StringUtils;
//import com.hthl.service.BearerTokenObtain;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @Author HTHLKJ
// * @Date 2019/6/25 17:27
// * @Description 用于将请求中的用户信息提取放置在线程中
// */
//@Slf4j
//@Component
//@Order(-99)
//public class AuthenticationFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        log.info("正在执行设置当前登录人信息！");
//        String token = BearerTokenObtain.extract(request);
//        if(StringUtils.isNotBlank(token)){
//            TokenContextHolder.setContext(token);
//            log.info("获取到的token：{}",token);
//        }else{
//            TokenContextHolder.clearContext();
//        }
//        filterChain.doFilter(request,response);
//    }
//}
