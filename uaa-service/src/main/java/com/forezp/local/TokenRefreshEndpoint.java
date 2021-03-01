//package com.forezp.local;
//
//import com.hthl.core.config.authentication.handler.Basic64Decoder;
//import com.hthl.core.response.ResponseEnum;
//import com.hthl.core.response.ResponseResult;
//import com.hthl.core.utils.StringUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
//import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.TokenRequest;
//import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
//import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Collections;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author zhijian
// * @date 2019/03/17   0017
// * @description token刷新端点
// */
//@RestController
//@Slf4j
//public class TokenRefreshEndpoint {
//
//    /**
//     * 注入获取client信息的service 此类由spring 容器管理
//     */
//    @Autowired(required = false)
//    private ClientDetailsService clientDetailsService;
//
//    /**
//     * 注入获取验证token的类
//     */
//    @Autowired(required = false)
//    private AuthorizationServerTokenServices authorizationServerTokenServices;
//
//    /**
//     * 异常解析类
//     */
//    private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();
//
//
//    /**
//     * 刷新token过期
//     */
//    private static final String TOKEN_EXPIRED_PREFIX = "Invalid refresh token (expired): ";
//
//    @RequestMapping("${custom.security.core.token-refresh:/token/refresh}")
//    public Object tokenRefresh(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String refreshToken = request.getParameter("refresh_token");
//        if (StringUtils.hasNotText(refreshToken)) {
//            return new ResponseResult(false, "没有刷新token");
//        }
//        String header = request.getHeader("Authorization");
//        String clietId = null;
//        if (StringUtils.hasText(header) && header.toLowerCase().startsWith("basic ")) {
//            String[] tokens = Basic64Decoder.extractAndDecodeHeader(header);
//            if (tokens != null || tokens.length == 2) {
//                clietId = tokens[0];
//            }
//        }
//        if (clietId == null) {
//            return new ResponseResult(false, "没有client凭证");
//        }
//        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clietId);
//        TokenRequest tokenRequest = new TokenRequest(Collections.EMPTY_MAP, clietId, clientDetails.getScope(), "custom");
//        // 刷新获取的token
//        OAuth2AccessToken accessToken;
//        try {
//            accessToken = authorizationServerTokenServices.refreshAccessToken(refreshToken, tokenRequest);
//        } catch (InvalidTokenException e) {
//            ResponseEntity<OAuth2Exception> result = exceptionTranslator.translate(e);
//            String message = result.getBody().getMessage();
//            // 是否是过期token判断
//            if (message.contains(TOKEN_EXPIRED_PREFIX)) {
//                return new ResponseResult(ResponseEnum.INVALID_REFRESH_TOKEN);
//            } else {
//                return new ResponseResult(false, "刷新token解析异常");
//            }
//        } catch (Exception e) {
//            return new ResponseResult(false, "刷新token异常");
//        }
//        // 创建一个map(返回到前端)
//        Map<String, Object> tokenMap = new ConcurrentHashMap<>(2);
//        tokenMap.put("access_token", accessToken.getValue());
//        tokenMap.put("refresh_token", accessToken.getRefreshToken().getValue());
//        return new ResponseResult(true, tokenMap);
//    }
//
//}
