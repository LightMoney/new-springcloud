//package com.forezp.local;
//
//import org.springframework.util.Assert;
//
///**
// * @Auther: xyd
// * @Date: 2019/6/26 22:14
// * @Description: 请求中持有token 封装成
// */
//public class TokenContextHolder {
//
//    private static final ThreadLocal<String> tokenContextHolder = new ThreadLocal<>();
//
//    public static void clearContext() {
//        tokenContextHolder.remove();
//    }
//
//    public static String getContext() {
//        String token = tokenContextHolder.get();
//        return token;
//    }
//
//    public static void setContext(String token) {
//        Assert.notNull(token, "Only non-null token instances are permitted");
//        tokenContextHolder.set(token);
//    }
//}
