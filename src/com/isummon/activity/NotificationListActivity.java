package com.isummon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.isummon.widget.TaskProgressDialog;

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

        final TaskProgressDialog progressDialog = new TaskProgressDialog(
                this,
                R.string.fetching_notifications
        );
        AsyncTask<Void, Void, List<Notification>> fetchTask = new AsyncTask<Void, Void, List<Notification>>() {
            @Override
            protected List<Notification> doInBackground(Void... params) {
                return NetHelper.getNotifications();
            }

            @Override
            protected void onPostExecute(List<Notification> notifications) {
                progressDialog.dismiss();
                if (notifications.size() != 0) {
                    updateList(notifications);
                } else {
                    Toast.makeText(NotificationListActivity.this,
                            R.string.no_notification,
                            Toast.LENGTH_SHORT).show();
                }
            }
        };
        progressDialog.setTask(fetchTask);
        progressDialog.show();
        fetchTask.execute();
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
        TaskProgressDialog progressDialog = new TaskProgressDialog(
                this,
                R.string.fetching_act);
        AsyncTask<Integer, Void, HDActivity> fetchTask =
                new AsyncTask<Integer, Void, HDActivity>() {
                    @Override
                    protected HDActivity doInBackground(Integer... params) {
                        return NetHelper.getHDActivityById(params[0]);
                    }

                    @Override
                    protected void onPostExecute(HDActivity hdActivity) {
                        Intent intent = new Intent(
                                NotificationListActivity.this,
                                ShowHdDetailActivity.class);
                        intent.putExtra(ShowHdDetailActivity.HDACTIVITY,
                                hdActivity);
                        startActivity(intent);
                    }
                };
        progressDialog.setTask(fetchTask);
        fetchTask.execute(id);
    }
}
