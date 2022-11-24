package com.xiechanglei.code.qingyan.code.vo;

/**
 * 消息对象,常规web开发中，返回的json对象包含以下字段
 *   message:消息提示
 *   code:状态码
 *   data:返回的数据
 *   这里提供一个简单的通用实现
 */
public class CommonMessageObject extends ValueObject {

    public static final String MESSAGE = "message";
    public static final String CODE = "code";
    public static final String DATA = "data";

    public CommonMessageObject message(String message) {
        this.of(MESSAGE, message);
        return this;
    }

    public CommonMessageObject code(Object code) {
        this.of(CODE, code);
        return this;
    }

    public CommonMessageObject data(Object data) {
        this.of(DATA, data);
        return this;
    }

    public static CommonMessageObject build(){
        return new CommonMessageObject();
    }

}
