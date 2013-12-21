package com.isummon.net;

import com.isummon.model.HDActivity;
import com.isummon.model.HDType;
import com.isummon.model.Invitation;
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

public class FormalNetHelper extends NetHelper {

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
     * wxy-----我乱填了些返回值
     *
     * @param username 这里的username应该是邮箱
     * @param passwd   用户的密码
     * @return 返回值为已登录用户的ID，验证失败返回-1
     */
    @Override
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
                return LogInResultType.FAIL_TIMEOUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LogInResultType.FAIL_TIMEOUT;
    }

    /**
     * 用户注册方法
     * 从服务器返回,
     *
     * wxy-----我乱填了些返回值
     *
     * @param username 用户注册名，应使用邮箱
     * @param nickname 用户需要显示的昵称
     * @param passwd   用户设定的密码
     * @return 成功or失败
     */
    @Override
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
                return RegisterResultType.FAIL_TIME_OUT;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RegisterResultType.FAIL_TIME_OUT;
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
