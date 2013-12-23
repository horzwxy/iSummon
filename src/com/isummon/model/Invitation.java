package com.isummon.model;

import java.io.Serializable;

/**
 * Created by horzwxy on 12/16/13.
 */
public class Invitation implements Serializable {
   
    @Override
	public String toString() {
		return "Invitation [originId=" + originId + ", targetId=" + targetId
				+ ", actId=" + actId + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Invitation() {
		super();
	}

	private int originId;
    private int targetId;
    private int actId;

    public Invitation(int originId, int targetId, int actId) {
        this.setOriginId(originId);
        this.setTargetId(targetId);
        this.setActId(actId);
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

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}

	

   
}
