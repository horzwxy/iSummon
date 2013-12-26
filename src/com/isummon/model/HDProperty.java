package com.isummon.model;

/**
 * 活动的其他属性
 */
public enum HDProperty {
    /**
     * 绝密活动
     */
    SECRET("绝密活动"),
    /**
     * 随时可以加入
     */
    ANYTIME_OPEN("随时欢迎加入"),
    /**
     * 无其他限制
     */
    OTHER("无");

    private String chn;

    HDProperty(String chn) {
        this.chn = chn;
    }

    /**
     * 获取活动其他属性的汉语表示
     * @return
     */
    public String getChn() {
        return this.chn;
    }

    /**
     * 获取各种活动其他属性的汉语表示
     * @return
     */
    public static String[] getChns() {
        HDProperty[] values = values();
        String[] result = new String[values.length];
        for(int i = 0; i < result.length; i++) {
            result[i] = values[i].getChn();
        }
        return result;
    }
}
