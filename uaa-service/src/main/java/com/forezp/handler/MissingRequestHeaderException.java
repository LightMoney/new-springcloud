package com.forezp.handler;

import org.springframework.security.core.AuthenticationException;

/**
 * @author lgh
 * @date 2018/12/30 0030
 * @description 头参数确实异常
 */
public class MissingRequestHeaderException extends AuthenticationException {
    public MissingRequestHeaderException(String msg, Throwable t) {
        super(msg, t);
    }

    public MissingRequestHeaderException(String msg) {
        super(msg);
    }
}
