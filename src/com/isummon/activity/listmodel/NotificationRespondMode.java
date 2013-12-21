package com.isummon.activity.listmodel;

/**
 * Created by horz on 12/22/13.
 */
public enum NotificationRespondMode {
    VIEW_DETAILS("查看活动详情"),
    REJECT("拒绝邀请"),
    LATER("以后再说");

    private String chn;

    NotificationRespondMode(String chn) {
        this.chn = chn;
    }

    public String getChn() {
        return chn;
    }

    public static String[] getChns() {
        NotificationRespondMode[] modes = values();
        String[] result = new String[modes.length];
        for(int i = 0; i < modes.length; i++) {
            result[i] = modes[i].getChn();
        }
        return result;
    }

    public String toString() {
        return this.chn;
    }
}
