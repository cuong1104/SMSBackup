package com.cuong.smsbackup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.provider.Telephony;
import android.provider.Telephony.Sms.Intents;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    /**
     * Check if the device runs Android 4.3 (JB MR2) or higher.
     */
    public static boolean hasJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * Check if the device runs Android 4.4 (KitKat) or higher.
     */
    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= VERSION_CODES.KITKAT;
    }

    /**
     * Check if your app is the default system SMS app.
     * @param context The Context
     * @return True if it is default, False otherwise. Pre-KitKat will always return True.
     */
    public static boolean isDefaultSmsApp(Context context) {
        if (hasKitKat()) {
            return context.getPackageName().equals(Telephony.Sms.getDefaultSmsPackage(context));
        }

        return true;
    }

    /**
     * Trigger the intent to open the system dialog that asks the user to change the default
     * SMS app.
     * @param context The Context
     */
    public static void setDefaultSmsApp(Context context) {
        // This is a new intent which only exists on KitKat
        if (hasKitKat()) {
            Intent intent = new Intent(Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Intents.EXTRA_PACKAGE_NAME, context.getPackageName());
            context.startActivity(intent);
        }
    }

    public static String getDate(long dateTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        Date date = new Date(dateTime);
        return sdf.format(date);
    }

    public static void setDefaultSave(Context context, String path){
        SharedPreferences pref = context.getSharedPreferences("default_sms", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString("save_path", path);
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        edit.commit();
    }

    public static String getDefaultSave(Context context){
        SharedPreferences pref = context.getSharedPreferences("default_sms", Context.MODE_PRIVATE);
        return pref.getString("save_path", getExternalPath());
    }

    public static String getExternalPath(){
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile()+ File.separator + "SMSBackup";
        File file = new File(path);
        if (!file.exists()){
            file.mkdir();
        }
        return path;
    }
}
