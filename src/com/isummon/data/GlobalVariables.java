package com.isummon.data;

import com.isummon.model.UserModel;
import com.isummon.net.NetHelper;

/**
 * Created by horzwxy on 12/16/13.
 */
public class GlobalVariables {

    public static NetHelper netHelper = NetHelper.getInstance();
    public static UserModel currentUser = netHelper.findUserByName("horzwxy").get(0);


}
