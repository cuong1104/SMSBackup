package com.cuong.lib_nvc_android.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by vcuon on 7/27/2015.
 * Open Source: This source has been wrote by CuongNguyen
 * Contact: vcuong11s@gmail.com or unme.rf@gmail.com
 */
public class TextViewUtils {
    public static TextWatcher AddTextChange(TextView textView, final Runnable runnable){
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (runnable != null){
                    runnable.run();
                }
            }
        };

        if (textView != null){
            textView.addTextChangedListener(tw);
        }
        return tw;
    }
}
