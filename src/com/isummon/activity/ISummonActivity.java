package com.isummon.activity;

import android.app.Activity;
import android.widget.Toast;

/**
 * 提供了各个Activity共享使用的方法。
 */
public class ISummonActivity extends Activity {

    /**
     * 通过头像id获取头像图片在系统中的Resource id。
     * @param avatarId 头像id，取值1-22
     * @return 返回resource id
     */
    public int getAvatarResourceId(int avatarId) {
        return getResources().getIdentifier(
                getPackageName() + ":drawable/hn"
                + (avatarId < 10 ? "0" + avatarId : avatarId + ""),
                null,
                null
        );
    }

    /**
     * 显示包含id所指代的字符串的Toast。
     * @param stringId
     */
    protected void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }
}
