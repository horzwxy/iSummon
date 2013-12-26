package com.isummon.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.isummon.net.FakeNetHelper;

/**
 * 由于ProgressDialog和AsyncTask经常同时使用，特将其封装在这个类中。
 */
public abstract class ProgressTaskBundle<P, R> {

    private ProgressDialog progressDialog;
    private AsyncTask<?,?,?> task;

    /**
     *
     * @param context ProgressDialog的显示环境
     * @param msgId message字符串的id
     */
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

    /**
     * 显示ProgressDialog并启动AsyncTask
     * @param params 传入的参数
     */
    public final void action(P... params) {
        progressDialog.show();
        task.execute(params);
    }

    /**
     * 覆盖此函数，确定AsyncTask中应执行怎样的后台操作
     * @param params
     * @return
     */
    abstract protected R doWork(P... params);

    /**
     * 覆盖此函数，确定AsyncTask应当如何处理返回值
     * @param result
     */
    abstract protected void dealResult(R result);
}
