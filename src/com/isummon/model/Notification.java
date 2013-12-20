package com.isummon.model;

/**
 * Created by horzwxy on 12/16/13.
 */
public class Notification {
    private String originName;
    private int hdId;
    private String hdName;
    private int originAvatar;

    public Notification(String originName, int originAvatar, int hdId, String hdName) {
        this.hdId = hdId;
        this.hdName = hdName;
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

    public String getHdName() {
        return hdName;
    }

    public void setHdName(String hdName) {
        this.hdName = hdName;
    }

    public int getOriginAvatar() {
        return originAvatar;
    }

    public void setOriginAvatar(int originAvatar) {
        this.originAvatar = originAvatar;
    }
}
