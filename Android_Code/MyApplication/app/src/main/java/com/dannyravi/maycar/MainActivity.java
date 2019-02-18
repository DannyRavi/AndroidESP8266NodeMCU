package com.dannyravi.maycar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnDataSendToActivity {

    ImageView bg_state;
    Button btn_en, btn_do, btn_hed, btn_tai;
    TextView txt_network;
    String url = "http://192.168.0.106/"; //Define your NodeMCU IP Address here Ex: http://192.168.1.4/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bg_state = findViewById(R.id.bg_status);
        txt_network = findViewById(R.id.txt_network);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isNetworkAvailable()){
                    bg_state.setImageResource(R.drawable.background_on);
                    txt_network.setText("");
                }else{
                    bg_state.setImageResource(R.drawable.background);
                    txt_network.setText("Cound not connect to the server");
                }

                updateStatus();
                handler.postDelayed(this, 2000);
            }
        }, 5000);  //the time is in miliseconds


        btn_en = findViewById(R.id.Engine);
        btn_do = findViewById(R.id.Doors);
        btn_hed = findViewById(R.id.HeadLight);
        btn_tai = findViewById(R.id.Taillight);

        btn_en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"engine";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });

        btn_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"doors";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });
        btn_hed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"head_light";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });
        btn_tai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"tail_light";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void sendData(String str) {
        updateButtonStatus(str);
    }

    private void updateStatus(){
        String url_rl = url+"status";
        StatusTask task = new StatusTask(url_rl, this);
        task.execute();
    }

    //Function for updating Button Status
    private void updateButtonStatus(String jsonStrings){
        try {
            JSONObject json = new JSONObject(jsonStrings);

            String room_light = json.getString("rl");
            String mirror_light = json.getString("ml");
            String bed_light = json.getString("bl");
            String fan = json.getString("fan");


            if(room_light.equals("1")){
                btn_en.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_en.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }
            if(mirror_light.equals("1")){
                btn_do.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_do.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }
            if(bed_light.equals("1")){
                btn_hed.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_hed.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }
            if(fan.equals("1")){
                btn_tai.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_tai.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {


       System.exit(0);
        finish();
    }
}
//room_light
//mirror_light
//bed_light
//fan