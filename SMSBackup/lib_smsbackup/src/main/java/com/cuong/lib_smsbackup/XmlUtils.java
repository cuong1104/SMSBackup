package com.cuong.lib_smsbackup;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.util.Xml;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cuong.lib_nvc_android.util.DataUtils;
import com.cuong.lib_nvc_android.util.DeviceUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by vcuon on 9/28/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class XmlUtils {

    public static String pathSave;

    public static void BackupSMS(String fileName, ArrayList<SMSEntity> listSms, ArrayList<MMSEntity> listMms, OnFinishBackup onFinish) {
        long current = System.currentTimeMillis();
        if (DataUtils.isNull(pathSave)) {
            pathSave = PicUtils.getExternalPath();
        }
        File newXmlFile = new File(pathSave + File.separator + fileName + ".xml");
        try {
            newXmlFile.createNewFile();
        } catch (IOException e) {
            Log.e("IOException", "Exception in create new File");
        }

        FileOutputStream fileOs = null;
        try {
            fileOs = new FileOutputStream(newXmlFile);
            BackupSMS backup = new BackupSMS();
            backup.setFileOs(fileOs);
            backup.setListMms(listMms);
            backup.setListSms(listSms);
            backup.setTime(current);
            backup.setOnFinishBackup(onFinish);
            backup.execute();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("FileNotFoundException", e.toString());
        }
    }

    public static final String BACKUP = "backup";
    public static final String SMS_BACKUP = "sms-backup";
    public static final String SMS = "sms";
    public static final String MMS = "mms";
    public static final String MMS_BACKUP = "mms-backup";

    public static ProgressBar pbBackup;
    public static TextView tvBackup;

    static class BackupSMS extends AsyncTask<Void, Integer, Integer> {
        private ArrayList<SMSEntity> listSms;
        private ArrayList<MMSEntity> listMms;
        private int total;
        private long time;
        private FileOutputStream fileOs;
        private OnFinishBackup onFinishBackup;

        public void setOnFinishBackup(OnFinishBackup onFinishBackup) {
            this.onFinishBackup = onFinishBackup;
        }

        public void setListSms(ArrayList<SMSEntity> listSms) {
            this.listSms = listSms;
        }

        public void setListMms(ArrayList<MMSEntity> listMms) {
            this.listMms = listMms;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public void setFileOs(FileOutputStream fileOs) {
            this.fileOs = fileOs;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            if (listSms != null) total += listSms.size();
            if (listMms != null) total += listMms.size();
            try {
                XmlSerializer xmlSerializer = Xml.newSerializer();
                StringWriter writer = new StringWriter();
                xmlSerializer.setOutput(writer);
                // start DOCUMENT
                xmlSerializer.startDocument("UTF-8", true);
                // open tag: <backup>
                xmlSerializer.startTag("", BACKUP);
                if (listSms != null && listSms.size() > 0) {
                    // open tag: <sms-backup>
                    xmlSerializer.startTag("", SMS_BACKUP);

                    for (int i = 1; i <= listSms.size(); i++) {
                        SMSEntity sms = listSms.get(i - 1);
                        // open tag: <sms _id='1'>
                        xmlSerializer.startTag("", SMS);
                        xmlSerializer.attribute("", SMSEntity.ID, String.valueOf(sms.get_id()));

                        // open tag: <address>
                        xmlSerializer.startTag("", SMSEntity.ADDRESS);
                        xmlSerializer.text(sms.getAddress() + "");
                        // close tag: </address>
                        xmlSerializer.endTag("", SMSEntity.ADDRESS);

                        // open tag: <date>
                        xmlSerializer.startTag("", SMSEntity.DATE);
                        xmlSerializer.text(sms.getDate() + "");
                        // close tag: </date>
                        xmlSerializer.endTag("", SMSEntity.DATE);

                        // open tag: <date_send>
                        xmlSerializer.startTag("", SMSEntity.DATE_SENT);
                        xmlSerializer.text(sms.getDate_sent() + "");
                        // close tag: </date_sent>
                        xmlSerializer.endTag("", SMSEntity.DATE_SENT);

                        // open tag: <read>
                        xmlSerializer.startTag("", SMSEntity.READ);
                        xmlSerializer.text(sms.getRead() + "");
                        // close tag: </read>
                        xmlSerializer.endTag("", SMSEntity.READ);

                        // open tag: <status>
                        xmlSerializer.startTag("", SMSEntity.STATUS);
                        xmlSerializer.text(sms.getStatus() + "");
                        // close tag: </status>
                        xmlSerializer.endTag("", SMSEntity.STATUS);

                        // open tag: <type>
                        xmlSerializer.startTag("", SMSEntity.TYPE);
                        xmlSerializer.text(sms.getType() + "");
                        // close tag: </type>
                        xmlSerializer.endTag("", SMSEntity.TYPE);

                        // open tag: <body>
                        xmlSerializer.startTag("", SMSEntity.BODY);
                        String text = Html.escapeHtml("" + sms.getBody());
                        xmlSerializer.text(text);
                        // close tag: </body>
                        xmlSerializer.endTag("", SMSEntity.BODY);

                        // open tag: <seen>
                        xmlSerializer.startTag("", SMSEntity.SEEN);
                        xmlSerializer.text(sms.getSeen() + "");
                        // close tag: </seen>
                        xmlSerializer.endTag("", SMSEntity.SEEN);

                        // close tag: </sms>
                        xmlSerializer.endTag("", SMS);
                        publishProgress(i);
                    }
                    // close tag: </sms-backup>
                    xmlSerializer.endTag("", SMS_BACKUP);
                }

                if (listMms != null && listMms.size() > 0) {
                    // open tag: <mms-backup>
                    xmlSerializer.startTag("", MMS_BACKUP);

                    for (int i = 1; i <= listMms.size(); i++) {
                        MMSEntity mms = listMms.get(i - 1);
                        // open tag: <mms _id='1'>
                        xmlSerializer.startTag("", MMS);
                        xmlSerializer.attribute("", MMSEntity.ID, String.valueOf(mms.get_id()));

                        // open tag: <date>
                        xmlSerializer.startTag("", MMSEntity.DATE);
                        xmlSerializer.text(mms.getDate() + "");
                        // close tag: </date>
                        xmlSerializer.endTag("", MMSEntity.DATE);

                        // open tag: <msg-box>
                        xmlSerializer.startTag("", MMSEntity.MSG_BOX);
                        xmlSerializer.text(mms.getMsg_box() + "");
                        // close tag: </msg-box>
                        xmlSerializer.endTag("", MMSEntity.MSG_BOX);

                        // open tag: <read>
                        xmlSerializer.startTag("", MMSEntity.READ);
                        xmlSerializer.text(mms.getRead() + "");
                        // close tag: </read>
                        xmlSerializer.endTag("", MMSEntity.READ);

                        // open tag: <sub>
                        xmlSerializer.startTag("", MMSEntity.SUBJECT);
                        xmlSerializer.text(mms.getSub() + "");
                        // close tag: </sub>
                        xmlSerializer.endTag("", MMSEntity.SUBJECT);

                        // open tag: <sub-cs>
                        xmlSerializer.startTag("", MMSEntity.SUBJECT_CS);
                        xmlSerializer.text(mms.getSub_cs() + "");
                        // close tag: </sub-cs>
                        xmlSerializer.endTag("", MMSEntity.SUBJECT_CS);

                        // open tag: <ct-t>
                        xmlSerializer.startTag("", MMSEntity.TYPE);
                        xmlSerializer.text(mms.getCt_t() + "");
                        // close tag: </ct-t>
                        xmlSerializer.endTag("", MMSEntity.TYPE);

                        // open tag: <exp>
                        xmlSerializer.startTag("", MMSEntity.EXP);
                        xmlSerializer.text(mms.getExp() + "");
                        // close tag: </exp>
                        xmlSerializer.endTag("", MMSEntity.EXP);

                        // open tag: <m-cls>
                        xmlSerializer.startTag("", MMSEntity.M_CLS);
                        xmlSerializer.text(mms.getM_cls() + "");
                        // close tag: </m-cls>
                        xmlSerializer.endTag("", MMSEntity.M_CLS);

                        // open tag: <m-type>
                        xmlSerializer.startTag("", MMSEntity.M_TYPE);
                        xmlSerializer.text(mms.getM_type() + "");
                        // close tag: </m-type>
                        xmlSerializer.endTag("", MMSEntity.M_TYPE);

                        // open tag: <v>
                        xmlSerializer.startTag("", MMSEntity.V);
                        xmlSerializer.text(mms.getV() + "");
                        // close tag: </v>
                        xmlSerializer.endTag("", MMSEntity.V);

                        // open tag: <pri>
                        xmlSerializer.startTag("", MMSEntity.PRI);
                        xmlSerializer.text(mms.getPri() + "");
                        // close tag: </pri>
                        xmlSerializer.endTag("", MMSEntity.PRI);

                        // open tag: <resp-st>
                        xmlSerializer.startTag("", MMSEntity.RESP_ST);
                        xmlSerializer.text(mms.getResp_st() + "");
                        // close tag: </resp-st>
                        xmlSerializer.endTag("", MMSEntity.RESP_ST);

                        // open tag: <seen>
                        xmlSerializer.startTag("", MMSEntity.SEEN);
                        xmlSerializer.text(mms.getSeen() + "");
                        // close tag: </seen>
                        xmlSerializer.endTag("", MMSEntity.SEEN);

                        if (mms.mmsPart != null && mms.mmsPart.size() > 0) {

                            for (int p = 0; p < mms.mmsPart.size(); p++) {
                                // open tag: <part>
                                xmlSerializer.startTag("", MMSEntity.PART);

                                // open tag: <ct>
                                xmlSerializer.startTag("", MMSEntity.PART_CT);
                                xmlSerializer.text(mms.mmsPart.get(p).getCt() + "");
                                // close tag: </ct>
                                xmlSerializer.endTag("", MMSEntity.PART_CT);

                                if (!DataUtils.isNull(mms.mmsPart.get(p).getName())) {
                                    // open tag: <name>
                                    xmlSerializer.startTag("", MMSEntity.PART_NAME);
                                    xmlSerializer.text(mms.mmsPart.get(p).getName() + "");
                                    // close tag: </name>
                                    xmlSerializer.endTag("", MMSEntity.PART_NAME);
                                }

                                if (!DataUtils.isNull(mms.mmsPart.get(p).getChset())) {
                                    // open tag: <chset>
                                    xmlSerializer.startTag("", MMSEntity.PART_CHSET);
                                    xmlSerializer.text(mms.mmsPart.get(p).getChset() + "");
                                    // close tag: </chset>
                                    xmlSerializer.endTag("", MMSEntity.PART_CHSET);
                                }

                                if (!DataUtils.isNull(mms.mmsPart.get(p).getText())) {
                                    // open tag: <chset>
                                    xmlSerializer.startTag("", MMSEntity.PART_TEXT);
                                    xmlSerializer.text(mms.mmsPart.get(p).getText() + "");
                                    // close tag: </chset>
                                    xmlSerializer.endTag("", MMSEntity.PART_TEXT);
                                }

                                try {
                                    String path = PicUtils.TempPhoto(time, mms.mmsPart.get(p).bitmap);
                                    if (!DataUtils.isNull(path)) {
                                        // open tag: <img_url>
                                        xmlSerializer.startTag("", MMSEntity.PART_IMG_URL);
                                        xmlSerializer.text(path);
                                        // close tag: </img_url>
                                        xmlSerializer.endTag("", MMSEntity.PART_IMG_URL);
                                    }
                                } catch (Exception e) {
                                    Log.e("MMS-IMG", "No image part");
                                }
                                // close tag: </part>
                                xmlSerializer.endTag("", MMSEntity.PART);
                            }
                        }

                        if (mms.mmsAddress != null && mms.mmsAddress.size() > 0) {
                            for (int n = 0; n < mms.mmsAddress.size(); n++) {
                                // open tag: <phone-num>
                                xmlSerializer.startTag("", MMSEntity.PHONE_NUM);

                                // open tag: <address>
                                xmlSerializer.startTag("", MMSEntity.ADDRESS);
                                xmlSerializer.text(mms.mmsAddress.get(n).getAddress() + "");
                                // close tag: </address>
                                xmlSerializer.endTag("", MMSEntity.ADDRESS);

                                // open tag: <type>
                                xmlSerializer.startTag("", MMSEntity.ADDRESS_TYPE);
                                xmlSerializer.text(mms.mmsAddress.get(n).getType() + "");
                                // close tag: </type>
                                xmlSerializer.endTag("", MMSEntity.ADDRESS_TYPE);

                                // open tag: <char-set>
                                xmlSerializer.startTag("", MMSEntity.ADDRESS_CHARSET);
                                xmlSerializer.text(mms.mmsAddress.get(n).getCharset() + "");
                                // close tag: </char-set>
                                xmlSerializer.endTag("", MMSEntity.ADDRESS_CHARSET);

                                // close tag: </phone-num>
                                xmlSerializer.endTag("", MMSEntity.PHONE_NUM);
                            }
                        }

                        // close tag: </sms>
                        xmlSerializer.endTag("", MMS);
                        publishProgress(listSms.size() + i);
                    }
                    // close tag: </sms-backup>
                    xmlSerializer.endTag("", MMS_BACKUP);
                }
                // close tag: </backup>
                xmlSerializer.endTag("", BACKUP);

                // end DOCUMENT
                xmlSerializer.endDocument();
                xmlSerializer.flush();
                String dataWrite = writer.toString();
                fileOs.write(dataWrite.getBytes());
                fileOs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvBackup.setText(values[0] + "/" + total);
            pbBackup.setMax(total);
            pbBackup.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            onFinishBackup.onFinish();
        }
    }

    public static ProgressBar pbRestore;
    public static TextView tvProgressRestore;

    public static ArrayList<BackupEntity> GetAllBackupFile() {
        if (DataUtils.isNull(pathSave)) {
            pathSave = PicUtils.getExternalPath();
        }
        File file = new File(pathSave);
        ArrayList<BackupEntity> filePath = new ArrayList<BackupEntity>();
        if (file == null || file.list() == null) {
            return filePath;
        }
        for (int i = 0; i < file.listFiles().length; i++) {
            File bk = file.listFiles()[i];
            BackupEntity backup = new BackupEntity();
            if (bk.getPath().contains(".xml")) {
                backup.setFileName(bk.getName());
                backup.setDateCreate(bk.lastModified());
                backup.setFilePath(bk.getPath());
                backup.setFileSize(bk.length() / 1024);
                filePath.add(backup);
            }
        }
        if (filePath != null) {
            Collections.sort(filePath, new Comparator<BackupEntity>() {
                @Override
                public int compare(BackupEntity b1, BackupEntity b2) {
                    return (int) (b2.getDateCreate() - b1.getDateCreate());
                }
            });
        }
        return filePath;
    }

    public static void ClearAllBackup() {
        ArrayList<BackupEntity> backups = GetAllBackupFile();
        String pathTemp = pathSave;
        if (backups != null && backups.size() > 0) {
            for (BackupEntity backup : backups) {
                File file = new File(backup.getFilePath());
                if (file.exists()) {
                    file.delete();
                }
            }
        }

        pathTemp += File.separator + "temp";
        File temp = new File(pathTemp);
        if (temp.exists() && temp.isDirectory()) {
            for (File f : temp.listFiles()) {
                DeleteRecursive(f);
            }
        }
    }
    private static void DeleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                DeleteRecursive(child);

        fileOrDirectory.delete();
    }

    private static AsyncTask<String, Integer, Void> restore;

    public static void StopRestoring(Context context) {
        isRestoring = false;
        btnRestore.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pbRestore.setMax(10);
                pbRestore.setProgress(10);
                tvProgressRestore.setText("NA/NA");
            }
        }, 1000);
    }

    public static boolean isRestoring;
    public static Button btnRestore;

    public static void RestoreSMS(final Context context, String filePath, final boolean isMerger) {

        restore = new AsyncTask<String, Integer, Void>() {
            private ArrayList<SMSEntity> listSms;
            private ArrayList<MMSEntity> listMms;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                btnRestore.setText("Stop");
                btnRestore.setEnabled(false);
            }

            @Override
            protected Void doInBackground(String... values) {
                File backup = new File(values[0]);
                if (backup.exists()) {
                    try {
                        isRestoring = true;
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                        SAXParser parser = factory.newSAXParser();
                        XMLReader reader = parser.getXMLReader();
                        XmlHandler handler = new XmlHandler();
                        reader.setContentHandler(handler);
                        FileInputStream stream = new FileInputStream(backup.getAbsolutePath());
                        InputSource source = new InputSource(stream);
                        reader.parse(source);
                        listSms = new ArrayList<SMSEntity>();
                        listSms = handler.getListSms();
                        listMms = new ArrayList<MMSEntity>();
                        listMms = handler.getListMms();

                        if (!isMerger) {
                            try {
                                Uri uri = Uri.parse("content://sms/");
                                context.getContentResolver().delete(uri, null, null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                Uri uri = Uri.parse("content://mms/");
                                context.getContentResolver().delete(uri, null, null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayList<SMSEntity> currentSms = SMSUtils.getAllSms((Activity) context);
                        for (int i = 1; i <= listSms.size(); i++) {
                            SMSEntity sms = listSms.get(i - 1);
                            if (!isRestoring) return null;
                            if (checkDuplicateSms(sms, currentSms)) {
                                publishProgress(i);
                                continue;
                            }
                            SMSUtils.RestoreSMS(context, sms);
                            publishProgress(i);
                        }
                        ArrayList<MMSEntity> currentMms = MMSUtils.getAllMms((Activity) context);

                        for (int i = 1; i <= listMms.size(); i++) {
                            if (!isRestoring) return null;
                            if (checkDuplicateMms(listMms.get(i - 1), currentMms)) {
                                publishProgress(listSms.size() + i);
                                continue;
                            }
                            MMSUtils.insert(context, listMms.get(i - 1));
                            publishProgress(listSms.size() + i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            private boolean checkDuplicateSms(SMSEntity sms, ArrayList<SMSEntity> list) {
                if (list == null || list.size() <= 0) return false;
                String body = StringEscapeUtils.unescapeHtml4(sms.getBody());
                sms.setBody(body);
                for (SMSEntity s : list) {
                    if (s.equals(sms)) {
                        return true;
                    }
                }
                return false;
            }

            private boolean checkDuplicateMms(MMSEntity mms, ArrayList<MMSEntity> list) {
                if (list == null || list.size() <= 0) return false;
                for (MMSEntity m : list) {
                    if (m.equals(mms)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                int sum = listMms.size() + listSms.size();
                tvProgressRestore.setText(values[0] + "/" + sum);
                pbRestore.setMax(sum);
                pbRestore.setProgress(values[0]);
                btnRestore.setEnabled(true);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                btnRestore.setEnabled(true);
                btnRestore.setText("Restore");
                if (isRestoring) {
                    DeviceUtils.showMessage(context, "Restore completed!");
                }
                isRestoring = false;
            }
        };
        restore.execute(filePath);
    }

    public static interface OnFinishBackup {
        void onFinish();
    }
}
