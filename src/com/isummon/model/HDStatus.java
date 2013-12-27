package com.isummon.model;

/**
 * 活动状态
 */
public enum HDStatus {

    /**
     * 允许加入
     */
    OPEN("正在招募受骗者"),
    /**
     * 活动名额已满
     */
    NO_VACANCY("名额已满"),
    /**
     * 活动已取消
     */
    CANCELED("已取消");

    private String chn;

    HDStatus(String chn) {
        this.chn = chn;
    }

    /**
     * 获取活动状态的汉语表示
     * @return
     */
    public String getChn() {
        return chn;
    }

    /**
     * 获取各种活动状态的汉语表示
     * @return
     */
    public static String[] getChns() {
        HDStatus[] values = values();
        String[] result = new String[values.length];
        for(int i = 0; i < result.length; i++) {
            result[i] = values[i].getChn();
        }
        return result;
    }
}
