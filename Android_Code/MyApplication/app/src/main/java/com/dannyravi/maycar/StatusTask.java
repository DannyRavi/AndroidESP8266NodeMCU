package com.dannyravi.maycar;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * Created by dzulkiflee on 3/19/2017 AD.
 */

public class StatusTask extends AsyncTask<Void, Void, String> {

    private String mUrl;

    OnDataSendToActivity dataSendToActivity;

    public StatusTask(String url, Activity activity){
        dataSendToActivity = (OnDataSendToActivity)activity;
        mUrl = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        String jsonString = JsonHttp.makeHttpRequest(mUrl);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        dataSendToActivity.sendData(result);
    }
}