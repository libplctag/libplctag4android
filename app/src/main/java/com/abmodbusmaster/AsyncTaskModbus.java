package com.abmodbusmaster;

import android.util.Log;
import android.os.AsyncTask;
import org.libplctag.Tag;

public class AsyncTaskModbus  extends AsyncTask<Void, Void, String> {

    private static final String TAG = "Modbus Task Activity";

    private static String tagModbusString = "protocol=modbus_tcp&gateway=192.168.1.20&path=1&elem_size=2&elem_count=3&name=hr0";
    public String val1 = "", val2 = "", val3 = "", tempVal1 = "", tempVal2 = "", tempVal3 = "";
    int timeout = 10000;
    private Tag MBMaster = null;

    MBTaskCallbackInterface MBtaskCallback = MainActivity.MBtaskCallback;

    public AsyncTaskModbus() {
        Log.v(TAG,"New Modbus Task Created");
    }

    @Override
    protected String doInBackground(Void... params) {
        Log.v(TAG,"On doInBackground...");

        MBMaster = new Tag(tagModbusString, timeout);

        while (MBMaster.getStatus() == Tag.PLCTAG_STATUS_PENDING){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (!isCancelled()){
            if (MBMaster.getStatus() == Tag.PLCTAG_STATUS_OK) {
                MBMaster.read(timeout);

                tempVal1 = String.valueOf(MBMaster.getInt16(0));
                tempVal2 = String.valueOf(MBMaster.getInt16(2));
                tempVal3 = String.valueOf(MBMaster.getInt16(4));
            } else {
                tempVal1 = "err " + MBMaster.getStatus();
                tempVal2 = "err " + MBMaster.getStatus();
                tempVal3 = "err " + MBMaster.getStatus();

                MBMaster.close();

                MBMaster = new Tag(tagModbusString, timeout);

                while (MBMaster.getStatus() == Tag.PLCTAG_STATUS_PENDING){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // If either value has changed then update them all and publish progress on UI thread
            if (!val1.equals(tempVal1) || !val2.equals(tempVal2) || !val3.equals(tempVal3)){
                val1 = tempVal1;
                val2 = tempVal2;
                val3 = tempVal3;

                publishProgress();
            }

            // Optional routine to slow down the communication
            //try {
            //    Thread.sleep(100);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }

        MBMaster.close();

        Log.v(TAG,"doInBackground Finished");

        return "FINISHED";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.v(TAG,"On PreExecute...");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate();

        MBtaskCallback.UpdateMBUI(val1, val2, val3);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.v(TAG,"On PostExecute...");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.v(TAG,"On Cancelled...");
    }
}
