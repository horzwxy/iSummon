package com.isummon.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * Created by horzwxy on 12/20/13.
 */
public class TaskProgressDialog extends ProgressDialog {

    private AsyncTask<?,?,?> task;

    public TaskProgressDialog(Context context, int msgStringId) {
        super(context);

        setMessage(context.getString(msgStringId));
        setCancelable(true);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                task.cancel(true);
            }
        });
    }

    public AsyncTask<?, ?, ?> getTask() {
        return task;
    }

    public void setTask(AsyncTask<?, ?, ?> task) {
        this.task = task;
    }
}
