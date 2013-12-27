package com.isummon.net;

import org.kobjects.base64.Base64;
import android.util.Log;
import com.isummon.model.DisplayInvitation;
import com.isummon.data.GlobalVariables;
import com.isummon.model.HDActivity;
import com.isummon.model.HDType;
import com.isummon.model.LogInResultType;
import com.isummon.model.Notification;
import com.isummon.model.RegisterResultType;
import com.isummon.model.SimpleHDActivity;
import com.isummon.model.UserModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


/**
 * Created by horz on 12/20/13.
 */
public class RealNetHelper extends NetHelper {
    private final String TAG ="iSummon";
    String userActionUrl = "http://10.131.251.146:8080/iSummon/services/UserActionImpl?wsdl";
    String notiActionUrl = "http://10.131.251.146:8080/iSummon/services/NotificationActionImpl?wsdl";
    String activityUrl = "http://10.131.251.146:8080/iSummon/services/ActivityActionImpl?wsdl";
    public RealNetHelper(){
        super();
    }


    //---------------------------------UserA Action----------------------------------------------------------

    public LogInResultType login(String username, String passwd) {

        Log.v(TAG, "in login: username " + username + "  passwd: " + passwd);
        String methodName = "login";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("username", username);
        request.addProperty("passwd", passwd);

        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
            GlobalVariables.currentUser = parseUserModel((SoapObject)resultObj);
            Log.v(TAG, GlobalVariables.currentUser.toString());

            test();
            return LogInResultType.SUCCESS;
        }
        return  LogInResultType.FAIL_NOT_MATCH;
    }

    private void test(){
        //                register(new UserModel());
//                getAllContacts();
//                findUserByName("ubuntu");
//            addContact(5);
//            Log.v(TAG, getUserById(2).toString());
//        getCurrentSimpleHDActivities();

//        ArrayList<UserModel> targets = new ArrayList<UserModel>();
//        targets.add(new UserModel(1));
//        targets.add(new UserModel(2));
//        invite(3, targets);
//        getCurrentSimpleHDActivities();
//        getHDActivityById(2);
//        modifyUserModel(new UserModel(5, "ubuntu", "ubuntu111", "ubuntu", 3));
//        testDemo();
//        getMyInvitations();
//        getNotifications();
//        getHDActivityByOriginId();
//        getHDActivityByOriginName("singo");
//        updateAvatar(3);
//        getHDActivityById(3);
    }


    public RegisterResultType register(UserModel newUser) {
        Log.v(TAG, "register received user " + newUser.toString());

        String methodName = "register";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("username", newUser.getUserName());
        request.addProperty("nickname", newUser.getNickName());
        request.addProperty("passwd", newUser.getPasswd());
        request.addProperty("avatarId", newUser.getAvatar());
//        Log.v(TAG, request.getProperty("nickname").toString());
        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
            Log.v(TAG, resultObj.toString());
            int retcode = Integer.parseInt(resultObj.toString());
            Log.v(TAG, "register result " + retcode);
            if(retcode == 0)
                return RegisterResultType.SUCCESS;
            else if(retcode == -2)
                return  RegisterResultType.FAIL_ON_USED_EMAIL;
            else
                return RegisterResultType.FAIL_TIME_OUT;
        }
        return RegisterResultType.FAIL_TIME_OUT;
    }

    @Override
    public void logOut() {
        GlobalVariables.currentUser = null;
    }

    @Override
    public UserModel getUserById(int userId) {
        String methodName = "getUserById";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("id", userId);
        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
            return parseUserModel((SoapObject)resultObj);
        }
        return  null;
    }

    @Override
    public ArrayList<UserModel> findUserByName(String nickname) {
        String methodName = "getUserByName";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("name", nickname);

        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
            return  getUsersFromSoap(resultObj);
        }
        return  null;
    }

    @Override
    public int addContact(int targetId) {
        String methodName = "addContact";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("originId", GlobalVariables.currentUser.getUserId());
        request.addProperty("targetId", targetId);

        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
            int code = Integer.parseInt(resultObj.toString());
            Log.v(TAG, "add contact: " + code);
            return  code;
        }
        return -1;
    }

    @Override
    public int removeContact(int targetId) {
        String methodName = "removeContact";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("originId", GlobalVariables.currentUser.getUserId());
        request.addProperty("targetId", targetId);

        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
            int code = Integer.parseInt(resultObj.toString());
            Log.v(TAG, "remove contact: " + code);
            return  code;
        }
        return -1;
    }

    @Override
    public ArrayList<UserModel> getAllContacts() {
        String methodName = "getAllContacts";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("userId", GlobalVariables.currentUser.getUserId());
        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
            return getUsersFromSoap(resultObj);
        }
        return null;
    }

    @Override
    public boolean isMyId(int userId) {
        return (GlobalVariables.currentUser.getUserId() == userId);
    }

    @Override
    public boolean updateAvatar(int avatarId) {
        SoapObject request = new SoapObject(namespace, "updateAvatar");
        request.addProperty("userId", GlobalVariables.currentUser.getUserId());
        request.addProperty("avatarId", avatarId);
        Log.v(TAG, "update avatar from " + GlobalVariables.currentUser.getAvatar() + " to " + avatarId + " of user " + GlobalVariables.currentUser.getNickName());
        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
            Log.v(TAG, "update avatar return : " + resultObj.toString());
            int code = Integer.parseInt(resultObj.toString());
            if(code == 0){
                GlobalVariables.currentUser.setAvatar(avatarId);
                return true;
            }
        }
        return  false;
    }

    /*-----------------------------------------------Notification-------------------------------------------------------------*/
    @Override
    public List<Notification> getNotifications() {
        SoapObject request = new SoapObject(namespace, "getNotificationByTargetId");
        request.addProperty("targetId", GlobalVariables.currentUser.getUserId());
        Object resultObj = makeKsoapCall(request, notiActionUrl);
        if(resultObj != null){
            Log.v(TAG, "getNotification: " + resultObj.toString());
            return  parseNotificationFromSoap(resultObj);
        }
        return null;
    }

    @Override
    public int invite(int hdId, ArrayList<UserModel> targets) {
        if(targets == null || targets.size() == 0)
            return -1;
        int originId = GlobalVariables.currentUser.getUserId();
        for(UserModel user : targets){
            SoapObject request = new SoapObject(namespace, "addInvitation");
            request.addProperty("originId", originId);
            request.addProperty("targetId", user.getUserId());
            request.addProperty("actId", hdId);
            Object resultObj = makeKsoapCall(request, notiActionUrl);
            if(resultObj != null){
                Log.v(TAG, "invite result: " + resultObj.toString());
                if(Integer.parseInt(resultObj.toString()) != 0)
                    return -1;
            }
        }
        return  0;
    }


    @Override
    public void onReadNotification(Notification notification) {
        SoapObject request = new SoapObject(namespace, "responseInvitation");
        request.addProperty("id", notification.getId());
        request.addProperty("response", notification.getResponseStatus());
        Object resultObj = makeKsoapCall(request, notiActionUrl);
        if(resultObj != null){
            Log.v(TAG, "responseNotification: " + resultObj.toString());

        }
        Log.v(TAG, "on read notification result null");
    }

    @Override
    public ArrayList<DisplayInvitation> getMyInvitations() {
        SoapObject request = new SoapObject(namespace, "getInvitationByOriginId");
        request.addProperty("originId", GlobalVariables.currentUser.getUserId());
        Object resultObj = makeKsoapCall(request, notiActionUrl);
        if(resultObj != null){
           Log.v(TAG, "getMyInvitation: " + resultObj.toString());
            return  parseMyInvitationsFromSoap(resultObj);
        }
        return null;
    }



    /*------------------------------------Activitation Action---------------------------------------------------------------------*/

    @Override
    public ArrayList<SimpleHDActivity> getCurrentSimpleHDActivities() {
        Log.v(TAG, "in getCurrentSimpleHDActivity");
        SoapObject request = new SoapObject(namespace, "getValidSimpleAct");
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            return parseSimpleHdFromSoap(resultObj);
        }
        return null;
    }

    @Override
    public ArrayList<SimpleHDActivity> getAllActs() {
        return  getCurrentSimpleHDActivities();
    }

    @Override
    public HDActivity getHDActivityById(int hdId) {
        SoapObject request = new SoapObject(namespace, "getActById");
        request.addProperty("hdId", hdId);
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            Log.v(TAG, "-------------get HDActivity By Id" + resultObj.toString() +"------------");
            return parseHDActivity((SoapObject)resultObj);
        }
        return null;
    }

    @Override
    public int addHDActivity(HDActivity hdActivity) {

        Log.v(TAG, hdActivity.toString());
        SoapObject request = new SoapObject(namespace, "addAct");
        request = formHDActivity(request, hdActivity);
        Log.v(TAG, request.toString());

        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
               Log.v(TAG, "add Hd activity get hdId: " + resultObj.toString());
            int resultCode = Integer.parseInt(resultObj.toString());
            if(resultCode > 0){
                return resultCode;
            }
        }
        return -1;
    }

    @Override
    public boolean modifyHDActivity(HDActivity hdActivityNew) {
        SoapObject request = new SoapObject(namespace, "addAct");
        request.addProperty("id", hdActivityNew.getHdId());
        request = formHDActivity(request, hdActivityNew);
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            return (Integer.parseInt(resultObj.toString()) == 1) ? true: false;
        }
        return false;
    }


    @Override
    public boolean cancleHDActivity(int hdId) {
        SoapObject request = new SoapObject(namespace, "cancelAct");
        request.addProperty("id", hdId);
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            Log.v(TAG, "in cancleHDActivity resultObj:　" + resultObj.toString());
            return  (Integer.parseInt(resultObj.toString()) == 1)? true: false;
        }
        return false;
    }

    @Override
    public boolean applyHDActivity(int hdId) {
        SoapObject request = new SoapObject(namespace, "applyHDActivity");
        request.addProperty("applierId", GlobalVariables.currentUser.getUserId());
        request.addProperty("id", hdId);
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            Log.v(TAG, "in applyHDActivity resultObj:　" + resultObj.toString());
            return  (Integer.parseInt(resultObj.toString()) == 1) ? true :false;
        }
        return false;
    }

    //-------------------------------一系列的查询方法-----

    //我发起的活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByOriginId() {
        SoapObject request = new SoapObject(namespace, "getActByOriginId");
        request.addProperty("originId", GlobalVariables.currentUser.getUserId());
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            return parseSimpleHdFromSoap(resultObj);
        }
        return null;
    }

    //我参加的活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByUserId() {
        SoapObject request = new SoapObject(namespace, "getActByParticipantId");
        request.addProperty("partId", GlobalVariables.currentUser.getUserId());
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            return parseSimpleHdFromSoap(resultObj);
        }
        return null;
    }

    //根据活动名称查询，如查询活动名称带有“三国杀”的活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByHdName(String hdName) {
        SoapObject request = new SoapObject(namespace, "getActByName");
        request.addProperty("name", hdName);
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            return parseSimpleHdFromSoap(resultObj);
        }
        return null;
    }

    //根据活动标签查询，如查询“娱乐”类的活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByHdType(HDType hdType) {
        SoapObject request = new SoapObject(namespace, "getActByType");
        request.addProperty("type", hdType.ordinal());
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            return parseSimpleHdFromSoap(resultObj);
        }
        return null;
    }

    //查询某时间范围以内的活动，两个参数可以一个为null，如(startTime, null)表示startTime以后的所有活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByTime(String startTime, String endTime) {
        SoapObject request = new SoapObject(namespace, "getActByTime");
        request.addProperty("startTime", startTime);
        request.addProperty("endTime", endTime);
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            return parseSimpleHdFromSoap(resultObj);
        }
        return null;
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByOriginName(String originName) {
        SoapObject request = new SoapObject(namespace, "getActByOriginName");
        request.addProperty("originName", originName);
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            return parseSimpleHdFromSoap(resultObj);
        }
        return null;
    }

    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByAddress(String address) {
        SoapObject request = new SoapObject(namespace, "getActByAddress");
        request.addProperty("address", address);
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){
            return parseSimpleHdFromSoap(resultObj);
        }
        return null;
    }

    /**--------------------------Private methods--------------------------------------------------------------------------------*/


    //-------------------------------------------User Model
    private UserModel parseUserModel(SoapObject soapObject){
        int userId = Integer.parseInt(soapObject.getProperty("userId").toString());
        String userName = soapObject.getProperty("userName").toString();
        String nickName = soapObject.getProperty("nickName").toString();
        int avatar = Integer.parseInt(soapObject.getProperty("avatar").toString());
        return new UserModel(userId, userName, nickName, null, avatar);
    }

    private ArrayList<UserModel> getUsersFromSoap(Object obj){
        ArrayList<UserModel> resultList = new ArrayList<UserModel>();
        Iterator ie = ((Vector)obj).iterator();
        while (ie.hasNext()){
            UserModel um  = parseUserModel((SoapObject)ie.next());
            resultList.add(um);
        }
        Log.v(TAG, "get UserModel of  " + resultList.size() );
        return  resultList;
    }

    //----------------------------------------------Activity
    private SimpleHDActivity parseSimpleHDActivity(SoapObject soapObject){
        Log.v(TAG, soapObject.toString());
        int hdId = Integer.parseInt(soapObject.getProperty("hdId").toString());
        String hdName = soapObject.getProperty("hdName").toString();
        String hdOriginName = soapObject.getProperty("hdOriginName").toString();
        int hdOriginId = Integer.parseInt(soapObject.getProperty("hdOriginId").toString());
        int hdLongitude = Integer.parseInt(soapObject.getProperty("hdLongitude").toString());
        int hdLatitude = Integer.parseInt(soapObject.getProperty("hdLatitude").toString());
        int hdType = Integer.parseInt(soapObject.getProperty("hdType").toString());
        int hdStatus = Integer.parseInt(soapObject.getProperty("hdStatus").toString());
        SimpleHDActivity ret = new SimpleHDActivity(hdId, hdName, hdOriginId, hdOriginName, hdLongitude, hdLatitude, hdType, hdStatus);
        Log.v(TAG, ret.toString());
        return ret;
    }
    private ArrayList<SimpleHDActivity> parseSimpleHdFromSoap(Object obj){
        ArrayList<SimpleHDActivity> resultList = new ArrayList<SimpleHDActivity>();
        Iterator ie = ((Vector)obj).iterator();
        while (ie.hasNext()){
            SimpleHDActivity sh  = parseSimpleHDActivity((SoapObject) ie.next());
            Log.v(TAG, sh.toString());
            resultList.add(sh);
        }
        Log.v(TAG, "get SimpleHdActivity of  " + resultList.size() );
        return  resultList;
    }

    private HDActivity parseHDActivity(SoapObject soapObject){
        Log.v(TAG, "-----------------" + soapObject.toString()+"-------------------");
        int hdId = Integer.parseInt(soapObject.getProperty("hdId").toString());
        String hdName = soapObject.getProperty("hdName").toString();
        String hdAddress = soapObject.getProperty("hdAddress").toString();
        int longitude = Integer.parseInt(soapObject.getProperty("longitude").toString());
        int latitude = Integer.parseInt(soapObject.getProperty("latitude").toString());
        String hdStartTime = soapObject.getProperty("hdStartTime").toString();
        String hdEndTime = soapObject.getProperty("hdEndTime").toString();
        int hdOriginId = Integer.parseInt(soapObject.getProperty("hdOriginId").toString());
        String hdOriginName = soapObject.getProperty("hdOriginName").toString();
        String hdDesc = soapObject.getProperty("hdDesc").toString();
        int hdType = Integer.parseInt(soapObject.getProperty("hdType").toString());
        int hdNumLimit = Integer.parseInt(soapObject.getProperty("hdNumLimit").toString());
        int hdCurNum = Integer.parseInt(soapObject.getProperty("hdCurNum").toString());
        int hdProperty = Integer.parseInt(soapObject.getProperty("hdProperty").toString());
        int hdStatus = Integer.parseInt(soapObject.getProperty("hdStatus").toString());

        HDActivity hd = new HDActivity(hdId,hdName,hdAddress,longitude, latitude, hdStartTime,
                hdEndTime, hdOriginId, hdOriginName, hdDesc, hdType, hdNumLimit, hdCurNum, hdProperty, hdStatus);

        Log.v(TAG, hd.toString());
        return hd;
    }

    private SoapObject formHDActivity(SoapObject request, HDActivity act){
        request.addProperty("name", act.getHdName());
        request.addProperty("address", act.getHdAddress());
        request.addProperty("longitude", act.getLongitude());
        request.addProperty("latitude", act.getLatitude());
//        request.addProperty("longitude", 11.0);
//        request.addProperty("latitude", 11.0);
        request.addProperty("startTime", act.getHdStartTime());
        request.addProperty("endTime", act.getHdEndTime());
        request.addProperty("originId", GlobalVariables.currentUser.getUserId());
        request.addProperty("desc", act.getHdDesc());
        request.addProperty("type", act.getHdType());
        request.addProperty("numLimit", act.getHdNumLimit());
        request.addProperty("curNum", act.getHdCurNum());
        request.addProperty("property", act.getHdProperty());
        request.addProperty("status", act.getHdStatus());
        return  request;

    }

    //---------------------------------------------Notification
    private DisplayInvitation parseMyInvitation(SoapObject soapObject){
        String targetName = soapObject.getProperty("targetName").toString();
        int targetAvatar = Integer.parseInt(soapObject.getProperty("targetAvatar").toString());
        String activityName = soapObject.getProperty("activityName").toString();
        int responseStatus = Integer.parseInt(soapObject.getProperty("responseStatus").toString());
        return new DisplayInvitation(targetName, targetAvatar, activityName, responseStatus);
    }

    private ArrayList<DisplayInvitation> parseMyInvitationsFromSoap(Object obj){
        ArrayList<DisplayInvitation> resultList = new ArrayList<DisplayInvitation>();
        Iterator ie = ((Vector)obj).iterator();
        while (ie.hasNext()){
            DisplayInvitation mi  = parseMyInvitation((SoapObject)ie.next());
            resultList.add(mi);
        }
        Log.v(TAG, "get MyInvitation of  " + resultList.size() );
        return  resultList;
    }

    private Notification parseNotification(SoapObject soapObject){
        int notificationId = Integer.parseInt(soapObject.getProperty("id").toString());
        int hdId = Integer.parseInt(soapObject.getProperty("hdId").toString());
        int responseStatus = Integer.parseInt(soapObject.getProperty("responseStatus").toString());
        String hdName = soapObject.getProperty("desc").toString();
        String originName = soapObject.getProperty("originName").toString();
        int originAvatar = Integer.parseInt(soapObject.getProperty("originAvatar").toString());
        Notification noti = new Notification(notificationId,hdId, responseStatus, hdName, originName, originAvatar);
        Log.v(TAG, noti.toString());
        return  noti;
    }
    private ArrayList<Notification> parseNotificationFromSoap(Object obj){
        ArrayList<Notification> resultList = new ArrayList<Notification>();
        Iterator ie = ((Vector)obj).iterator();
        while (ie.hasNext()){
            Notification noti  = parseNotification((SoapObject) ie.next());
            resultList.add(noti);
        }
        Log.v(TAG, "get Notification of  " + resultList.size() );
        return  resultList;
    }



    private Object makeKsoapCall(SoapObject request, String url){
        Log.v(TAG, "try to make soap call: " + request.toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = request;
        HttpTransportSE ht = new HttpTransportSE(url);
        try {
            ht.call(null, envelope);
            if (envelope.getResponse() != null) {
                return envelope.getResponse();
            } else {
                Log.v(TAG, " null while make ksoap call");
                return  null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /*-------------------------------Fake test----------------------------------**/
    private void testDemo(){
        String url = "http://10.131.251.146:8080/iSummon/services/TestActionImpl?wsdl";
        String ret;
        SoapObject request = new SoapObject(namespace, "testA");
        request.addProperty("a", 420);
        request.addProperty("b", new DemoBean(1, "ss"));
        Object resultObj = makeKsoapCall(request, url);
        if(resultObj != null){
            Log.v(TAG, resultObj.toString());
            SoapPrimitive response = (SoapPrimitive)resultObj;
            ret = response.toString();
            byte[] bytes = Base64.decode(ret);
            DemoBean demoBean = DemoBean.deserialize(bytes);
            ret = "Id=" + demoBean.getId() + " Name=" + demoBean.getName();
            Log.v(TAG, ret);
        }
        Log.v(TAG, "resultObj null");
        return ;
    }


}

