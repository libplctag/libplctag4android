package com.abmodbusmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.sun.jna.Platform;

public class MainActivity extends AppCompatActivity implements ABTaskCallbackInterface,MBTaskCallbackInterface {
    public static ABTaskCallbackInterface ABtaskCallback;
    public static MBTaskCallbackInterface MBtaskCallback;

    private static final String TAG = "Main Activity";

    AsyncTaskAB myTaskAB = null;
    AsyncTaskModbus myTaskMB = null;

    TextView tv1, tv2, tv3, tv4, tv5, tv6;
    ColorStateList textColor;
    Button btnStartAB, btnStopAB, btnStartMB, btnStopMB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);
        tv5 = findViewById(R.id.textView5);
        tv6 = findViewById(R.id.textView6);

        textColor = tv1.getTextColors();

        btnStartAB = findViewById(R.id.btnStartAB);
        btnStopAB = findViewById(R.id.btnStopAB);
        btnStartMB = findViewById(R.id.btnStartMB);
        btnStopMB = findViewById(R.id.btnStopMB);

        btnStopAB.setEnabled(false);
        btnStopMB.setEnabled(false);

        ABtaskCallback = this;
        MBtaskCallback = this;

        String osArch = "OS Arch : " + System.getProperty("os.arch");
        String platformArch = "Platform Arch : " + Platform.ARCH;

        EditText et0 = findViewById(R.id.txtOSArch);
        et0.setText(osArch);
        EditText et1 = findViewById(R.id.txtPlatformArch);
        et1.setText(platformArch);
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
        btnStartAB.setBackground(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_off));
        btnStopAB.setEnabled(true);
        btnStopAB.setBackground(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_on));

        Log.v(TAG, "AB Task is " + myTaskAB.getStatus().toString());
    }

    public void sendMessageStopAB(View v){
        if (myTaskAB != null){
            myTaskAB.cancel(true);
            myTaskAB = null;
        }

        tv1.setTextColor(textColor);
        tv1.setText("0");
        tv2.setTextColor(textColor);
        tv2.setText("0");
        tv3.setTextColor(textColor);
        tv3.setText("0");

        btnStartAB.setEnabled(true);
        btnStartAB.setBackground(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_on));
        btnStopAB.setEnabled(false);
        btnStopAB.setBackground(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_off));

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
        btnStartMB.setBackground(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_off));
        btnStopMB.setEnabled(true);
        btnStopMB.setBackground(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_on));

        Log.v(TAG, "Modbus Task is " + myTaskMB.getStatus().toString());
    }

    public void sendMessageStopMB(View v){
        if (myTaskMB != null){
            myTaskMB.cancel(true);
            myTaskMB = null;
        }

        tv4.setTextColor(textColor);
        tv4.setText("0");
        tv5.setTextColor(textColor);
        tv5.setText("0");
        tv6.setTextColor(textColor);
        tv6.setText("0");

        btnStartMB.setEnabled(true);
        btnStartMB.setBackground(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_on));
        btnStopMB.setEnabled(false);
        btnStopMB.setBackground(ContextCompat.getDrawable(this, android.R.drawable.button_onoff_indicator_off));

        Log.v(TAG, "Modbus Task is Cancelled");
    }

    public void sendMessageExit(View v){
        System.exit(-1);
    }

    @Override
    public void UpdateABUI(String val1, String val2, String val3) {
        if (val1.startsWith("err")){
            tv1.setTextColor(Color.RED);
        } else {
            tv1.setTextColor(textColor);
        }
        tv1.setText(val1);

        if (val2.startsWith("err")){
            tv2.setTextColor(Color.RED);
        } else {
            tv2.setTextColor(textColor);
        }
        tv2.setText(val2);

        if (val3.startsWith("err")){
            tv3.setTextColor(Color.RED);
        } else {
            tv3.setTextColor(textColor);
        }
        tv3.setText(val3);
    }

    @Override
    public void UpdateMBUI(String val1, String val2, String val3) {
        if (val1.startsWith("err")){
            tv4.setTextColor(Color.RED);
        } else {
            tv4.setTextColor(textColor);
        }
        tv4.setText(val1);

        if (val2.startsWith("err")){
            tv5.setTextColor(Color.RED);
        } else {
            tv5.setTextColor(textColor);
        }
        tv5.setText(val2);

        if (val3.startsWith("err")){
            tv6.setTextColor(Color.RED);
        } else {
            tv6.setTextColor(textColor);
        }
        tv6.setText(val3);
    }
}
