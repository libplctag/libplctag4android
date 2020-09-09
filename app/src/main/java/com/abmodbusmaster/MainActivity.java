package com.abmodbusmaster;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.sun.jna.Native;
import com.sun.jna.Platform;

public class MainActivity extends AppCompatActivity implements ABTaskCallbackInterface.ABTaskCallback,MBTaskCallbackInterface.MBTaskCallback {
    public static ABTaskCallbackInterface.ABTaskCallback ABtaskCallback;
    public static MBTaskCallbackInterface.MBTaskCallback MBtaskCallback;

    private static final String TAG = "Main Activity";

    AsyncTaskAB myTaskAB = null;
    AsyncTaskModbus myTaskMB = null;

    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    Button btnStartAB, btnStopAB, btnStartMB, btnStopMB;

//    static {
//        Native.register(Tag.class, "plctag");
//        Log.v(TAG, "Library plctag registered!");
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = (TextView)findViewById(R.id.textView1);
        tv2 = (TextView)findViewById(R.id.textView2);
        tv3 = (TextView)findViewById(R.id.textView3);
        tv4 = (TextView)findViewById(R.id.textView4);
        tv5 = (TextView)findViewById(R.id.textView5);
        tv6 = (TextView)findViewById(R.id.textView6);

        btnStartAB = (Button)findViewById(R.id.btnStartAB);
        btnStopAB = (Button)findViewById(R.id.btnStopAB);
        btnStartMB = (Button)findViewById(R.id.btnStartMB);
        btnStopMB = (Button)findViewById(R.id.btnStopMB);

        btnStopAB.setEnabled(false);
        btnStopMB.setEnabled(false);

        ABtaskCallback = this;
        MBtaskCallback = this;

        EditText et0 = (EditText)findViewById(R.id.txtOSArch);
        et0.setText("OS Arch : " + System.getProperty("os.arch"));
        EditText et1 = (EditText)findViewById(R.id.txtPlatformArch);
        et1.setText("Platform Arch : " + Platform.ARCH.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (myTaskAB != null){
            myTaskAB.cancel(true);
            myTaskAB = null;
        }

        if (myTaskMB != null){
            myTaskMB.cancel(true);
            myTaskMB = null;
        }
    }

    public void sendMessageStartAB(View v){
        if (myTaskAB == null) {
            myTaskAB = new AsyncTaskAB();
        } else {
            return;
        }

        myTaskAB.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        btnStartAB.setEnabled(false);
        btnStartAB.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_off));
        btnStopAB.setEnabled(true);
        btnStopAB.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_on));

        Log.v(TAG, "AB Task is " + myTaskAB.getStatus().toString());
    }

    public void sendMessageStopAB(View v){
        if (myTaskAB != null){
            myTaskAB.cancel(true);
            myTaskAB = null;
        }

        tv1.setText("0");
        tv2.setText("0");
        tv3.setText("0");

        btnStartAB.setEnabled(true);
        btnStartAB.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_on));
        btnStopAB.setEnabled(false);
        btnStopAB.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_off));

        Log.v(TAG, "AB Task is Cancelled");
    }

    public void sendMessageStartMB(View v) {
        if (myTaskMB == null) {
            myTaskMB = new AsyncTaskModbus();
        } else {
            return;
        }

        myTaskMB.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        btnStartMB.setEnabled(false);
        btnStartMB.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_off));
        btnStopMB.setEnabled(true);
        btnStopMB.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_on));

        Log.v(TAG, "Modbus Task is " + myTaskMB.getStatus().toString());
    }

    public void sendMessageStopMB(View v){
        if (myTaskMB != null){
            myTaskMB.cancel(true);
            myTaskMB = null;
        }

        tv4.setText("0");
        tv5.setText("0");
        tv6.setText("0");

        btnStartMB.setEnabled(true);
        btnStartMB.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_on));
        btnStopMB.setEnabled(false);
        btnStopMB.setBackground(getResources().getDrawable(android.R.drawable.button_onoff_indicator_off));

        Log.v(TAG, "Modbus Task is Cancelled");
    }

    public void sendMessageExit(View v){
        System.exit(-1);
    }

    @Override
    public void UpdateABUI(String val1, String val2, String val3) {
        tv1.setText(val1);
        tv2.setText(val2);
        tv3.setText(val3);
    }

    @Override
    public void UpdateMBUI(String val1, String val2, String val3) {
        tv4.setText(val1);
        tv5.setText(val2);
        tv6.setText(val3);
    }
}
