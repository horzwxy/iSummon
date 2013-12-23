package com.isummon.model;

import java.io.Serializable;

/**
 * Created by horzwxy on 12/16/13.
 */
public class DisplayInvitation implements Serializable {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String targetName;
    private int targetAvatar;
    private String activityName;
    private int responseStatus;

    public DisplayInvitation(String targetName, int targetAvatar,String activityName, int responseStatus) {
        this.setTargetName(targetName);
        this.setTargetAvatar(targetAvatar);
        this.setActivityName(activityName);
        this.setResponseStatus(responseStatus);
    }

	public DisplayInvitation() {
		super();
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

    public InvitationStatus getInvitationStatus() {
        return InvitationStatus.values()[responseStatus];
    }

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
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

	@Override
	public String toString() {
		return "DisplayInvitation [targetName=" + targetName
				+ ", targetAvatar=" + targetAvatar + ", activityName="
				+ activityName + ", responseStatus=" + responseStatus + "]";
	}


}
