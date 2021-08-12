package com.example.constellation.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

public class LoadDataAsyncTask extends AsyncTask<String,Void,String> {

    Context context;
    OnGetNetDataListener listener;

    ProgressDialog dialog;

    private void initDialog() {
        dialog = new ProgressDialog(context);
        dialog.setTitle("提示信息");
        dialog.setMessage("正在加载中......");
    }

    public LoadDataAsyncTask(Context context, OnGetNetDataListener listener) {
        this.context = context;
        this.listener = listener;
        initDialog();
    }

    public interface OnGetNetDataListener {
        void onSuccess(String json);
    }

    //运行在主线程中，通常在此方法中进行控件的初始化
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
    }

    //运行在子线程中，可以在此方法中进行耗时操作等
    @Override
    protected String doInBackground(String... params) {
        String json = HttpUtils.getJSONFromNet(params[0]);
        return json;
    }

    //运行在主线程中，可以在此处得到doInBackground返回的数据，进行控件的更新
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        listener.onSuccess(s);
    }
}
