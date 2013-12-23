package com.isummon.model;

/**
 * Created by horzwxy on 12/23/13.
 */
public enum InvitationStatus {
    UNREAD("对方暂未阅读"),
    READ("对方已阅读"),
    REJECTED("对方已拒绝");

    private String chn;

    InvitationStatus(String chn) {
        this.chn = chn;
    }

    public String getChn() {
        return chn;
    }
}
