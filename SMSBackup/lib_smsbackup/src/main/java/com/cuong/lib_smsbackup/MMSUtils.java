package com.cuong.lib_smsbackup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.Telephony;
import android.util.Log;
import android.widget.TextView;

import com.cuong.lib_nvc_android.util.DataUtils;
import com.cuong.lib_nvc_android.util.DeviceUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vcuon on 9/24/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class MMSUtils {
    public static final int MESSAGE_TYPE_OUTBOX = 2;
    public static final int MESSAGE_TYPE_INBOX = 1;

    public static ArrayList<MMSEntity> getAllMmsAfter(Activity activity, long date){
        ArrayList<MMSEntity> all = getAllMms(activity);
        if (all != null && all.size() > 0){
            for (int i = 0; i < all.size(); i++){
                if (DataUtils.GetLong(all.get(i).getDate()) < date){
                    all.remove(i);
                    i--;
                }
            }
        }
        return all;
    }

    public static ArrayList<MMSEntity> getAllMms(Activity activity) {
        ArrayList<MMSEntity> result = null;
        Uri uri = Uri.parse("content://mms/");
//        String[] columns = new String[]{"_id", "thread_id", "date", "date_sent", "msg_box", "read", "m_id", "sub", "sub_cs", "ct_t", "ct_l", "exp", "m_cls", "m_type", "v", "m_size", "pri", "rr", "rpt_a", "resp_st", "st", "tr_id", "retr_st", "retr_txt", "retr_txt_cs", "read_status", "ct_cls", "resp_txt", "d_tm", "d_rpt", "locked", "seen", "text_only", "imsi", "block", "spam", "sub_id", "phone_id", "creator"};
        String[] columns = new String[]{"_id", "date", "msg_box", "read", "m_id", "sub", "sub_cs", "ct_t", "exp", "m_cls", "m_type", "v", "m_size", "pri", "resp_st", "seen"};
        Cursor cursor = activity.getContentResolver().query(uri, columns, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            result = new ArrayList<MMSEntity>();
            while (cursor.moveToNext()) {
                MMSEntity mms = new MMSEntity();
                int i = 0;
                mms.set_id(cursor.getString(cursor.getColumnIndex(columns[i++])));
//                mms.setThread_id(cursor.getString(cursor.getColumnIndex(columns[1])));
                mms.setDate(cursor.getString(cursor.getColumnIndex(columns[i++])));
//                mms.setDate_sent(cursor.getString(cursor.getColumnIndex(columns[3])));
                mms.setMsg_box(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setRead(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setM_id(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setSub(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setSub_cs(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setCt_t(cursor.getString(cursor.getColumnIndex(columns[i++])));
//                mms.setCt_l(cursor.getString(cursor.getColumnIndex(columns[10])));
                mms.setExp(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setM_cls(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setM_type(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setV(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setM_size(cursor.getString(cursor.getColumnIndex(columns[i++])));
                mms.setPri(cursor.getString(cursor.getColumnIndex(columns[i++])));
//                mms.setRr(cursor.getString(cursor.getColumnIndex(columns[17])));
//                mms.setRpt_a(cursor.getString(cursor.getColumnIndex(columns[18])));
                mms.setResp_st(cursor.getString(cursor.getColumnIndex(columns[i++])));
//                mms.setSt(cursor.getString(cursor.getColumnIndex(columns[20])));
//                mms.setTr_id(cursor.getString(cursor.getColumnIndex(columns[21])));
//                mms.setRetr_st(cursor.getString(cursor.getColumnIndex(columns[22])));
//                mms.setRetr_txt(cursor.getString(cursor.getColumnIndex(columns[23])));
//                mms.setRetr_txt_cs(cursor.getString(cursor.getColumnIndex(columns[24])));
//                mms.setRead_status(cursor.getString(cursor.getColumnIndex(columns[25])));
//                mms.setCt_cls(cursor.getString(cursor.getColumnIndex(columns[26])));
//                mms.setResp_txt(cursor.getString(cursor.getColumnIndex(columns[27])));
//                mms.setD_tm(cursor.getString(cursor.getColumnIndex(columns[28])));
//                mms.setD_rpt(cursor.getString(cursor.getColumnIndex(columns[29])));
//                mms.setLocked(cursor.getString(cursor.getColumnIndex(columns[30])));
                mms.setSeen(cursor.getString(cursor.getColumnIndex(columns[i++])));
//                mms.setText_only(cursor.getString(cursor.getColumnIndex(columns[32])));
//                mms.setImsi(cursor.getString(cursor.getColumnIndex(columns[33])));
//                mms.setBlock(cursor.getString(cursor.getColumnIndex(columns[34])));
//                mms.setSpam(cursor.getString(cursor.getColumnIndex(columns[35])));
//                mms.setSub_id(cursor.getString(cursor.getColumnIndex(columns[36])));
//                mms.setPhone_id(cursor.getString(cursor.getColumnIndex(columns[37])));
//                mms.setCreator(cursor.getString(cursor.getColumnIndex(columns[38])));
                mms.mmsAddress = getAddressNumber(activity, DataUtils.GetInt(mms.get_id()));
                mms.mmsPart = getMmsPart(activity, mms.get_id());
                result.add(mms);
            }
        }
        return result;
    }

    private static ArrayList<MMSPartEntity> getMmsPart(Activity activity, String mmsId) {
        String selectionPart = "mid=" + mmsId;
        Uri uri = Uri.parse("content://mms/part");
        Cursor cursor = activity.getContentResolver().query(uri, null,
                selectionPart, null, null);
        String body = "";
        ArrayList<MMSPartEntity> result = new ArrayList<MMSPartEntity>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                try {
                    String partId = cursor.getString(cursor.getColumnIndex("_id"));
                    String mid = cursor.getString(cursor.getColumnIndex("mid"));
                    String seq = cursor.getString(cursor.getColumnIndex("seq"));
                    String type = cursor.getString(cursor.getColumnIndex("ct"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String chset = cursor.getString(cursor.getColumnIndex("chset"));
                    String cd = cursor.getString(cursor.getColumnIndex("cd"));
                    String fn = cursor.getString(cursor.getColumnIndex("fn"));
                    String cl = cursor.getString(cursor.getColumnIndex("cl"));
                    String ctt_s = cursor.getString(cursor.getColumnIndex("ctt_s"));
                    String ctt_t = cursor.getString(cursor.getColumnIndex("ctt_t"));
                    MMSPartEntity mms = new MMSPartEntity();
                    mms.set_id(partId);
                    mms.setMid(mid);
                    mms.setSeq(seq);
                    mms.setCt(type);
                    mms.setName(name);
                    mms.setChset(chset);
                    mms.setCd(cd);
                    mms.setFn(fn);
                    mms.setCl(cl);
                    mms.setCtt_s(ctt_s);
                    mms.setCtt_t(ctt_t);
                    if ("text/plain".equals(type)) {
                        String data = cursor.getString(cursor.getColumnIndex("_data"));
                        mms.set_data(data);
                        if (data != null) {
                            // implementation of this method below
                            body = getMmsText(activity, partId);
                        } else {
                            body = cursor.getString(cursor.getColumnIndex("text"));
                        }
                        mms.setText(body);
                    } else if ("image/jpeg".equals(type) || "image/bmp".equals(type) ||
                            "image/gif".equals(type) || "image/jpg".equals(type) ||
                            "image/png".equals(type)) {
                        mms.bitmap = getMmsImage(activity, partId);
                    }
                    result.add(mms);
                }catch (Exception e){
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        return result;
    }

    private static String getMmsText(Activity activity, String id) {
        Uri partURI = Uri.parse("content://mms/part/" + id);
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = activity.getContentResolver().openInputStream(partURI);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader reader = new BufferedReader(isr);
                String temp = reader.readLine();
                while (temp != null) {
                    sb.append(temp);
                    temp = reader.readLine();
                }
            }
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }

    private static Bitmap getMmsImage(Activity activity, String _id) {
        Uri partURI = Uri.parse("content://mms/part/" + _id);
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = activity.getContentResolver().openInputStream(partURI);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return bitmap;
    }

    private static ArrayList<MMSAddressEntity> getAddressNumber(Activity activity, int id) {
        String selectionAdd = new String("msg_id=" + id);
        String uriStr = MessageFormat.format("content://mms/{0}/addr", id);
        Uri uriAddress = Uri.parse(uriStr);
        Cursor cAdd = activity.getContentResolver().query(uriAddress, null,
                selectionAdd, null, null);
        String name = null;
        ArrayList<MMSAddressEntity> result = new ArrayList<MMSAddressEntity>();
        if (cAdd.moveToFirst()) {
            do {
                String _id = cAdd.getString(cAdd.getColumnIndex("_id"));
                String msg_id = cAdd.getString(cAdd.getColumnIndex("msg_id"));
                String contact_id = cAdd.getString(cAdd.getColumnIndex("contact_id"));
                String number = cAdd.getString(cAdd.getColumnIndex("address"));
                String type = cAdd.getString(cAdd.getColumnIndex("type"));
                String charset = cAdd.getString(cAdd.getColumnIndex("charset"));
                MMSAddressEntity mms = new MMSAddressEntity();
                mms.set_id(_id);
                mms.setMsg_id(msg_id);
                mms.setContact_id(contact_id);
                mms.setAddress(number);
                mms.setType(type);
                mms.setCharset(charset);
                if (number != null) {
                    try {
                        Long.parseLong(number.replace("-", ""));
                        name = number;
                    } catch (NumberFormatException nfe) {
                        if (name == null) {
                            name = number;
                        }
                    }
                }
                result.add(mms);
            } while (cAdd.moveToNext());
        }
        if (cAdd != null) {
            cAdd.close();
        }
        return result;
    }

    public static void deleteAll(Context context) {
        context.getContentResolver().delete(Uri.parse("content://mms"), null, null);
        context.getContentResolver().delete(Uri.parse("content://mms/part"), null, null);
    }

    private static boolean isDuplicate(String num1, String num2) {
        if (num1.equalsIgnoreCase(num2)) return true;
        if (num1.startsWith("0")) num1 = num1.substring(1);
        if (num2.startsWith("0")) num2 = num2.substring(1);
        if (num1.contains(num2) || num2.contains(num1)) return true;
        return false;
    }

    public static void cleanMMS(final Activity context, final TextView btnClearMMS) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (btnClearMMS != null) {
                    btnClearMMS.setEnabled(false);
                }
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Uri uri = Uri.parse("content://mms/");
                    context.getContentResolver().delete(uri, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (btnClearMMS != null) {
                    btnClearMMS.setEnabled(true);
                }
                DeviceUtils.showMessage(context, "All MMS cleared");
            }
        }.execute();
    }

    public static Uri insert(Context context, MMSEntity mms) {
        try {
            Uri destUri = Uri.parse("content://mms/inbox");
            ArrayList<MMSAddressEntity> mAddress = mms.mmsAddress;
            ArrayList<String> listAddress = new ArrayList<String>();
            if (mAddress != null && mAddress.size() > 0) {
                for (MMSAddressEntity address : mAddress) {
                    boolean duplicate = false;
                    String a = address.getAddress();
                    if (DataUtils.GetLong(a) == 0) continue;

                    if (a.startsWith("84")) a = "0" + a.substring(3);
                    for (String s : listAddress) {
                        if (isDuplicate(s, a)) {
                            duplicate = true;
                            break;
                        }
                    }
                    if (!duplicate) {
                        listAddress.add(a);
                    }
                }
            }
            String[] address = new String[listAddress.size()];
            for (int i = 0; i < listAddress.size(); i++){
                address[i] = listAddress.get(i);
            }

            long threadId = getOrCreateThreadId(context, address);
            Log.e("Thread ID", threadId+"");
            // Create a dummy sms
            ContentValues dummyValues = new ContentValues();
            dummyValues.put("thread_id", threadId);
            dummyValues.put("body", "Dummy SMS body.");
            Uri dummySms = context.getContentResolver().insert(Uri.parse("content://sms/sent"), dummyValues);

            // Create a new message entry
            ContentValues mmsValues = new ContentValues();
            mmsValues.put("thread_id", threadId);
            mmsValues.put("date", mms.getDate());
            mmsValues.put("msg_box", mms.getMsg_box());
            mmsValues.put("read", mms.getRead());
            mmsValues.put("sub", mms.getSub());
            mmsValues.put("sub_cs", mms.getSub_cs());
            mmsValues.put("ct_t", mms.getCt_t());
            mmsValues.put("exp", mms.getExp());
            mmsValues.put("m_cls", "personal");
            mmsValues.put("m_type", mms.getM_type());
            mmsValues.put("v", 19);
            mmsValues.put("pri", 129);
            mmsValues.put("resp_st", 128);
            mmsValues.put("seen", mms.getSeen());

            // Insert message
            Uri res = context.getContentResolver().insert(destUri, mmsValues);
            String messageId = res.getLastPathSegment().trim();
            mms.set_id(messageId);
            Log.e(">>>>>>>", "Message saved as " + res);

            // Create part
            createPart(context, mms);

            // Create addresses
            for (MMSAddressEntity addr : mms.mmsAddress) {
                try {
                    addr.setMsg_id(mms.get_id());
                    createAddr(context, addr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Delete dummy sms
            context.getContentResolver().delete(dummySms, null, null);

            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long getOrCreateThreadId(Context context, String[] listAddress) {
        // Get thread id
        Set<String> recipients = new HashSet<String>();
        recipients.addAll(Arrays.asList(listAddress));
        Class[] paramTypes = new Class[2];
        paramTypes[0] = Context.class;
        paramTypes[1] = Set.class;

        Method getOrCreateThreadId = null;
        Class classToInvestigate = null;
        Object cC = null;
        try {
            String className = "android.provider.Telephony";

            classToInvestigate = Class.forName(className);
            //cC = classToInvestigate.newInstance();
            Class[] classes = classToInvestigate.getClasses();

            Class threadsClass = null;
            for (Class c : classes) {
                if (c.equals((Class) Telephony.Threads.class))
                    threadsClass = c;
            }


            getOrCreateThreadId = threadsClass.getMethod("getOrCreateThreadId", paramTypes);
            getOrCreateThreadId.setAccessible(true);
            Log.e("Methode", "" + getOrCreateThreadId.toString());
            //Toast.makeText(getApplicationContext(), getOrCreateThreadId.toString(), Toast.LENGTH_LONG).show();

            Object arglist[] = new Object[2];
            arglist[0] = context;
            arglist[1] = recipients; // Not a real phone number

            Long threadId = (Long) getOrCreateThreadId.invoke(null, arglist);
            return threadId;

        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IllegalAccessException e3) {
            // TODO Auto-generated catch block
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        }
        return -1;
    }

    private static void createPart(Context context, MMSEntity mms) throws Exception {
        for (MMSPartEntity part : mms.mmsPart) {
            try {
                part.setMid(mms.get_id());
                String type = part.getCt();
                ContentValues mmsPartValues = new ContentValues();
                mmsPartValues.put("mid", part.getMid());
                mmsPartValues.put("ct", part.getCt());
                mmsPartValues.put("cid", "<" + System.currentTimeMillis() + ">");

                if ("application/smil".equals(type)){
                    continue;
                }

                if (isPartText(type)) {
                    mmsPartValues.put("name", part.getName());
                    mmsPartValues.put("chset", part.getChset());
                    mmsPartValues.put("text", part.getText());
                }
                Uri partUri = Uri.parse("content://mms/" + part.getMid() + "/part");
                Uri res = context.getContentResolver().insert(partUri, mmsPartValues);
                Log.e("InsertPart", res != null ? res.toString() : "null");
                if (isPartImage(type)) {
                    // Add data to part
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    part.bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    OutputStream os = context.getContentResolver().openOutputStream(res);
                    ByteArrayInputStream is = new ByteArrayInputStream(byteArray);
                    byte[] buffer = new byte[256];
                    for (int len = 0; (len = is.read(buffer)) != -1; ) {
                        os.write(buffer, 0, len);
                    }
                    os.close();
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isPartText(String type){
        if ("text/plain".equals(type)) {
            return true;
        }
        return false;
    }

    private static boolean isPartImage(String type){
        if ("image/jpeg".equals(type) || "image/bmp".equals(type) ||
                "image/gif".equals(type) || "image/jpg".equals(type) ||
                "image/png".equals(type)) {
            return true;
        }
        return false;
    }

    private static Uri createAddr(Context context, MMSAddressEntity addr) throws Exception {
        ContentValues addrValues = new ContentValues();
        addrValues.put("address", addr.getAddress());
        addrValues.put("type", addr.getType());
        addrValues.put("charset", addr.getCharset());
        Uri addrUri = Uri.parse("content://mms/" + addr.getMsg_id() + "/addr");
        Uri res = context.getContentResolver().insert(addrUri, addrValues);
        Log.e(">>>>>>>", "Addr uri is " + res.toString());

        return res;
    }
}
