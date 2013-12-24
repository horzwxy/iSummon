package com.isummon.net;

import com.isummon.model.DisplayInvitation;
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
    protected final static String serviceUrl = "http://10.131.251.146:8080/iSummon/services";
    protected final static String namespace = "http://edu.fudan.10ss";

    public static NetHelper getInstance() {
//        return new FakeNetHelper();
        return new RealNetHelper();
    }

    public abstract ArrayList<SimpleHDActivity> getAllActs();


    //---------------------------------User Action------------------------------------------------------------------------------

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
     *
     * @param newUser
     * @return 成功or失败
     */
    public abstract RegisterResultType register(UserModel newUser);

    public abstract void logOut();

    public abstract  UserModel getUserById(int userId);

    /**
     *
     * @param nickname
     * @return empty list if no result
     */
    public abstract ArrayList<UserModel> findUserByName(String nickname);

    /**
     *
     * @param targetId
     * @return 0 if normal, -1 if unexpected excepton , -2 if already been contacts
     */
    public abstract int addContact(int targetId);

    public abstract ArrayList<UserModel> getAllContacts();


    public abstract boolean isMyId(int userId);

    public abstract boolean updateAvatar(int avatarId);

    /**
     *
     * @param targetId
     * @return 0 if normal, -1 if unexpected excepton , -2 if no such contacts
     */
    public abstract int removeContact(int targetId);

    /*-----------------------------------Notification Action--------------------------------------------------------------------------------*/

    public abstract List<Notification> getNotifications();


    /**
     *
     * @param hdId
     * @param targets
     * @return SUCCEED (0) OR FAIL(-1)
     */
    public abstract int invite(int hdId, ArrayList<UserModel> targets);



    public abstract void onReadNotification(Notification notification);

    //---查看我发出的邀请
    public abstract ArrayList<DisplayInvitation> getMyInvitations();


    /*--------------------------------------------Activity Action--------------------------------------------------------------*/

    /**
     * 返回当前有效的活动简介
     * SimpleHDActivity用于网络传输的轻量级HDActivity，详情见其类定义
     *
     * @return
     */
    public abstract ArrayList<SimpleHDActivity> getCurrentSimpleHDActivities();



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

    public abstract boolean applyHDActivity(int hdId);

    //----------------------------------一系列的查询方法-----

    //我发起的活动
    public abstract ArrayList<SimpleHDActivity> getHDActivityByOriginId();

    //我参加的活动
    public abstract ArrayList<SimpleHDActivity> getHDActivityByUserId();

    //根据活动名称查询，如查询活动名称带有“三国杀”的活动
    public abstract ArrayList<SimpleHDActivity> getHDActivityByHdName(String hdName);

    //根据活动标签查询，如查询“娱乐”类的活动
    public abstract ArrayList<SimpleHDActivity> getHDActivityByHdType(HDType hdType);

    //查询某时间范围以内的活动，两个参数可以一个为null，如(startTime, null)表示startTime以后的所有活动
    public abstract ArrayList<SimpleHDActivity> getHDActivityByTime(String startTime, String endTime);

    public abstract ArrayList<SimpleHDActivity> getHDActivityByOriginName(String originName);

    public abstract ArrayList<SimpleHDActivity> getHDActivityByAddress(String address);






}

