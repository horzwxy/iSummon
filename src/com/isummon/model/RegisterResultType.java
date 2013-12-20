package com.isummon.model;

/**
 * Created by horzwxy on 12/20/13.
 */
public enum RegisterResultType {
    SUCCESS("注册成功"),
    FAIL_ON_USED_NICKNAME("昵称已存在"),
    FAIL_ON_USED_EMAIL(),
    FAIL_TIME_OUT;
}
