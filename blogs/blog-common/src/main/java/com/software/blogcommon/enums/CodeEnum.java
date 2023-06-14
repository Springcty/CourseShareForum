package com.software.blogcommon.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {

    SUCCESS(20000, "操作成功"),

    SYSTEM_ERROR(50000, "系统异常"),

    FAIL(51000, "操作失败"),

    VALID_ERROR(52000, "参数格式不正确"),

    TOKEN_EXPIRED(51001,"token过期，请重新登录"),

    PERMISSION_DENIED(51002, "没有管理员权限"),

    UNAUTHORIZED(51003, "这是未授权的请求"),

    USERNAME_EXIST(52001, "用户名已存在"),

    USERNAME_NOT_EXIST(52002, "用户名不存在"),

    PASSWORD_WRONG(52003, "密码错误");
    /*
    状态码
     */
    private final Integer code;
    /*
    描述
     */
    private final String descipt;

}
