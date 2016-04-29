package com.cuong.lib_nvc_android.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vcuon on 7/20/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class DataUtils {
    public static int GetInt(String num) {
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 0;
        }
    }

    public static long GetLong(String num) {
        try {
            return Long.parseLong(num);
        } catch (Exception e) {
            return 0;
        }
    }

    public static float GetFloat(String num) {
        try {
            return Float.parseFloat(num);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isNull(String str) {
        if (str != null) {
            if (!str.trim().equals("") && !str.trim().equals("null")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isFileExists(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Round to certain number of decimals
     *
     * @param d
     * @param decimalPlace
     * @return
     */
    public static String Round(float d, int decimalPlace) {
        String fs = d + "";
        int index = fs.indexOf(".");
        if (isNull(fs) || fs.split("\\.").length > 2) {
            return "0";
        } else {
            if (index < 0) {
                String ds = d + "";
                ds = ds.replaceAll("\\.0", "");
                return ds;
            } else if (index == 0) {
                fs = "0" + fs;
                return Round(GetFloat(fs), decimalPlace);
            } else {
                String result = fs.substring(0, index + decimalPlace + 1);
                result.replaceAll("\\.0", "");
                return result;
            }
        }
    }

    public static String ReadFromFile(String fileName, Context context) {
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            fIn = context.getResources().getAssets()
                    .open(fileName, Context.MODE_WORLD_READABLE);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);
            String line = "";
            while ((line = input.readLine()) != null) {
                returnString.append(line);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            try {
                if (isr != null)
                    isr.close();
                if (fIn != null)
                    fIn.close();
                if (input != null)
                    input.close();
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        return returnString.toString();
    }
}
