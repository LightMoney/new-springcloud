//package com.forezp.local;
//
//import com.hthl.framework.utils.StringUtils;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author zhijian
// * @date 2019/01/02   0002
// * @description 登录信息保存工具（提供给业务系统的工具，由业务系统处理）
// */
//@Slf4j
//public class PrincipalContextHolder {
//
//    /**
//     * 唯一上下文容器
//     */
//    private final static ContextHolder contextHolder = new ContextHolder();
//
//    public static ContextHolder getContextHolder() {
//        return contextHolder;
//    }
//
//    public static final String JWT_SIGNING_KEY = "MUSTKEY";
//
//    /**
//     * 静态内部上下文持有类
//     */
//    static public class ContextHolder {
//
//        public Map<String, Object> getContext() {
//            try {
//                String tokenValue = TokenContextHolder.getContext();
//                if(StringUtils.isEmpty(tokenValue)){
//                    return new HashMap<>(3);
//                }
//                // jwt密签不是使用utf-8解签，需要自己指定
//                Claims claims = Jwts.parser().setSigningKey(JWT_SIGNING_KEY.getBytes("UTF-8")).parseClaimsJws(tokenValue).getBody();
//                // 目前三个
//                Map<String, Object> principal = new HashMap<>(3);
//                principal.put("userId", claims.get("userId"));
//                principal.put("enterpriseId", claims.get("enterpriseId"));
//                principal.put("loginTime", new Date((Long) claims.get("loginTime")));
//                principal.put("adminName",claims.get("adminName"));
//                principal.put("adminExternalId",claims.get("adminExternalId"));
//                log.info("principal:{}",principal);
//                return principal;
//
//            } catch (UnsupportedEncodingException e) {
//                log.warn("获取登录人信息异常", e);
//            }
//            throw new InfoMissException("不能获取到信息~~~");
//        }
//    }
//
//    /**
//     * 缺失session异常
//     */
//    static public class InfoMissException extends RuntimeException {
//        public InfoMissException(String message) {
//            super(message);
//        }
//    }
//}
