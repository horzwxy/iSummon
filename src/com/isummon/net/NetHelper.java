package com.isummon.net;

import com.isummon.data.GlobalVariables;
import com.isummon.model.HDActivity;
import com.isummon.model.HDType;
import com.isummon.model.LogInResultType;
import com.isummon.model.Notification;
import com.isummon.model.RegisterResultType;
import com.isummon.model.SimpleHDActivity;
import com.isummon.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public abstract class NetHelper {

    public static final int SUCCEED = 1;
    public static final int FAIL = 0;

    // WSDL文档的URL，192.168.17.156为PC的ID地址
    protected final static String serviceUrl = "http://192.168.17.156:8080/axis2/services";
    protected final static String namespace = "http://edu.fudan.10ss";

    /**
     * 可以把这里返回的NetHelper改成其他实例。
     *
     * @return
     */
    public static NetHelper getInstance() {
        return new FakeNetHelper();
    }

    public abstract ArrayList<SimpleHDActivity> getAllActs();


    //---------------------------------基本功能-----------------------------------------

    /**
     * 用户登录方法
     * 登录成功后需要将GlobalVariable中的currentUser设为有效值
     *
     * @param username 这里的username应该是邮箱
     * @param passwd   用户的密码
     * @return 返回值为已登录用户的ID，验证失败返回-1
     */
    public abstract LogInResultType login(String username, String passwd);

    /**
     * 用户注册方法
     * 从服务器返回,
     *
     * @param username 用户注册名，应使用邮箱
     * @param nickname 用户需要显示的昵称
     * @param passwd   用户设定的密码
     * @return 成功or失败
     */
    public abstract RegisterResultType register(String username, String nickname, String passwd);

    public abstract void logOut();

    /**
     * 返回当前有效的活动简介
     * SimpleHDActivity用于网络传输的轻量级HDActivity，详情见其类定义
     *
     * @return
     */
    public abstract ArrayList<SimpleHDActivity> getCurrentSimpleHDActivities();

    public abstract List<Notification> getNotifications();

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
    public abstract HDActivity getHDActivityById(int hdId);


    /**
     * 增
     * 用户添加活动
     *
     * @param hdActivity 活动详情
     * @return 添加后的活动id，添加失败返回-1
     */
    public abstract int addHDActivity(HDActivity hdActivity);

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
    public abstract boolean modifyHDActivity(HDActivity hdActivityNew);

    /**
     * 删
     * 活动发起者可以取消该活动，取消该活动之后服务端应通知参加的用户
     *
     * @param hdId
     * @return
     */
    public abstract boolean cancleHDActivity(int hdId);

    //----------------------------------一系列的查询方法-------------------------------------------

    //我发起的活动
    public abstract ArrayList<SimpleHDActivity> getHDActivityByOriginId();

    //我参加的活动
    public abstract ArrayList<SimpleHDActivity> getHDActivityByUserId();

    //根据活动名称查询，如查询活动名称带有“三国杀”的活动
    public abstract ArrayList<SimpleHDActivity> getHDActivityByHdName(String hdName);

    //根据活动标签查询，如查询“娱乐”类的活动
    public abstract ArrayList<SimpleHDActivity> getHDActivityByHdType(HDType hdType);

    //查询某时间范围以内的活动，两个参数可以一个为null，如(startTime, null)表示startTime以后的所有活动
    // wxy----------界面上没有实现这个功能
    public abstract ArrayList<SimpleHDActivity> getHDActivityByTime(String startTime, String endTime);

    //---------其他

    //----------------------------通知用户--------------------------------------------------

    /**
     * @param hdId
     * @param targets
     * @return SUCCEED OR FAIL
     */
    public abstract int invite(int hdId, ArrayList<UserModel> targets);

    /**
     * @param nickname
     * @return empty list if no result
     */
    public abstract ArrayList<UserModel> findUserByName(String nickname);

    /**
     * @param targetId
     * @return SUCCEED OR FAIL
     */
    public abstract int addContact(int targetId);

    public abstract ArrayList<UserModel> getAllContacts();

    public abstract void onReadNotification(Notification notification);

    /**
     * 判断传入的id是否是当前用户的
     * @param userId
     * @return
     */
    public abstract boolean isMyId(int userId);
}


//--------------------------------用于网络传输的简化类-------------------------------------------------

