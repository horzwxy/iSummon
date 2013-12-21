package com.isummon.net;

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
import java.util.Arrays;
import java.util.List;

/**
 * Created by horz on 12/20/13.
 */
public class RealNetHelper extends NetHelper {

    @Override
    public ArrayList<SimpleHDActivity> getAllActs() {
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
        return null;
    }


    //---------------------------------基本功能-----------------------------------------

    /**
     * 用户登录方法
     * 登录成功后需要将GlobalVariable中的currentUser设为有效值
     *
     * @param username 这里的username应该是邮箱
     * @param passwd   用户的密码
     * @return 返回值为已登录用户的ID，验证失败返回-1
     */
    public LogInResultType login(String username, String passwd) {
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
                return LogInResultType.SUCCESS;
            } else {
                return LogInResultType.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LogInResultType.SUCCESS;
    }

    /**
     * 用户注册方法
     * 从服务器返回,
     *
     *
     * @param username 用户注册名，应使用邮箱
     * @param nickname 用户需要显示的昵称
     * @param passwd   用户设定的密码
     * @return 成功or失败
     */
    public RegisterResultType register(String username, String nickname, String passwd) {
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
                return RegisterResultType.SUCCESS;
            } else {
                return RegisterResultType.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RegisterResultType.SUCCESS;
    }

    @Override
    public void logOut() {

    }

    /**
     * 返回当前有效的活动简介
     * SimpleHDActivity用于网络传输的轻量级HDActivity，详情见其类定义
     *
     * @return
     */
    @Override
    public ArrayList<SimpleHDActivity> getCurrentSimpleHDActivities() {
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

    @Override
    public List<Notification> getNotifications() {
        return null;
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

    //----------------------------------一系列的查询方法-------------------------------------------

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

    //---------其他

    //----------------------------通知用户--------------------------------------------------

    /**
     *
     * @param hdId
     * @param targets
     * @return SUCCEED OR FAIL
     */
    @Override
    public int invite(int hdId, ArrayList<UserModel> targets) {
        return 0;
    }

    /**
     *
     * @param nickname
     * @return empty list if no result
     */
    @Override
    public ArrayList<UserModel> findUserByName(String nickname) {
        return null;
    }

    /**
     *
     * @param targetId
     * @return SUCCEED OR FAIL
     */
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

    @Override
    public boolean isMyId(int userId) {
        return false;
    }
}
