package com.cuong.lib_nvc_android.calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.Log;

import com.cuong.lib_nvc_android.R;
import com.cuong.lib_nvc_android.view.CustomDialog;

import java.util.Calendar;

/**
 * Created by unmer_000 on 27/06/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class UnmeAlarm {
    private Context context;
    private CustomDialog dialogAlarm;
    private MediaPlayer playerAlarm;
    private boolean enableSound = false;
    private PendingIntent alarmIntent;
    private AlarmManager alarm;

    /**
     * TODO: How to use
     * 1) Create a UnmeAlarm with dialog: UnmeAlarm(CustomDialog dialog)
     * 2) Create a UnmeAlarm with dialog and media: UnmeAlarm(CustomDialog dialog, MediaPlayer media)
     */

    /**
     * This constructor does not support
     * @param context
     */
    private UnmeAlarm(Context context) {
        this.context = context;
    }

    /**
     * Create a UnmeAlarm with a dialog custom
     *
     * @param dialog Dialog will show when alarm
     */
    public UnmeAlarm(CustomDialog dialog) {
        this.context = dialog.getContext();
        this.dialogAlarm = dialog;
        playerAlarm = MediaPlayer.create(context, R.raw.alarm);
    }

    /**
     * Create a UnmeAlarm with a dialog and a media
     *
     * @param dialog Dialog will show when alarm
     * @param media  Media (sound will be play when alarm)
     */
    public UnmeAlarm(CustomDialog dialog, MediaPlayer media) {
        this.context = dialog.getContext();
        this.dialogAlarm = dialog;
        this.playerAlarm = media;
    }

    /**
     * @param timeMilliSeconds alarm will alert after timeMilliSeconds
     */
    public void alarmAfter(long timeMilliSeconds) {
        initAlarmManager((int)(timeMilliSeconds+System.currentTimeMillis()));
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + timeMilliSeconds, 0, alarmIntent);
    }

    /**
     * @param timeMilliSeconds alarm will alert at this time
     */
    public void alarmAt(long timeMilliSeconds) {
        Log.e("UnmeAlarm", "Alarm set at: " + timeMilliSeconds);

        initAlarmManager((int)timeMilliSeconds);
        long elapsedTime = timeMilliSeconds - System.currentTimeMillis();
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + elapsedTime, 0, alarmIntent);
    }

    /**
     * @param year  ex 2015
     * @param month Month of year (1 - 12)
     * @param day   Day of month (1 - 31)
     * @param hour  Hour of day (0 - 23)
     * @param min   Minute of hour (0 - 59)
     *              <p>
     *              Device will alert at this time
     *              </p>
     */
    public void alarmAt(int year, int month, int day, int hour, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, hour, min, 0);
        if (cal.getTimeInMillis() < System.currentTimeMillis()){
            return;
        }
        alarmAt(cal.getTimeInMillis());
    }

    public void stopAlarm() {
        alarm.cancel(alarmIntent);
    }

    private void initAlarmManager(int id) {
        Intent intent = new Intent(context, AlarmService.class);
//        alarmIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmIntent = PendingIntent.getService(context, id, intent, PendingIntent.FLAG_ONE_SHOT);
        alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public boolean isEnableSound() {
        return enableSound;
    }

    public void setEnableSound(boolean enableSound) {
        this.enableSound = enableSound;
    }
}
