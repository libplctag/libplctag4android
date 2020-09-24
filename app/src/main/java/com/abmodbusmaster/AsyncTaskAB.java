package com.abmodbusmaster;

import android.util.Log;
import android.os.AsyncTask;
import org.libplctag.Tag;

public class AsyncTaskAB  extends AsyncTask<Void, Void, String> {

    private static final String TAG = "AB Task Activity";

    private static String tagABString = "protocol=ab_eip&gateway=192.168.1.10&plc=micrologix&elem_size=2&elem_count=3&name=N7:0";
    public String val1 = "", val2 = "", val3 = "", tempVal1 = "", tempVal2 = "", tempVal3 = "";
    int timeout = 10000;
    private Tag ABMaster = null;

    com.abmodbusmaster.ABTaskCallbackInterface ABtaskCallback = MainActivity.ABtaskCallback;

    public AsyncTaskAB() {
        Log.v(TAG,"New AB Task Created");
    }

    @Override
    protected String doInBackground(Void... params) {
        Log.v(TAG,"On doInBackground...");

        ABMaster = new Tag(tagABString, timeout);

        while (ABMaster.getStatus() == Tag.PLCTAG_STATUS_PENDING){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (!isCancelled()){
            if (ABMaster.getStatus() == Tag.PLCTAG_STATUS_OK) {
                ABMaster.read(timeout);

                tempVal1 = String.valueOf(ABMaster.getInt16(0));
                tempVal2 = String.valueOf(ABMaster.getInt16(2));
                tempVal3 = String.valueOf(ABMaster.getInt16(4));
            } else {
                tempVal1 = "err " + ABMaster.getStatus();
                tempVal2 = "err " + ABMaster.getStatus();
                tempVal3 = "err " + ABMaster.getStatus();

                ABMaster.close();

                ABMaster = new Tag(tagABString, timeout);

                while (ABMaster.getStatus() == Tag.PLCTAG_STATUS_PENDING){
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

        ABMaster.close();

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

        ABtaskCallback.UpdateABUI(val1, val2, val3);
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
