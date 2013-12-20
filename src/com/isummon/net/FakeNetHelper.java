package com.isummon.net;

import com.isummon.model.HDActivity;
import com.isummon.model.HDType;
import com.isummon.model.LogInResultType;
import com.isummon.model.Notification;
import com.isummon.model.RegisterResultType;
import com.isummon.model.SimpleHDActivity;
import com.isummon.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by horz on 12/20/13.
 */
public class FakeNetHelper extends NetHelper {

    @Override
    public ArrayList<SimpleHDActivity> getAllActs() {
        return null;
    }

    @Override
    public LogInResultType login(String username, String passwd) {
        return null;
    }

    @Override
    public RegisterResultType register(String username, String nickname, String passwd) {
        return null;
    }

    @Override
    public void logOut() {

    }

    @Override
    public ArrayList<SimpleHDActivity> getCurrentSimpleHDActivities() {
        return null;
    }

    @Override
    public List<Notification> getNotifications() {
        return null;
    }

    @Override
    public HDActivity getHDActivityById(int hdId) {
        return null;
    }

    @Override
    public int addHDActivity(HDActivity hdActivity) {
        return 0;
    }

    @Override
    public boolean modifyHDActivity(HDActivity hdActivityNew) {
        return false;
    }

    @Override
    public boolean cancleHDActivity(int hdId) {
        return false;
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByOriginId() {
        return null;
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByUserId() {
        return null;
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByHdName(String hdName) {
        return null;
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByHdType(HDType hdType) {
        return null;
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByTime(String startTime, String endTime) {
        return null;
    }

    @Override
    public int invite(int hdId, ArrayList<UserModel> targets) {
        return 0;
    }

    @Override
    public ArrayList<UserModel> findUserByName(String nickname) {
        return null;
    }

    @Override
    public int addContact(int targetId) {
        return 0;
    }

    @Override
    public ArrayList<UserModel> getAllContacts() {
        return null;
    }

    @Override
    public void onReadNotification(Notification notification) {

    }
}
