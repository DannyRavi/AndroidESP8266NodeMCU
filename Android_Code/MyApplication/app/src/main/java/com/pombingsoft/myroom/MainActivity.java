package com.pombingsoft.myroom;

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
    Button btn_rl, btn_mr, btn_bed, btn_fan;
    TextView txt_network;
    String url = "http://192.168.x.x/"; //Define your NodeMCU IP Address here Ex: http://192.168.1.4/

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


        btn_rl = findViewById(R.id.room);
        btn_mr = findViewById(R.id.mirror);
        btn_bed = findViewById(R.id.bed);
        btn_fan = findViewById(R.id.fan);

        btn_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"room_light";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });

        btn_mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"mirror_light";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });
        btn_bed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"bed_light";
                SelectTask task = new SelectTask(url_rl);
                task.execute();
                updateStatus();
            }
        });
        btn_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url_rl = url+"fan";
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
                btn_rl.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_rl.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }
            if(mirror_light.equals("1")){
                btn_mr.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_mr.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }
            if(bed_light.equals("1")){
                btn_bed.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_bed.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }
            if(fan.equals("1")){
                btn_fan.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_on);
            }else{
                btn_fan.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.power_off);
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
