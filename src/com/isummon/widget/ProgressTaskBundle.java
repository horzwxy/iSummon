package com.isummon.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.isummon.net.FakeNetHelper;

/**
 * Created by horzwxy on 12/20/13.
 */
public abstract class ProgressTaskBundle<P, R> {

    private ProgressDialog progressDialog;
    private AsyncTask<?,?,?> task;

    public ProgressTaskBundle(Context context, int msgId) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(msgId));
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                task.cancel(true);
            }
        });

        task = new AsyncTask<P, Void, R>() {
            @Override
            protected R doInBackground(P... params) {
                FakeNetHelper.fakeBlock();
                return doWork(params);
            }

            @Override
            protected void onPostExecute(R r) {
                progressDialog.dismiss();
                dealResult(r);
            }

            @Override
            protected void onCancelled() {
                progressDialog.dismiss();
            }
        };
    }

    public final void action(P... params) {
        progressDialog.show();
        task.execute(params);
    }

    abstract protected R doWork(P... params);

    abstract protected void dealResult(R result);
}
