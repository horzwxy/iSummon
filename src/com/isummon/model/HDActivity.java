package com.isummon.model;

import java.io.Serializable;

/**
 * 包含活动详细信息的model。
 */
public class HDActivity implements Serializable {
    /**
     * 活动时间字符串的格式
     */
	public static final String tmFormat = "yyyy-MM-dd hh:mm";

    private int hdId;
	private String hdName;
	private String hdAddress;
    private int longitude;
    private int latitude;
	private String hdStartTime;
    private String hdEndTime;
	private int hdOriginId;
    private String hdOriginName;
	private String hdDesc;
	private int hdType;
	private int hdNumLimit; // -1 if no limit
	private int hdCurNum;

    private int hdProperty;
	private int hdStatus;

    /**
     * 默认构造函数
     */
    public HDActivity() {

    }

    /**
     * 直接用枚举类型做函数构造活动详情的构造函数
     * @param hdId 活动id
     * @param hdName 活动名称
     * @param hdAddress 活动地点
     * @param longitude 活动地点的经度
     * @param latitude 活动地点的纬度
     * @param hdStartTime 活动开始时间
     * @param hdEndTime 活动结束时间
     * @param hdOriginId 活动发起者的id
     * @param hdOriginName 活动发起者的昵称
     * @param hdDesc 活动详情描述
     * @param hdType 活动类型
     * @param hdNumLimit 活动人数上限
     * @param hdCurNum 当前参加活动的人数
     * @param hdProperty 活动的其他属性
     * @param hdStatus 活动当前的状态
     */
    public HDActivity(int hdId, String hdName, String hdAddress, int longitude, int latitude, String hdStartTime, String hdEndTime, int hdOriginId, String hdOriginName, String hdDesc, HDType hdType, int hdNumLimit, int hdCurNum, HDProperty hdProperty, HDStatus hdStatus) {
        this.hdId = hdId;
        this.hdName = hdName;
        this.hdAddress = hdAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hdStartTime = hdStartTime;
        this.hdEndTime = hdEndTime;
        this.hdOriginId = hdOriginId;
        this.hdOriginName = hdOriginName;
        this.hdDesc = hdDesc;
        this.hdType = hdType.ordinal();
        this.hdNumLimit = hdNumLimit;
        this.hdCurNum = hdCurNum;
        this.hdProperty = hdProperty.ordinal();
        this.hdStatus = hdStatus.ordinal();
    }

    /**
     *
     * 用int值指代枚举类型实例构造活动详情的构造函数
     * @param hdId 活动id
     * @param hdName 活动名称
     * @param hdAddress 活动地点
     * @param longitude 活动地点的经度
     * @param latitude 活动地点的纬度
     * @param hdStartTime 活动开始时间
     * @param hdEndTime 活动结束时间
     * @param hdOriginId 活动发起者的id
     * @param hdOriginName 活动发起者的昵称
     * @param hdDesc 活动详情描述
     * @param hdType 活动类型，为HDType枚举实例对应的int
     * @param hdNumLimit 活动人数上限
     * @param hdCurNum 当前参加活动的人数
     * @param hdProperty 活动的其他属性，为HDProperty枚举实例对应的int
     * @param hdStatus 活动当前的状态，为HDStatus枚举实例对应的int
     */
    public HDActivity(int hdId, String hdName, String hdAddress, int longitude, int latitude, String hdStartTime, String hdEndTime, int hdOriginId, String hdOriginName, String hdDesc, int hdType, int hdNumLimit, int hdCurNum, int hdProperty, int hdStatus) {
        this.hdId = hdId;
        this.hdName = hdName;
        this.hdAddress = hdAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hdStartTime = hdStartTime;
        this.hdEndTime = hdEndTime;
        this.hdOriginId = hdOriginId;
        this.hdOriginName = hdOriginName;
        this.hdDesc = hdDesc;
        this.hdType = hdType;
        this.hdNumLimit = hdNumLimit;
        this.hdCurNum = hdCurNum;
        this.hdProperty = hdProperty;
        this.hdStatus = hdStatus;
    }

   
    @Override
	public String toString() {
		return "HDActivity [hdName=" + hdName + ", hdAddress=" + hdAddress
				+ ", hdStartTime=" + hdStartTime + ", hdEndTime=" + hdEndTime
				+ ", hdOriginId=" + hdOriginId + ", hdDesc=" + hdDesc + ", hdTags="
				+ hdType + ", hdNumLimit=" + hdNumLimit + ", hdCurNum="
				+ hdCurNum + "]";
	}
	public String getHdName() {
		return hdName;
	}
	public void setHdName(String hdName) {
		this.hdName = hdName;
	}
	public String getHdAddress() {
		return hdAddress;
	}
	public void setHdAddress(String hdAddress) {
		this.hdAddress = hdAddress;
	}
	public String getHdStartTime() {
		return hdStartTime;
	}
	public void setHdStartTime(String hdStartTime) {
		this.hdStartTime = hdStartTime;
	}
	public String getHdEndTime() {
		return hdEndTime;
	}
	public void setHdEndTime(String hdEndTime) {
		this.hdEndTime = hdEndTime;
	}
	public int getHdOriginId() {
		return hdOriginId;
	}
	public void setHdOriginId(int hdOriginId) {
		this.hdOriginId = hdOriginId;
	}
	public String getHdDesc() {
		return hdDesc;
	}
	public void setHdDesc(String hdDesc) {
		this.hdDesc = hdDesc;
	}
	public int getHdNumLimit() {
		return hdNumLimit;
	}
	public void setHdNumLimit(int hdNumLimit) {
		this.hdNumLimit = hdNumLimit;
	}
	public int getHdCurNum() {
		return hdCurNum;
	}
	public void setHdCurNum(int hdCurNum) {
		this.hdCurNum = hdCurNum;
	}

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setHdId(int id) { this.hdId = id; }

    public int getHdId() {
        return hdId;
    }

    public HDType getHdTypeInstance() {
        return HDType.values()[hdType];
    }

    public int getHdType() {
        return hdType;
    }

    public void setHdType(HDType hdType) {
        this.hdType = hdType.ordinal();
    }

    public HDStatus getHdStatusInstance() {
        return HDStatus.values()[hdStatus];
    }

    public int getHdStatus() {
        return hdStatus;
    }

    public void setHdStatus(HDStatus hdStatus) {
        this.hdStatus = hdStatus.ordinal();
    }

    public HDProperty getHdPropertyInstance() {
        return HDProperty.values()[hdProperty];
    }

    public int getHdProperty() {
        return hdProperty;
    }

    public void setHdProperty(HDProperty hdProperty) {
        this.hdProperty = hdProperty.ordinal();
    }

    public String getHdOriginName() {
        return hdOriginName;
    }

    public void setHdOriginName(String hdOriginName) {
        this.hdOriginName = hdOriginName;
    }

    /**
     * 由活动详情的model构造活动简略的model
     * @return
     */
    public SimpleHDActivity getSimpleModel() {
        return new SimpleHDActivity(
                hdId,
                hdName,
                hdOriginId,
                hdOriginName,
                longitude,
                latitude,
                getHdTypeInstance(),
                getHdStatusInstance()
        );
    }
}
