package com.isummon.model;

/**
 * 注册的结果类型
 */
public enum RegisterResultType {
    /**
     * 注册成功
     */
    SUCCESS,
    /**
     * 用户昵称错误
     */
    FAIL_ON_USED_NICKNAME,
    /**
     * 用户名称错误
     */
    FAIL_ON_USED_EMAIL,
    /**
     * 网络超时
     */
    FAIL_TIME_OUT;
}
