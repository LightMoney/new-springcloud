package com.forezp.response;

/**
 * @author zhijian
 * @date 2019/03/17   0017
 * @description 返回状态枚举类
 */
public enum ResponseEnum {
    //======================================== enum ~ =================================================================
    INVALID_ACCESS_TOKEN(false, 40001, "失效的访问token"),
    INVALID_REFRESH_TOKEN(false, 40002, "失效的刷新token");

    //======================================== filed ~ =================================================================
    private boolean success;
    private int code;
    private String message;

    ResponseEnum(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
