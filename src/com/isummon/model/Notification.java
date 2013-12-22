package com.isummon.model;

/**
 * Created by horzwxy on 12/16/13.
 */
public class Notification {
    private int notificationId;
    private String originName;
    private int hdId;
    private int originAvatar;

    public Notification(int notificationId, String originName, int originAvatar, int hdId) {
        this.notificationId = notificationId;
        this.hdId = hdId;
        this.originName = originName;
        this.originAvatar = originAvatar;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
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
