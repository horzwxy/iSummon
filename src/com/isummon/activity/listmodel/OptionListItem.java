package com.isummon.activity.listmodel;

/**
 * Created by horzwxy on 12/19/13.
 */
public enum OptionListItem {

    MANAGE_CONTACT("我的好友"),
    MY_MESSAGE("我的消息"),
    MY_INVITATIONS("我的邀请"),
    VIEW_ALL("查看活动"),
    ADD_ACT("创建活动"),
    LOG_OUT("注销"),
    EXIT("退出");

    public String chn;

    OptionListItem(String chn) {
        this.chn = chn;
    }

    @Override
    public String toString() {
        return this.chn;
    }
}
