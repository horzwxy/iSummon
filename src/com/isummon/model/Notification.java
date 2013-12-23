package com.isummon.model;

import java.io.Serializable;

/**
 * Created by horzwxy on 12/16/13.
 */
public class Notification implements Serializable {
    @Override
    public String toString() {
        return "Notification [id=" + id + ", hdId=" + hdId
                + ", responseStatus=" + responseStatus + ", name=" + hdName
                + ", originName=" + originName + ", originAvatar="
                + originAvatar + "]";
    }

    public Notification() {
        super();
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private int hdId;
    private int responseStatus;
    private String hdName;
    private String originName;
    private int originAvatar;

    public Notification(int id, int hdId, int responseStatus, String hdName,
                        String originName, int originAvatar) {
        this.id = id;
        this.hdId = hdId;
        this.responseStatus = responseStatus;
        this.originName = originName;
        this.originAvatar = originAvatar;
        this.hdName = hdName;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHdName() {
        return hdName;
    }

    public void setHdName(String hdName) {
        this.hdName = hdName;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int isRead) {
        this.responseStatus = isRead;
    }
}
