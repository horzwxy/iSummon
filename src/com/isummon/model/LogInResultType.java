package com.isummon.model;

/**
 * 登录的返回值种类
 */
public enum LogInResultType {
    /**
     * 登录成功
     */
    SUCCESS,
    /**
     * 用户名或密码错误
     */
    FAIL_NOT_MATCH,
    /**
     * 网络超时
     */
    FAIL_TIMEOUT;
}
