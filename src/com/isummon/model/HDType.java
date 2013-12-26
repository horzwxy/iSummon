package com.isummon.model;

import java.io.Serializable;

/**
 * 活动类别
 */
public enum HDType implements Serializable {
    /**
     * 运动
     */
    SPORT("运动"),
    /**
     * 学习
     */
    STUDY("学习"),
    /**
     * 娱乐
     */
    ENTERTAINMENT("娱乐"),
    /**
     * 聚餐
     */
    DINING("聚餐"),
    /**
     * 其他活动类别
     */
    OTHER("其他");

    private String chn;

    HDType(String chn) {
        this.chn = chn;
    }

    /**
     * 获取活动类别的汉语表示
     * @return
     */
    public String getChn() {
        return this.chn;
    }

    /**
     * 获取所有活动类别的汉语表示
     * @return
     */
    public static String[] getChns() {
        HDType[] types = values();
        String[] result = new String[types.length];
        for(int i = 0; i < types.length; i++) {
            result[i] = types[i].getChn();
        }
        return result;
    }

    /**
     *
     * @return 返回活动类别的汉语表示
     */
    @Override
    public String toString() {
        return this.chn;
    }
}
