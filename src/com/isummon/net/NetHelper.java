package com.isummon.net;

import com.isummon.model.HDActivity;
import com.isummon.model.Invitation;
import com.isummon.model.Notification;
import com.isummon.model.SimpleHDActivity;
import com.isummon.model.UserModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class NetHelper {
    // WSDL文档的URL，192.168.17.156为PC的ID地址
    private final static String serviceUrl = "http://192.168.17.156:8080/axis2/services";
    private final static String namespace = "http://edu.fudan.10ss";

    public static void getAllActs() {
        // 定义调用的WebService方法名
        String methodName = "getAllActs";
        // 第1步：创建SoapObject对象，并指定WebService的命名空间和调用的方法名
        SoapObject request = new SoapObject(namespace, methodName);
        // 第2步：设置WebService方法的参数
        request.addProperty("testArg", "test");
        // 第3步：创建SoapSerializationEnvelope对象，并指定WebService的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // 设置bodyOut属性
        envelope.bodyOut = request;

        // 第4步：创建HttpTransportSE对象，并指定WSDL文档的URL
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);
        try {
            // 第5步：调用WebService
            ht.call(null, envelope);
            if (envelope.getResponse() != null) {
                // 第6步：使用getResponse方法获得WebService方法的返回结果
                SoapObject soapObject = (SoapObject) envelope.getResponse();
                // 通过getProperty方法获得Product对象的属性值
                String result = "产品名称：" + soapObject.getProperty("name") + "\n";
                result += "产品数量：" + soapObject.getProperty("productNumber")
                        + "\n";
                result += "产品价格：" + soapObject.getProperty("price");
                //blabalbla

            } else {
                //blabla
            }
        } catch (Exception e) {
            //blalalb
        }
    }


    //---------------------------------基本功能-----------------------------------------

    /**
     * 用户登录方法
     *
     * @param username 这里的username应该是邮箱
     * @param passwd   用户的密码
     * @return 返回值为已登录用户的ID，验证失败返回-1
     */
    public static int login(String username, String passwd) {
        String methodName = "login";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("username", username);
        request.addProperty("passwd", passwd);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = request;
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);
        try {
            // 第5步：调用WebService
            ht.call(null, envelope);
            if (envelope.getResponse() != null) {
                SoapObject soapObject = (SoapObject) envelope.getResponse();
                return Integer.parseInt(soapObject.getPropertyAsString(0));
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 用户注册方法
     *
     * @param username 用户注册名，应使用邮箱
     * @param nickname 用户需要显示的昵称
     * @param passwd   用户设定的密码
     * @return 成功or失败
     */
    public static boolean register(String username, String nickname, String passwd) {
        String methodName = "register";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("username", username);
        request.addProperty("nickname", nickname);
        request.addProperty("passwd", passwd);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = request;
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);
        try {
            // 第5步：调用WebService
            ht.call(null, envelope);
            if (envelope.getResponse() != null) {
                SoapObject soapObject = (SoapObject) envelope.getResponse();
                return Boolean.parseBoolean(soapObject.getPropertyAsString(0));
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 返回当前有效的活动简介
     * SimpleHDActivity用于网络传输的轻量级HDActivity，详情见其类定义
     *
     * @return
     */
    public static ArrayList<SimpleHDActivity> getCurrentSimpleHDActivities() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<SimpleHDActivity> retList = new ArrayList<SimpleHDActivity>();
        retList.add(new SimpleHDActivity("Test SimpleHD1", 31.195 * 1E6, 121.604 * 1E6));
        retList.add(new SimpleHDActivity("Test SimpleHD2", 31.196 * 1E6, 121.604 * 1E6));
        retList.add(new SimpleHDActivity("Test SimpleHD3", 31.197 * 1E6, 121.604 * 1E6));
        return retList;
    }

    public static ArrayList<SimpleHDActivity> getSimpleHdByIdList(ArrayList<Integer> idList){
        return null;
    }

    public static List<Notification> getNotifications() {
        return FakeDataProvider.getNotifications();
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
    public static HDActivity getHDActivityById(int hdId) {
        return FakeDataProvider.getHDById(hdId);
    }



    /**
     * 增
     * 用户添加活动
     *
     * @param userId     活动发起者id
     * @param hdActivity 活动详情
     * @return 添加后的活动id，添加失败返回-1
     */
    public static int addHDActivity(int userId, HDActivity hdActivity) {
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
    public static boolean modifyHDActivity(HDActivity hdActivityNew) {
        return false;
    }

    /**
     * 删
     * 活动发起者可以取消该活动，取消该活动之后服务端应通知参加的用户
     *
     * @param hdId
     * @return
     */
    public static boolean cancleHDActivity(int hdId) {
        return false;
    }

    //----------------------------------一系列的查询方法-------------------------------------------

    //我发起的活动
    public static ArrayList<SimpleHDActivity> getHDActivityByOriginId(int userId) {
        return null;
    }

    //我参加的活动
    public static ArrayList<SimpleHDActivity> getHDActivityByUserId(int userId) {
        return null;
    }

    //查询某个人（使用昵称）发起的活动
    //[注意：？？？是否提供查询某个人参加的活动？？？隐私考虑的话应不提供接口]
    public static ArrayList<SimpleHDActivity> getHDActivityByOriginerName(String originerName) {
        return null;
    }

    //根据活动名称查询，如查询活动名称带有“三国杀”的活动
    public static ArrayList<SimpleHDActivity> getHDActivityByHdName(String hdName) {
        return null;
    }

    //根据活动标签查询，如查询“娱乐”类的活动
    public static ArrayList<SimpleHDActivity> getHDActivityByHdTag(String hdTag) {
        return null;
    }

    //查询某地点附近的活动，如“二教”附近的活动
    public static ArrayList<SimpleHDActivity> getHDActivityByAddress(String hdAddress) {
        return null;
    }

    //查询某时间范围以内的活动，两个参数可以一个为null，如(startTime, null)表示startTime以后的所有活动
    public static ArrayList<SimpleHDActivity> getHDActivityByTime(String startTime, String endTime) {
        return null;
    }

    //---------其他

    //----------------------------通知用户--------------------------------------------------
    public static void invite(int hdId, ArrayList<Integer> targerIds) {

    }

    /**
     *
     * @param nickname
     * @return null if no result
     */
    public static UserModel findUserByName(String nickname) {
        return FakeDataProvider.findUserByName(nickname);
    }

    public static void addContact(int targetId) {

    }

    public static void removeContact(int targetId) {

    }

    public static ArrayList<UserModel> getAllContacts() {
        return FakeDataProvider.getContacts();
    }
}


//--------------------------------用于网络传输的简化类-------------------------------------------------

