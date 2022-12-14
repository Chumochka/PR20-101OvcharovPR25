package com.example.pr25;

import java.util.concurrent.TimeUnit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService3 extends Service {

    final String LOG_TAG = "myLogs";

    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MyService onCreate");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "MyService onDestroy");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MyService onStartCommand");
        readFlags(flags);
        MyRun mr = new MyRun(startId);
        new Thread(mr).start();
        return START_REDELIVER_INTENT;
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    void readFlags(int flags) {
        if ((flags&START_FLAG_REDELIVERY) == START_FLAG_REDELIVERY) {
            Log.d(LOG_TAG, "START_FLAG_REDELIVERY");
            Toast.makeText(getApplicationContext(), "START_FLAG_REDELIVERY", Toast.LENGTH_SHORT).show();
        }
        if ((flags&START_FLAG_RETRY) == START_FLAG_RETRY) {
            Log.d(LOG_TAG, "START_FLAG_RETRY");
            Toast.makeText(getApplicationContext(), "START_FLAG_RETRY", Toast.LENGTH_SHORT).show();
        }
    }

    class MyRun implements Runnable {

        int startId;

        public MyRun(int startId) {
            this.startId = startId;
            Log.d(LOG_TAG, "MyRun#" + startId + " create");
            Toast.makeText(getApplicationContext(), "MyRun#" + startId + " create", Toast.LENGTH_SHORT).show();
        }

        public void run() {
            Log.d(LOG_TAG, "MyRun#" + startId + " start");
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();
        }

        void stop() {
            Toast.makeText(getApplicationContext(), "MyRun#" + startId + " end, stopSelfResult(" + startId + ") = " + stopSelfResult(startId), Toast.LENGTH_LONG).show();
        }
    }
}