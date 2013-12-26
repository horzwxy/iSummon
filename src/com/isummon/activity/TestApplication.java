package com.isummon.activity;


import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

/**
 * 按照应用需求定制的Application类，包含了使用百度地图相关的逻辑。
 */
public class TestApplication extends Application {

    private static TestApplication mInstance = null;
    public boolean m_bKeyRight = true;
    BMapManager mBMapManager = null;

    public static final String strKey = "lKKBT2kwTlFOjKPDL3NQdEut";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initEngineManager(this);
    }

    public BMapManager getBMapManager(){
        return this.mBMapManager;
    }

    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(TestApplication.getInstance().getApplicationContext(),
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
    }

    public static TestApplication getInstance() {
        return mInstance;
    }


    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(TestApplication.getInstance().getApplicationContext(), "您的网络出错啦！",
                        Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(TestApplication.getInstance().getApplicationContext(), "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                //授权Key错误：
                Toast.makeText(TestApplication.getInstance().getApplicationContext(),
                        "请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
                TestApplication.getInstance().m_bKeyRight = false;
            }
        }
    }
}