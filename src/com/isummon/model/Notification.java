package com.isummon.model;

/**
 * Created by horzwxy on 12/16/13.
 */
public class Notification {
    private String originName;
    private int hdId;
    private int originAvatar;

    public Notification(String originName, int originAvatar, int hdId) {
        this.hdId = hdId;
        this.originName = originName;
        this.originAvatar = originAvatar;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public int getHdId() {
        return hdId;
    }

    public void setHdId(int hdId) {
        this.hdId = hdId;
    }

    public int getOriginAvatar() {
        return originAvatar;
    }

    public void setOriginAvatar(int originAvatar) {
        this.originAvatar = originAvatar;
    }
}
