package com.isummon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.isummon.R;
import com.isummon.model.HDActivity;
import com.isummon.model.Notification;
import com.isummon.net.NetHelper;
import com.isummon.widget.NotificationAdapter;
import com.isummon.widget.ProgressTaskBundle;

import java.util.List;

/**
 * Created by horzwxy on 12/20/13.
 */
public class NotificationListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new ProgressTaskBundle<Void, List<Notification>>(
                this,
                R.string.fetching_notifications
        ) {
            @Override
            protected List<Notification> doWork(Void... params) {
                return NetHelper.getNotifications();
            }

            @Override
            protected void dealResult(List<Notification> result) {
                if (result.size() != 0) {
                    updateList(result);
                } else {
                    Toast.makeText(NotificationListActivity.this,
                            R.string.no_notification,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }.action();
    }

    private void updateList(List<Notification> notifications) {
        ListView listView = (ListView) findViewById(R.id.notification_list);
        listView.setAdapter(new NotificationAdapter(this, notifications));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onShowDetails((int)id);
            }
        });
    }

    private void onShowDetails(int id) {
        new ProgressTaskBundle<Integer, HDActivity>(
                this,
                R.string.fetching_act
        ) {
            @Override
            protected HDActivity doWork(Integer... params) {
                return NetHelper.getHDActivityById(params[0]);
            }

            @Override
            protected void dealResult(HDActivity result) {
                Intent intent = new Intent(
                        NotificationListActivity.this,
                        ShowHdDetailActivity.class);
                intent.putExtra(ShowHdDetailActivity.HDACTIVITY,
                        result);
                startActivity(intent);
            }
        }.action(id);
    }
}
