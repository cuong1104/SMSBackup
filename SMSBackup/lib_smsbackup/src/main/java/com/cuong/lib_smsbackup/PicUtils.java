package com.cuong.lib_smsbackup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.cuong.lib_nvc_android.util.DataUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by vcuon on 10/1/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class PicUtils {
    // save a bitmap with file temp to device
    public static String TempPhoto(long time, Bitmap bmp) {
        if (bmp == null || bmp.getByteCount() < 10) {
            return "";
        }
        String filename = "backup_mms_temp_" + System.currentTimeMillis();
        String path = XmlUtils.pathSave + "/temp/";
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        path += time + "/";
        folder = new File(path);
        if (!folder.exists()) {
            folder.mkdir();
        }
        path += filename + ".png";
        File temp = new File(path);
        if (temp.exists()) {
            return path;
        } else {
            try {
                FileOutputStream out = new FileOutputStream(path);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
                return path;
            } catch (Exception e) {
                Log.e("SaveTempFile", e.getMessage());
            }
            return "";
        }
    }

    // load a bitmap from file
    public static Bitmap loadFromFilePath(String filePath) {
        if (!DataUtils.isNull(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    Bitmap tmp = decodeBitmap(file.getAbsolutePath());
                    return tmp;
                } catch (Exception e) {
                    Log.e("LoadImage", "Can not load file " + filePath);
                    return null;
                }
            } else {
                Log.e("LoadImage", "Error File not exists " + filePath);
                return null;
            }
        } else {
            return null;
        }
    }

    public static Bitmap decodeBitmap(String filepath) {
        Bitmap bitmap = null;
        // Get the dimensions of the original bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filepath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

// Determine how much to scale down the image.
        int scaleFactor = 1;    //1, 2, 3, 4, 5, 6, ...
        scaleFactor = (int) Math.pow(2.0, Math.floor(Math.log((double) scaleFactor) / Math.log(2.0)));               //1, 2, 4, 8, ...

// Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        do {
            try {
                Log.d("tag", "scaleFactor: " + scaleFactor);
                scaleFactor *= 2;
                bitmap = BitmapFactory.decodeFile(filepath, bmOptions);
            } catch (OutOfMemoryError e) {
                scaleFactor++;
                bmOptions.inSampleSize = scaleFactor;
                Log.d("tag", "OutOfMemoryError: " + e.toString());
            }
        }
        while (bitmap == null && scaleFactor <= 256);

        if (bitmap == null)
            return null;
        return bitmap;
    }

    // get save path
    public static File getSavePath() {
        File path;
        if (hasSDCard()) { // SD card
            path = new File(getSDCardPath() + "/smsbackup/");
            path.mkdir();
        } else {
            path = Environment.getDataDirectory();
        }
        return path;
    }

    // check if SDCard has been mount
    public static boolean hasSDCard() { // SD????????
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    // get SDCard path
    public static String getSDCardPath() {
        File path = Environment.getExternalStorageDirectory();
        return path.getAbsolutePath();
    }


    public static ArrayList<String> getExternalMounts() {
//        File storageDir = new File("/");
        ArrayList<String> out = new ArrayList<String>();
        String[] path = new String[]{
                "/mnt/external_sd/",
                "/mnt/extsdcard/",
                "/storage/sdcard1",
                "/storage/extsdcard",
                "/storage/sdcard0/external_sdcard",
                "/mnt/extsdcard",
                "/mnt/sdcard/external_sd",
                "/mnt/media_rw/sdcard1",
                "/removable/microsd",
                "/mnt/emmc",
                "/storage/external_SD",
                "/storage/ext_sd",
                "/Removable/MicroSD",
                "/storage/removable/sdcard1",
                "/data/sdext",
                "/data/sdext2",
                "/data/sdext3",
                "/data/sdext4"};
        for (String s : path) {
            File file = new File(s);
            if (file.exists() && file.canWrite()) {
                Log.i("SDCard", s);
                File dir = new File(file.getAbsolutePath()+File.pathSeparator+"SMSBackup");
                if (!dir.exists()){
                    dir.mkdir();
                    if (dir.exists()){
                        out.add(s);
                    }
                }
            }
        }

        out.add(Environment.getExternalStorageDirectory().getAbsolutePath());
        return out;
    }

    public static String getExternalPath() {
        String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "SMSBackup";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }
}
