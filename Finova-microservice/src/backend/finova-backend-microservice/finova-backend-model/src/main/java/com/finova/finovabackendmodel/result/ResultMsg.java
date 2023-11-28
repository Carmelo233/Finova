package com.finova.finovabackendmodel.result;

public enum ResultMsg {
    LOGIN_SUCCESS("登录成功"),
    REGISTER_SUCCESS("注册成功"),
    ANA_SUCCESS("分析成功"),
    GET_SUCCESS("获取成功"),
    UPLOAD_SUCCESS("上传成功"),

    LOGIN_ERROR("登录失败"),
    REGISTER_ERROR("注册失败"),
    ANA_ERROR("分析失败"),
    GET_ERROR("获取失败"),
    UPLOAD_ERROR("上传失败"),

    WRONG("选择有误"),
    NOT_EXIST("请求资源不存在"),
    LOSS("参数/数据丢失"),
    ;
    private final String msg;

    ResultMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
