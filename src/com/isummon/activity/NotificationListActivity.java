package com.isummon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.isummon.R;
import com.isummon.activity.listmodel.NotificationRespondMode;
import com.isummon.data.GlobalVariables;
import com.isummon.model.HDActivity;
import com.isummon.model.InvitationStatus;
import com.isummon.model.Notification;
import com.isummon.widget.NotificationAdapter;
import com.isummon.widget.ProgressTaskBundle;

import java.util.List;

/**
 * 列表显示用户收到的邀请的界面
 */
public class NotificationListActivity extends ISummonActivity {

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
                return GlobalVariables.netHelper.getNotifications();
            }

            @Override
            protected void dealResult(List<Notification> result) {
                if (result.size() != 0) {
                    updateList(result);
                } else {
                    showToast(R.string.no_notification);
                }
            }
        }.action();
    }

    private void updateList(final List<Notification> notifications) {
        ListView listView = (ListView) findViewById(R.id.notification_list);
        listView.setAdapter(new NotificationAdapter(this, notifications));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                onShowOptions(notifications.get(position));
                //onShowDetails((int)id);
            }
        });
    }

    private void onShowOptions(final Notification notification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.choose_respond_mode);
        builder.setItems(NotificationRespondMode.getChns(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                NotificationRespondMode mode = NotificationRespondMode.values()[which];
                switch (mode) {
                    case LATER:
                        break;
                    case REJECT:
                        notification.setResponseStatus(InvitationStatus.REJECTED.ordinal());
                        new AsyncTask<Notification, Void, Void>() {
                            @Override
                            protected Void doInBackground(Notification... notifications) {
                                GlobalVariables.netHelper.onReadNotification(notifications[0]);
                                return null;
                            }
                        }.execute(notification);
                        break;
                    case VIEW_DETAILS:
                        notification.setResponseStatus(InvitationStatus.READ.ordinal());
                        new AsyncTask<Notification, Void, Void>() {
                            @Override
                            protected Void doInBackground(Notification... notifications) {
                                GlobalVariables.netHelper.onReadNotification(notifications[0]);
                                return null;
                            }
                        }.execute(notification);
                        onShowDetails(notification.getHdId());
                        break;
                }
            }
        });
        builder.create().show();

    }

    private void onShowDetails(int id) {
        new ProgressTaskBundle<Integer, HDActivity>(
                this,
                R.string.fetching_act
        ) {
            @Override
            protected HDActivity doWork(Integer... params) {
                return GlobalVariables.netHelper.getHDActivityById(params[0]);
            }

            @Override
            protected void dealResult(HDActivity result) {
                Intent intent = new Intent(
                        NotificationListActivity.this,
                        ShowHdDetailsActivity.class);
                intent.putExtra(ShowHdDetailsActivity.HDACTIVITY,
                        result);
                startActivity(intent);
            }
        }.action(id);
    }
}
