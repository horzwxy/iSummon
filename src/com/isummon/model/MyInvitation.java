package com.isummon.model;

/**
 * Created by horz on 12/22/13.
 * 用户点击“查看我发出的邀请”时，使用到的“邀请”
 */
public class MyInvitation {
    private String targetName;
    private int targetAvatar;
    private String hdName;
    private InvitationStatus status;

    public MyInvitation(String targetName, int targetAvatar, String hdName, InvitationStatus status) {
        this.targetName = targetName;
        this.targetAvatar = targetAvatar;
        this.hdName = hdName;
        this.status = status;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public int getTargetAvatar() {
        return targetAvatar;
    }

    public void setTargetAvatar(int targetAvatar) {
        this.targetAvatar = targetAvatar;
    }

    public String getHdName() {
        return hdName;
    }

    public void setHdName(String hdName) {
        this.hdName = hdName;
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
