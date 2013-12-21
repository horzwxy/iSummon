package com.isummon.net;

import com.isummon.model.HDActivity;
import com.isummon.model.HDProperty;
import com.isummon.model.HDStatus;
import com.isummon.model.HDType;
import com.isummon.model.MyInvitation;
import com.isummon.model.LogInResultType;
import com.isummon.model.Notification;
import com.isummon.model.RegisterResultType;
import com.isummon.model.SimpleHDActivity;
import com.isummon.model.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by horz on 12/20/13.
 */
public class FakeNetHelper extends NetHelper {

    private ArrayList<HDActivity> hdArray = new ArrayList<HDActivity>();
    private ArrayList<UserModel> users = new ArrayList<UserModel>();
    private ArrayList<UserModel> contacts = new ArrayList<UserModel>();
    private ArrayList<Notification> notifications = new ArrayList<Notification>();
    private ArrayList<MyInvitation> invitations = new ArrayList<MyInvitation>();

    public FakeNetHelper(){
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
        a1.setHdOriginId(1);
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
        a2.setHdOriginId(2);
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
        a3.setHdOriginId(3);
        a3.setHdProperty(HDProperty.SECRET);
        a3.setHdStatus(HDStatus.CANCELED);
        a3.setHdOriginName("陈水扁");
        a3.setLatitude(31.195722 * 1E6);
        a3.setLongitude(121.604425 * 1E6);
        hdArray.add(a3);

        Notification n1 = new Notification(
                m1.getNickName(),
                m1.getAvatar(),
                a1.getHdId()
        );
        notifications.add(n1);

        MyInvitation i1 = new MyInvitation(
                "罗玉凤",
                0,
                "入党",
                MyInvitation.InvitationStatus.READ
        );
        invitations.add(i1);
    }

    @Override
    public ArrayList<SimpleHDActivity> getAllActs() {
        ArrayList<SimpleHDActivity> result = new ArrayList<SimpleHDActivity>();
        for(HDActivity h : hdArray) {
            result.add(h.getSimpleModel());
        }
        return result;
    }

    @Override
    public LogInResultType login(String username, String passwd) {

        return LogInResultType.SUCCESS;
    }

    @Override
    public void logOut() {

    }

    @Override
    public ArrayList<SimpleHDActivity> getCurrentSimpleHDActivities() {
       return getAllActs();
    }

    @Override
    public List<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public HDActivity getHDActivityById(int hdId) {
        for (HDActivity a : hdArray) {
            if (a.getHdId() == hdId) {
                return a;
            }
        }
        return null;
    }

    @Override
    public int addHDActivity(HDActivity hdActivity) {
        return 0;
    }

    @Override
    public boolean modifyHDActivity(HDActivity hdActivityNew) {
        return true;
    }

    @Override
    public boolean cancleHDActivity(int hdId) {
        return true;
    }


    @Override
    public RegisterResultType register(UserModel newUser) {
        return RegisterResultType.SUCCESS;
    }

    @Override
    public boolean applyHDActivity(int hdId) {
        return false;
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByOriginId() {
        return new ArrayList<SimpleHDActivity>();
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByUserId() {
        return new ArrayList<SimpleHDActivity>();
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByHdName(String hdName) {
        for(SimpleHDActivity s : getAllActs()) {
            if(s.getHdName().equals(hdName)) {
                return new ArrayList<SimpleHDActivity>(Arrays.asList(s));
            }
        }
        return new ArrayList<SimpleHDActivity>();
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByHdType(HDType hdType) {
        for(SimpleHDActivity s : getAllActs()) {
            if(s.getHdType().equals(hdType)) {
                return new ArrayList<SimpleHDActivity>(Arrays.asList(s));
            }
        }
        return new ArrayList<SimpleHDActivity>();
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByTime(String startTime, String endTime) {
        return new ArrayList<SimpleHDActivity>();
    }

    @Override
    public int invite(int hdId, ArrayList<UserModel> targets) {
        return 1;
    }

    @Override
    public ArrayList<UserModel> findUserByName(String nickname) {
        for(UserModel userModel : users) {
            if(userModel.getNickName().equals(nickname)) {
                return new ArrayList<UserModel>(Arrays.asList(userModel));
            }
        }
        return new ArrayList<UserModel>();
    }

    @Override
    public int addContact(int targetId) {
        return 1;
    }

    @Override
    public ArrayList<UserModel> getAllContacts() {
        return contacts;
    }

    @Override
    public void onReadNotification(Notification notification) {

    }

    @Override
    public boolean isMyId(int userId) {
//        return GlobalVariables.currentUser.getUserId() == userId;
        return false;
    }

    @Override
    public ArrayList<MyInvitation> getMyInvitations() {
        return invitations;
    }

    public static void fakeBlock() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
