package com.dannyravi.maycar;


import android.os.AsyncTask;


public class SelectTask extends AsyncTask<Void, Void, String> {

    private String mUrl;

    public SelectTask(String url){
        super();
        mUrl = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        String jsonString = JsonHttp.makeHttpRequest(mUrl);
        return jsonString;
    }

    @Override
    protected void onPostExecute(String result) {

    }
}
