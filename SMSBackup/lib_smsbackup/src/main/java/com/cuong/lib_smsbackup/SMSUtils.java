package com.cuong.lib_smsbackup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.cuong.lib_nvc_android.util.DataUtils;
import com.cuong.lib_nvc_android.util.DeviceUtils;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by vcuon on 9/24/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class SMSUtils {
    public static ArrayList<SMSEntity> getAllSmsBefore(Activity activity, long time){
        ArrayList<SMSEntity> listSms = getAllSms(activity);
        if (listSms == null || listSms.size() <= 0){
            return listSms;
        }
        for (int i = 0; i < listSms.size(); i++){
            if (DataUtils.GetLong(listSms.get(i).getDate()) < time){
                listSms.remove(i);
                i--;
            }
        }
        return listSms;
    }

    public static ArrayList<SMSEntity> getAllSms(Activity activity) {
        ArrayList<SMSEntity> result = null;
        Uri uri = Uri.parse("content://sms");
//        String[] columns = new String[]{"_id", "thread_id", "address", "person", "date", "date_sent", "protocol", "read", "status", "type", "reply_path_present", "subject", "body", "service_center", "locked", "error_code", "seen", "si_or_id", "group_id", "imsi", "block", "spam", "sub_id", "phone_id", "creator"};
        String[] columns = new String[]{"_id", "address", "date", "date_sent", "read", "status", "type", "body", "seen"};
        Cursor cur = activity.getContentResolver().query(
                uri,null, null, null, null);
        if (cur != null && cur.getCount() > 0) {
            result = new ArrayList<SMSEntity>();
            String count = Integer.toString(cur.getCount());
            Log.d("CountSMS", count);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                try {
                    SMSEntity sms = new SMSEntity();
                    int i = 0;
                    sms.set_id(cur.getString(cur.getColumnIndex(columns[i++])));
                    sms.setAddress(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setPerson(cur.getString(cur.getColumnIndex(columns[i++])));
                    sms.setDate(cur.getString(cur.getColumnIndex(columns[i++])));
                    sms.setDate_sent(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setProtocol(cur.getString(cur.getColumnIndex(columns[i++])));
                    sms.setRead(cur.getString(cur.getColumnIndex(columns[i++])));
                    sms.setStatus(cur.getString(cur.getColumnIndex(columns[i++])));
                    sms.setType(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setReply_path_present(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setSubject(cur.getString(cur.getColumnIndex(columns[i++])));
                    sms.setBody(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setService_center(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setLocked(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setError_code(cur.getString(cur.getColumnIndex(columns[i++])));
                    sms.setSeen(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setSi_or_id(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setGroup_id(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setImsi(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setBlock(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setSpam(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setSub_id(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setPhone_id(cur.getString(cur.getColumnIndex(columns[i++])));
//                    sms.setCreator(cur.getString(cur.getColumnIndex(columns[i++])));
                    result.add(sms);
                }catch (Exception e){
                    e.printStackTrace();
                }
                cur.moveToNext();
//                Log.e("SMS", sms.toString());
            }
        }
        if (result != null && result.size() > 1){
            Collections.sort(result, new Comparator<SMSEntity>() {
                @Override
                public int compare(SMSEntity sms1, SMSEntity sms2) {
                    long time1 = DataUtils.GetLong(sms1.getDate());
                    long time2 = DataUtils.GetLong(sms2.getDate());
                    if (time1 > time2){
                        return 1;
                    } else if (time2 > time1){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }
        return result;
    }


    public static void cleanSMS(final Activity context, final Button btnClearSMS){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (btnClearSMS != null) {
                    btnClearSMS.setEnabled(false);
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    Uri uri = Uri.parse("content://sms/");
                    context.getContentResolver().delete(uri, null, null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (btnClearSMS != null) {
                    btnClearSMS.setEnabled(true);
                }
                DeviceUtils.showMessage(context, "All SMS cleared");
            }
        }.execute();
    }

    public static void RestoreSMS(Context context, SMSEntity sms) {
        // ContentProvider
        ContentValues values = new ContentValues();
        values.put("address", sms.getAddress());
        values.put("date", sms.getDate());
        values.put("date_sent", sms.getDate_sent());
        values.put("read", sms.getRead());
        values.put("status", sms.getStatus());
        String body = StringEscapeUtils.unescapeHtml4(sms.getBody());
        values.put("body", body);
        values.put("seen", sms.getSeen());
        values.put("type", sms.getType());
        context.getContentResolver().insert(Uri.parse("content://sms/sent"), values);
    }
}
