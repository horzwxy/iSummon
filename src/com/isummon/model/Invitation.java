package com.isummon.model;

/**
 * Created by horzwxy on 12/16/13.
 */
public class Invitation {
    private int originId;
    private int targetId;
    private int hdId;
    private InvitationStatus status;

    public Invitation(int targetId, int hdId) {
        this.targetId = targetId;
        this.hdId = hdId;
    }

    public Invitation(int originId, int targetId, int hdId, InvitationStatus status) {
        this.originId = originId;
        this.targetId = targetId;
        this.hdId = hdId;
        this.status = status;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getHdId() {
        return hdId;
    }

    public void setHdId(int hdId) {
        this.hdId = hdId;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

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
}
