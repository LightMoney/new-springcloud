package com.forezp.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forezp.response.ResponseResult;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义成功失败处理器   将几种处理器使用一个类实现
 * 对于前后端分离的项目   登录成功后应该返回token 和refresh——token' 所以需要自定义处理器
 */
@Component("myAuthenctiationSuccessHandler")
public class AuthenctiationStatusHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 注入获取client信息的service 此类由spring 容器管理
     */
    @Autowired(required = false)
    private ClientDetailsService clientDetailsService;

    /**
     * 注入获取验证token的类
     */
    @Autowired(required = false)
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        logger.info("登录成功");
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().write(objectMapper.writeValueAsString(authentication));

        // 处理需要token去访问请求的逻辑，求请头必须带着提供clientId和clientSecret的base64
        String header = request.getHeader("Authorization");
        try {
            if (header == null || !header.toLowerCase().startsWith("basic ")) {
                throw new MissingRequestHeaderException("missing clientId and clientSecret encoded header parameter");
            }
            String[] tokens = Basic64Decoder.extractAndDecodeHeader(header);
            // 断言关键字，判断是否满足要求(默认没有开启断言  需要VM加入参数 -ea 或者 -enableassertions 开启)
            assert tokens.length == 2;
            String clientId = tokens[0];
            String clientSecret = tokens[1];
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
            if (clientDetails == null) {
                throw new UnapprovedClientAuthenticationException("client_id \"" + clientId + " \" is not found about information");
            } else if (!clientSecret.trim().equals(clientDetails.getClientSecret().trim())) {
                throw new UnapprovedClientAuthenticationException("client_secret \"" + clientSecret + " \" is valid failed");
            }
            // Map<String, String> requestParameters, String clientId, Collection<String> scope,String grantType
            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
            // 获取oauth2Request
            OAuth2Request storedOAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            //获取
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(storedOAuth2Request, authentication);

            // 获取token(默认是12小时过期，刷新token是30天)（内存中我设置的是1小时过期）
            OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
//            将token放入副本中
//            TokenContextHolder.setContext(accessToken.getValue());
//            pushLoginLog(httpServletRequest, accessToken.getValue());

            // 创建一个map(返回到前端)
            Map<String, Object> tokenMap = new ConcurrentHashMap<>(2);
            tokenMap.put("access_token", accessToken.getValue());
            tokenMap.put("refresh_token", accessToken.getRefreshToken().getValue());

            // 添加cookie
            Cookie cookie = new Cookie("atoken", accessToken.getValue());
            cookie.setPath("/");
            // 禁止js操作，防止XSS攻击
            cookie.setHttpOnly(true);
            cookie.setMaxAge(accessToken.getExpiresIn());
            response.addCookie(cookie);

            // 转成json返回信息
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(new ResponseResult(true, tokenMap)));
            writer.close();
            logger.info("登录成功");
            //加载用户的组织缓存
//            sendHttpRequestLoadOrganize(LoginContextHolder.getCurUserId(), LoginContextHolder.getCurEnterPriseId());
        } catch (IOException e) {
            logger.error("登录异常！", e);
            PrintWriter writer = response.getWriter();
            writer.write(objectMapper.writeValueAsString(new ResponseResult(false, "登录认证异常！")));
            writer.close();
        }
        }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        logger.error("登录失败");
//        if (properties.getBrowser().getResultType().trim().equalsIgnoreCase(ResultType.JSON.toString())) {
//            // 失败了返回错误信息
//            httpServletResponse.setContentType("application/json;charset=utf-8");
//            String message = e.getMessage();
//            if (e instanceof BadCredentialsException) {
//                message = "密码错误！";
//            }
//            httpServletResponse.getWriter().println(objectMapper.writeValueAsString(new ResponseResult(false, message)));
//        } else {
            // 调用默认实现
            AuthenticationFailureHandler authenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
            authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
//        }
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.error("退出成功");
//        推送日志  清除副本中token
//        pushLogoutLog(request, BearerTokenObtain.extract(request));
//        TokenContextHolder.clearContext();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(new ResponseResult(true, "退出成功")));
        writer.close();
    }
}