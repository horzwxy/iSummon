package com.isummon.model;

import com.isummon.R;

import java.io.Serializable;

/**
 * Created by horzwxy on 12/15/13.
 */
public enum HDType implements Serializable {
    SPORT("运动"),
    STUDY("学习"),
    ENTERTAINMENT("娱乐"),
    DINING("聚餐"),
    OTHER("其他");

    private String chn;

    HDType(String chn) {
        this.chn = chn;
    }

    public String getChn() {
        return this.chn;
    }

    public static String[] getChns() {
        HDType[] types = values();
        String[] result = new String[types.length];
        for(int i = 0; i < types.length; i++) {
            result[i] = types[i].getChn();
        }
        return result;
    }

    @Override
    public String toString() {
        return this.chn;
    }
}
