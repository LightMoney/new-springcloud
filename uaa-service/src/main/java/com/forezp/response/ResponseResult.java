package com.forezp.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zhijian
 * @date 2018/12/09   0009
 * @description 返回到前端的对象
 */
@Data
@AllArgsConstructor
public class ResponseResult {

    private boolean success;

    private int code;

    private String message;

    private Object data;

    /**
     * 发生错误时候调用此构造
     *
     * @param success
     * @param message
     */
    public ResponseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.code = 11111;
    }

    /**
     * 没有发生异常时候的构造
     *
     * @param success
     * @param data
     */
    public ResponseResult(boolean success, Object data) {
        this.success = success;
        this.data = data;
        this.code = 10000;
    }

    /**
     * 回应枚举状态注入
     * @param responseEnum
     */
    public ResponseResult(ResponseEnum responseEnum) {
        this.success = responseEnum.success();
        this.code = responseEnum.code();
        this.message = responseEnum.message();
    }
}
