package com.isummon.model;

/**
 * 邀请的处理状态
 */
public enum InvitationStatus {
    /**
     * 被邀请人暂未阅读此消息
     */
    UNREAD("对方暂未阅读"),
    /**
     * 被邀请人已阅读此消息，但未做出回应
     */
    READ("对方已阅读"),
    /**
     * 被邀请人已拒绝此邀请
     */
    REJECTED("对方已拒绝");

    private String chn;

    InvitationStatus(String chn) {
        this.chn = chn;
    }

    /**
     * 获取邀请状态的汉语表达
     * @return
     */
    public String getChn() {
        return chn;
    }
}
