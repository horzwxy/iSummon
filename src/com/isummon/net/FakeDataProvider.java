package com.isummon.net;

import com.isummon.model.HDActivity;
import com.isummon.model.HDProperty;
import com.isummon.model.HDStatus;
import com.isummon.model.HDType;
import com.isummon.model.Notification;
import com.isummon.model.SimpleHDActivity;
import com.isummon.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horzwxy on 12/18/13.
 */
public class FakeDataProvider {

    public static ArrayList<HDActivity> hdArray = new ArrayList<HDActivity>();
    public static ArrayList<UserModel> users = new ArrayList<UserModel>();
    public static ArrayList<UserModel> contacts = new ArrayList<UserModel>();
    public static ArrayList<Notification> notifications = new ArrayList<Notification>();

    static {
        UserModel m1 = new UserModel();
        m1.setAvatar(0);
        m1.setNickName("horzwxy");
        m1.setPasswd("horzwxy");
        m1.setUserId(1);
        m1.setUserName("horzwxy@isummon.com");
        users.add(m1);

        UserModel m2 = new UserModel();
        m2.setAvatar(0);
        m2.setNickName("罗玉凤");
        m2.setPasswd("lyf");
        m2.setUserId(2);
        m2.setUserName("lyf@isummon.com");
        users.add(m2);

        UserModel m3 = new UserModel();
        m3.setAvatar(0);
        m3.setNickName("陈水扁");
        m3.setPasswd("csb");
        m3.setUserId(3);
        m3.setUserName("csb@isummon.com");
        users.add(m3);

        contacts.add(m2);
        contacts.add(m3);

        HDActivity a1 = new HDActivity();
        a1.setHdId(1);
        a1.setHdName("我们去加班吧!");
        a1.setHdAddress("世界500强");
        a1.setHdStartTime("2013-12-18:0915");
        a1.setHdEndTime("2013-12-19:0916");
        a1.setHdDesc("加班是世界上最幸福的事情！！！");
        a1.setHdCurNum(0);
        a1.setHdNumLimit(100);
        a1.setHdType(HDType.OTHER);
        a1.setHdOrigin(1);
        a1.setHdProperty(HDProperty.ANYTIME_OPEN);
        a1.setHdStatus(HDStatus.NO_VACANCY);
        a1.setHdOriginName("horzwxy");
        a1.setLatitude(31.195719 * 1E6);
        a1.setLongitude(121.604423 * 1E6);
        hdArray.add(a1);

        HDActivity a2 = new HDActivity();
        a2.setHdId(2);
        a2.setHdName("我去炸学校，天天不迟到");
        a2.setHdAddress("复旦大学");
        a2.setHdStartTime("2013-12-17:0915");
        a2.setHdEndTime("2013-12-18:0916");
        a2.setHdDesc("万恶的PJ");
        a2.setHdCurNum(5);
        a2.setHdNumLimit(6);
        a2.setHdType(HDType.DINING);
        a2.setHdOrigin(2);
        a2.setHdProperty(HDProperty.OTHER);
        a2.setHdStatus(HDStatus.OPEN);
        a2.setHdOriginName("罗玉凤");
        a2.setLatitude(31.195720 * 1E6);
        a2.setLongitude(121.604424 * 1E6);
        hdArray.add(a2);

        HDActivity a3 = new HDActivity();
        a3.setHdName("入党");
        a3.setHdAddress("桃园劳教所");
        a3.setHdStartTime("2013-12-20:2100");
        a3.setHdEndTime("2013-12-21:2200");
        a3.setHdDesc("伟大！光荣！正确！");
        a3.setHdCurNum(1);
        a3.setHdNumLimit(1);
        a3.setHdType(HDType.ENTERTAINMENT);
        a3.setHdOrigin(3);
        a3.setHdProperty(HDProperty.SECRET);
        a3.setHdStatus(HDStatus.CANCELED);
        a3.setHdOriginName("陈水扁");
        a3.setLatitude(31.195722 * 1E6);
        a3.setLongitude(121.604425 * 1E6);
        hdArray.add(a3);

        Notification n1 = new Notification(
                m1.getNickName(),
                m1.getAvatar(),
                a1.getHdId(),
                a1.getHdName()
        );
        notifications.add(n1);
    }

    public static List<SimpleHDActivity> getSimpleHDActivities() {
        List<SimpleHDActivity> result = new ArrayList<SimpleHDActivity>();
        for (HDActivity a : hdArray) {
            result.add(a.getSimpleModel());
        }
        return result;
    }

    public static HDActivity getHDById(int id) {
        for (HDActivity a : hdArray) {
            if (a.getHdId() == id) {
                return a;
            }
        }
        return null;
    }

    public static UserModel findUserByName(String name) {
        for(UserModel userModel : users) {
            if(userModel.getNickName().equals(name)) {
                return userModel;
            }
        }
        return null;
    }

    public static ArrayList<UserModel> getContacts() {
        return contacts;
    }

    public static void fakeBlock() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Notification> getNotifications() {
        return notifications;
    }
}
