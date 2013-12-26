package com.isummon.model;

import java.io.Serializable;

/**
 * 用于向服务器提交的邀请model
 */
public class Invitation implements Serializable {

    private static  final long serialVersionUID = -1239388387363463L;
	private int originId;
    private int targetId;
    private int actId;

    public Invitation() {

    }
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

    @Override
    public String toString() {
        return "Invitation{" +
                "originId=" + originId +
                ", targetId=" + targetId +
                ", actId=" + actId +
                '}';
    }

   
}
