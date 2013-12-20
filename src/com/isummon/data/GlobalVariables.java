package com.isummon.data;

import com.isummon.model.UserModel;
import com.isummon.net.FakeDataProvider;

/**
 * Created by horzwxy on 12/16/13.
 */
public class GlobalVariables {

    public static UserModel currentUser = FakeDataProvider.findUserByName("horzwxy");


}
