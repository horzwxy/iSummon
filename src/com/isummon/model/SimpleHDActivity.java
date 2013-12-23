package com.isummon.model;

import java.io.Serializable;

/**
 * 我们有一个活动的类HDActivity,而这是一个简化的类用于
 * 网络传输，减少负荷
 * 该类用于用户刚登陆时在地图上画出当前活动
 * 服务器返回的是所有有效的活动
 * 有效的定义：
 * 1. 有人数限制的活动，在没有超过人数限制的情况下
 * 2. 还没有到开始时间的活动
 * 3. 已经开始但尚未结束，且不限制人数的活动
 * 4. 其他。
 */
public class SimpleHDActivity implements Serializable {
    private int hdId;            //活动的id
    private String hdName;        //活动的名称
    private String hdOriginName;        //活动发起者的id，
    private int hdOriginId;
    private double hdLongitude;    //活动的经度
    private double hdLatitude;    //纬度
    private int hdType;
    private int hdStatus;

    public SimpleHDActivity(String hdName, double hdLatitude, double hdLongitude){
        this.hdName = hdName;
        this.hdLatitude = hdLatitude;
        this.hdLongitude = hdLongitude;
    }
    public SimpleHDActivity(int hdId, String hdName, int hdOriginId, String hdOriginName, double hdLongitude, double hdLatitude, HDType hdType, HDStatus hdStatus) {
        this.hdId = hdId;
        this.hdName = hdName;
        this.hdOriginId = hdOriginId;
        this.hdOriginName = hdOriginName;
        this.hdLongitude = hdLongitude;
        this.hdLatitude = hdLatitude;
        this.hdType = hdType.ordinal();
        this.hdStatus = hdStatus.ordinal();
    }

    public SimpleHDActivity(int hdId, String hdName, int hdOriginId, String hdOriginName, double hdLongitude, double hdLatitude, int hdType, int hdStatus) {
        this.hdId = hdId;
        this.hdName = hdName;
        this.hdOriginId = hdOriginId;
        this.hdOriginName = hdOriginName;
        this.hdLongitude = hdLongitude;
        this.hdLatitude = hdLatitude;
        this.hdType = hdType;
        this.hdStatus = hdStatus;
    }

    public int getHdId() {
        return hdId;
    }

    public String getHdName() {
        return hdName;
    }

    public String getHdOriginName() {
        return hdOriginName;
    }

    public double getHdLongitude() {
        return hdLongitude;
    }

    public double getHdLatitude() {
        return hdLatitude;
    }

    public HDType getHdType() {
        return HDType.values()[hdType];
    }

    public HDStatus getHdStatus() {
        return HDStatus.values()[hdStatus];
    }

    public int getHdOriginId() {
        return hdOriginId;
    }

    public void setHdOriginId(int hdOriginId) {
        this.hdOriginId = hdOriginId;
    }
}
