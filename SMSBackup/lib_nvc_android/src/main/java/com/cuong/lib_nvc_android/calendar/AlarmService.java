package com.cuong.lib_nvc_android.calendar;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.util.Log;

/**
 * Created by vcuon on 8/20/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class AlarmService extends Service {
    private ResultReceiver resultReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("UnmeAlarm", "Service Alarm Create");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("UnmeAlarm", "Service Alarm Start");
        Intent i = new Intent();
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("is_alert", true);
        i.setComponent(new ComponentName("com.cuong.calendaralert", "com.cuong.calendaralert.AlertActivity"));
        startActivity(i);
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("UnmeAlarm", "Service Alarm OnBind");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("UnmeAlarm", "Service Alarm OnDestroy");
    }
}
