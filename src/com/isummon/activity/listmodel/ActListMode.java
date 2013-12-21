package com.isummon.activity.listmodel;

/**
 * Created by horzwxy on 12/18/13.
 */
public enum ActListMode {
    ALL("所有活动"),
    TYPE("活动类型");

    private String chn;

    ActListMode(String chn) {
        this.chn = chn;
    }

    public String getChn() {
        return chn;
    }

    public static String[] getChns() {
        ActListMode[] modes = values();
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
