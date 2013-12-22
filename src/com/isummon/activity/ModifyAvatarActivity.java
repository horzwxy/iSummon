package com.isummon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.widget.AvatarAdapter;
import com.isummon.widget.ProgressTaskBundle;

import java.util.ArrayList;

/**
 * Created by horzwxy on 12/22/13.
 */
public class ModifyAvatarActivity extends Activity {

    public final static int MODIFY_AVATAR = 757347;
    public final static String AVATAR_ID = "avatar_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_avatar);

        getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg_black);

        GridView gridView = (GridView) findViewById(R.id.avatar_gallery);
        gridView.setAdapter(new AvatarAdapter(this, getAvatarResourceIds()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // position is equivalent with avatarId
                onUpdateAvatar(position);
            }
        });
    }

    private void onUpdateAvatar(final int avatarId) {
        new ProgressTaskBundle<Integer, Boolean>(
                this,
                R.string.delivering
        ) {
            @Override
            protected Boolean doWork(Integer... params) {
                return GlobalVariables.netHelper.updateAvatar(params[0]);
            }

            @Override
            protected void dealResult(Boolean result) {
                if(result) {
                    Intent intent = new Intent();
                    intent.putExtra(AVATAR_ID, avatarId);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else {
                    Toast.makeText(
                            ModifyAvatarActivity.this,
                            "头像修改失败",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }.action(avatarId);
    }

    private ArrayList<Integer> getAvatarResourceIds() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for(int i = 1; i <= 22; i++) {
            int id = getResources().getIdentifier(
                    "com.isummon:drawable/hn" +
                            ((i < 10) ? "0" + i : i + "" ),
                    null,
                    null
            );
            result.add(id);
        }
        return result;
    }
}
