package com.isummon.activity;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by horzwxy on 12/22/13.
 */
public class ISummonActivity extends Activity {

    public int getAvatarResourceId(int avatarId) {
        return getResources().getIdentifier(
                getPackageName() + ":drawable/hn"
                + (avatarId < 10 ? "0" + avatarId : avatarId + ""),
                null,
                null
        );
    }

    protected void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }
}
