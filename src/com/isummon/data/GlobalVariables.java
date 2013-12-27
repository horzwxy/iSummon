package com.isummon.data;

import com.isummon.model.UserModel;
import com.isummon.net.NetHelper;

/**
 * 应用内部各个类共享的数据。
 */
public class GlobalVariables {

    /**
     * 网络连接工具，用于与服务器端通信。
     */
    public static NetHelper netHelper = NetHelper.getInstance();
    /**
     * 在此保留登录用户的实例。
     */
    public static UserModel currentUser = netHelper.findUserByName("horzwxy").get(0);//new UserModel();
}
