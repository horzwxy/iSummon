package com.isummon.net;

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
    String activityUrl = "http://10.131.224.63:808/iSummon/services/ActivityActionImpl?wsdl";
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
//                register(new UserModel());
//                getAllContacts();
//                findUserByName("ubuntu");
//            addContact(5);
//            Log.v(TAG, getUserById(2).toString());
        getCurrentSimpleHDActivities();
            return LogInResultType.SUCCESS;
        }
        return  LogInResultType.FAIL_NOT_MATCH;
    }

    public RegisterResultType register(UserModel newUser) {
        String methodName = "register";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("username", newUser.getUserName());
        request.addProperty("nickname", newUser.getNickName());
        request.addProperty("passwd", newUser.getPasswd());
        request.addProperty("avatarId", newUser.getAvatar());

        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
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

        Object resultObj = makeKsoapCall(request, userActionUrl);
        if(resultObj != null){
            return Boolean.parseBoolean(resultObj.toString());
        }
        return  false;
    }

    /*-----------------------------------------------Notification-------------------------------------------------------------*/
    @Override
    public List<Notification> getNotifications() {
        return null;
    }

    @Override
    public int invite(int hdId, ArrayList<UserModel> targets) {
        return  0;
    }

    @Override
    public void onReadNotification(Notification notification) {

    }

    @Override
    public ArrayList<DisplayInvitation> getMyInvitations() {
        return null;
    }



    /*------------------------------------Activitation Action---------------------------------------------------------------------*/
    /**
     * 返回当前有效的活动简介
     * SimpleHDActivity用于网络传输的轻量级HDActivity，详情见其类定义
     *
     * @return
     */
    @Override
    public ArrayList<SimpleHDActivity> getCurrentSimpleHDActivities() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ArrayList<SimpleHDActivity> retList = new ArrayList<SimpleHDActivity>();
//        retList.add(new SimpleHDActivity("Test SimpleHD1", 31.195 * 1E6, 121.604 * 1E6));
//        retList.add(new SimpleHDActivity("Test SimpleHD2", 31.196 * 1E6, 121.604 * 1E6));
//        retList.add(new SimpleHDActivity("Test SimpleHD3", 31.197 * 1E6, 121.604 * 1E6));
//        return retList;
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
        String methodName = "getValidSimpleAct";
        SoapObject request = new SoapObject(namespace, methodName);
        return  null;
    }


    /**
     * 查
     * 返回相应id的活动详情
     *
     * @param hdId 活动id
     * @return 活动详情
     * <p/>
     * 用例：
     * 1. 用户在地图图层上点击某活动，或用户在活动管理列表中点击该活动时，
     * 应跳转到ShowHDActivity界面，同时通过本方法获取活动详情并显示
     */
    @Override
    public HDActivity getHDActivityById(int hdId) {
        SoapObject request = new SoapObject(namespace, "getActById");
        request.addProperty("hdId", hdId);
        Object resultObj = makeKsoapCall(request, activityUrl);
        if(resultObj != null){

        }
        return null;
    }

    /**
     * 增
     * 用户添加活动
     *
     * @param hdActivity 活动详情
     * @return 添加后的活动id，添加失败返回-1
     */
    @Override
    public int addHDActivity(HDActivity hdActivity) {
        int hdId = 0; // add hdactivity
        return hdId;
    }

    /**
     * 改
     * 更改活动  （暂定为这样，可能返回类型较弱，考虑应返回具体出错信息）
     *
     * @param hdActivityNew 要更改的活动，hdId不能改变，其他可能可以改变
     * @return 成功or失败，成功的话用客户端的备份更新原来内容
     * <p/>
     * 注意：
     * 1. 只有活动发起者才能更改他发起的活动！！客户端调用该方法前要判断请求者userId与活动发起人hdOrigin是否一致
     * 2. HDActivity中有些属性是不能更改的，客户端不能将这些属性暴露给用户
     * 3. 更改活动之后服务器端应通知参加的用户
     */
    @Override
    public boolean modifyHDActivity(HDActivity hdActivityNew) {
        return false;
    }

    /**
     * 删
     * 活动发起者可以取消该活动，取消该活动之后服务端应通知参加的用户
     *
     * @param hdId
     * @return
     */
    @Override
    public boolean cancleHDActivity(int hdId) {
        return false;
    }

    @Override
    public boolean applyHDActivity(int hdId) {
        return false;
    }

    //-------------------------------一系列的查询方法-----

    //我发起的活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByOriginId() {
        return null;
    }

    //我参加的活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByUserId() {
        return null;
    }

    //根据活动名称查询，如查询活动名称带有“三国杀”的活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByHdName(String hdName) {
        return null;
    }

    //根据活动标签查询，如查询“娱乐”类的活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByHdType(HDType hdType) {
        return null;
    }

    //查询某时间范围以内的活动，两个参数可以一个为null，如(startTime, null)表示startTime以后的所有活动
    @Override
    public ArrayList<SimpleHDActivity> getHDActivityByTime(String startTime, String endTime) {
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
//        private int hdId;            //活动的id
//        private String hdName;        //活动的名称
//        private String hdOriginName;        //活动发起者的id，
//        private int hdOriginId;
//        private double hdLongitude;    //活动的经度
//        private double hdLatitude;    //纬度
//        private int hdType;
//        private int hdStatus;
        int hdId = Integer.parseInt(soapObject.getProperty("hdId").toString());
        String hdName = soapObject.getProperty("hdName").toString();
        String hdOriginName = soapObject.getProperty("hdOriginName").toString();
        int hdOriginId = Integer.parseInt(soapObject.getProperty("hdOriginId").toString());
        double hdLongitude = Double.parseDouble(soapObject.getProperty("hdLongitude").toString());
        double hdLatitude = Double.parseDouble(soapObject.getProperty("hdLatitude").toString());
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
            resultList.add(sh);
        }
        Log.v(TAG, "get SimpleHdActivity of  " + resultList.size() );
        return  resultList;
    }

    private HDActivity parseHDActivity(SoapObject soapObject){
        return null;
    }

    //---------------------------------------------Notification
    private DisplayInvitation parseMyInvitation(SoapObject soapObject){
        String targetName = soapObject.getProperty("targetName").toString();
        int targetAvatar = Integer.parseInt(soapObject.getProperty("targetAvatar").toString());
        String activityName = soapObject.getProperty("activityName").toString();
        int responseStatus = Integer.parseInt(soapObject.getProperty("responseStatus").toString());
        return new DisplayInvitation(targetName, targetAvatar, activityName, 1);
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



    private Object makeKsoapCall(SoapObject request, String url){
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


}

